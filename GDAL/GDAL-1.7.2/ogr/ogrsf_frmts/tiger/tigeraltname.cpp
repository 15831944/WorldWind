/******************************************************************************
 * $Id: tigeraltname.cpp 1 2011-07-16 23:22:47Z dcollins $
 *
 * Project:  TIGER/Line Translator
 * Purpose:  Implements TigerAltName, providing access to RT4 files.
 * Author:   Frank Warmerdam, warmerdam@pobox.com
 *
 ******************************************************************************
 * Copyright (c) 1999, Frank Warmerdam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 ****************************************************************************/

#include "ogr_tiger.h"
#include "cpl_conv.h"

CPL_CVSID("$Id: tigeraltname.cpp 1 2011-07-16 23:22:47Z dcollins $");

#define FILE_CODE "4"

static TigerFieldInfo rt4_fields[] = {
  // fieldname    fmt  type  OFTType         beg  end  len  bDefine bSet bWrite
  { "MODULE",     ' ',  ' ', OFTString,        0,   0,   8,       1,   0,     0 },
  { "TLID",       'R',  'N', OFTInteger,       6,  15,  10,       1,   1,     1 },
  { "RTSQ",       'R',  'N', OFTInteger,      16,  18,   3,       1,   1,     1 },
  { "FEAT",       ' ',  ' ', OFTIntegerList,   0,   0,   8,       1,   0,     0 }
  // Note: we don't mention the FEAT1, FEAT2, FEAT3, FEAT4, FEAT5 fields
  // here because they're handled separately in the code below; they correspond
  // to the FEAT array field here.
};

static TigerRecordInfo rt4_info =
  {
    rt4_fields,
    sizeof(rt4_fields) / sizeof(TigerFieldInfo),
    58
  };


/************************************************************************/
/*                            TigerAltName()                            */
/************************************************************************/

TigerAltName::TigerAltName( OGRTigerDataSource * poDSIn,
                            const char * pszPrototypeModule )

{
    OGRFieldDefn        oField("",OFTInteger);

    psRT4Info = &rt4_info;

    poDS = poDSIn;
    poFeatureDefn = new OGRFeatureDefn( "AltName" );
    poFeatureDefn->Reference();
    poFeatureDefn->SetGeomType( wkbNone );

    /* -------------------------------------------------------------------- */
    /*      Fields from type 4 record.                                      */
    /* -------------------------------------------------------------------- */

    AddFieldDefns( psRT4Info, poFeatureDefn );
}

/************************************************************************/
/*                           ~TigerAltName()                            */
/************************************************************************/

TigerAltName::~TigerAltName()

{
}

/************************************************************************/
/*                             SetModule()                              */
/************************************************************************/

int TigerAltName::SetModule( const char * pszModule )

{
    if( !OpenFile( pszModule, FILE_CODE ) )
        return FALSE;

    EstablishFeatureCount();
    
    return TRUE;
}

/************************************************************************/
/*                             GetFeature()                             */
/************************************************************************/

OGRFeature *TigerAltName::GetFeature( int nRecordId )

{
    char        achRecord[OGR_TIGER_RECBUF_LEN];

    if( nRecordId < 0 || nRecordId >= nFeatures )
    {
        CPLError( CE_Failure, CPLE_FileIO,
                  "Request for out-of-range feature %d of %s4",
                  nRecordId, pszModule );
        return NULL;
    }

/* -------------------------------------------------------------------- */
/*      Read the raw record data from the file.                         */
/* -------------------------------------------------------------------- */
    if( fpPrimary == NULL )
        return NULL;

    if( VSIFSeek( fpPrimary, nRecordId * nRecordLength, SEEK_SET ) != 0 )
    {
        CPLError( CE_Failure, CPLE_FileIO,
                  "Failed to seek to %d of %s4",
                  nRecordId * nRecordLength, pszModule );
        return NULL;
    }

    if( VSIFRead( achRecord, psRT4Info->nRecordLength, 1, fpPrimary ) != 1 )
    {
        CPLError( CE_Failure, CPLE_FileIO,
                  "Failed to read record %d of %s4",
                  nRecordId, pszModule );
        return NULL;
    }

    /* -------------------------------------------------------------------- */
    /*      Set fields.                                                     */
    /* -------------------------------------------------------------------- */

    OGRFeature  *poFeature = new OGRFeature( poFeatureDefn );
    int         anFeatList[5];
    int         nFeatCount=0;

    SetFields( psRT4Info, poFeature, achRecord );

    for( int iFeat = 0; iFeat < 5; iFeat++ )
    {
        const char *    pszFieldText;

        pszFieldText = GetField( achRecord, 19 + iFeat*8, 26 + iFeat*8 );

        if( *pszFieldText != '\0' )
            anFeatList[nFeatCount++] = atoi(pszFieldText);
    }

    poFeature->SetField( "FEAT", nFeatCount, anFeatList );

    return poFeature;
}

/************************************************************************/
/*                           CreateFeature()                            */
/************************************************************************/

OGRErr TigerAltName::CreateFeature( OGRFeature *poFeature )

{
  char        szRecord[OGR_TIGER_RECBUF_LEN];
    const int   *panValue;
    int         nValueCount = 0;

    if( !SetWriteModule( FILE_CODE, psRT4Info->nRecordLength+2, poFeature ) )
        return OGRERR_FAILURE;

    memset( szRecord, ' ', psRT4Info->nRecordLength );

    WriteFields( psRT4Info, poFeature, szRecord );

    panValue = poFeature->GetFieldAsIntegerList( "FEAT", &nValueCount );
    for( int i = 0; i < nValueCount; i++ )
    {
        char    szWork[9];
        
        sprintf( szWork, "%8d", panValue[i] );
        strncpy( szRecord + 18 + 8 * i, szWork, 8 );
    }

    WriteRecord( szRecord, psRT4Info->nRecordLength, FILE_CODE );

    return OGRERR_NONE;
}
