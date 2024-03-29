/******************************************************************************
 * $Id: ogr_pcidsk.h 1 2011-07-16 23:22:47Z dcollins $
 *
 * Project:  PCIDSK Translator
 * Purpose:  Definition of classes for PCIDSK vector segment driver.
 * Author:   Frank Warmerdam, warmerdam@pobox.com
 *
 ******************************************************************************
 * Copyright (c) 2009,  Frank Warmerdam
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

#ifndef _OGR_PCIDSK_H_INCLUDED
#define _OGR_PCIDSK_H_INCLUDED

#include "ogrsf_frmts.h"
#include "pcidsk.h"
#include "pcidsk_vectorsegment.h"

class OGRPCIDSKDataSource;

/************************************************************************/
/*                             OGRPCIDSKLayer                              */
/************************************************************************/

class OGRPCIDSKLayer : public OGRLayer
{
    PCIDSK::PCIDSKVectorSegment *poVecSeg;
    PCIDSK::PCIDSKSegment       *poSeg;

    OGRFeatureDefn     *poFeatureDefn;

    OGRFeature *        GetNextUnfilteredFeature();

    int                 iRingStartField;
    PCIDSK::ShapeId     hLastShapeId;

  public:
    OGRPCIDSKLayer( PCIDSK::PCIDSKSegment* );
    ~OGRPCIDSKLayer();

    void                ResetReading();
    OGRFeature *        GetNextFeature();

    OGRFeatureDefn *    GetLayerDefn() { return poFeatureDefn; }

    int                 TestCapability( const char * );
};

/************************************************************************/
/*                           OGRPCIDSKDataSource                           */
/************************************************************************/

class OGRPCIDSKDataSource : public OGRDataSource
{
    CPLString           osName;

    std::vector<OGRPCIDSKLayer*> apoLayers;

    int                 bUpdate;

    PCIDSK::PCIDSKFile  *poFile;
    
  public:
                        OGRPCIDSKDataSource();
                        ~OGRPCIDSKDataSource();

    int                 Open( const char * pszFilename, int bUpdate);
    
    const char          *GetName() { return osName; }

    int                 GetLayerCount() { return (int) apoLayers.size(); }
    OGRLayer            *GetLayer( int );

    int                 TestCapability( const char * );
};

/************************************************************************/
/*                           OGRPCIDSKDriver                            */
/************************************************************************/

class OGRPCIDSKDriver : public OGRSFDriver
{
  public:
                ~OGRPCIDSKDriver();
                
    const char *GetName();
    OGRDataSource *Open( const char *, int );
    int         TestCapability( const char * );
};


#endif /* ndef _OGR_PCIDSK_H_INCLUDED */
