/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.

 @version $Id: WWTerrainSharedGeometry.h 1469 2013-06-20 23:27:00Z tgaskins $
 */

#import <Foundation/Foundation.h>

/**
* Internal class that represents data shared within the tessellator.
*/
@interface WWTerrainSharedGeometry : NSObject

@property (nonatomic) int numTexCoords;
@property (nonatomic) float* texCoords;
@property (nonatomic) int numIndices;
@property (nonatomic) short* indices;
@property (nonatomic) short* wireframeIndices;
@property (nonatomic) int numWireframeIndices;
@property (nonatomic) short* outlineIndices;
@property (nonatomic) int numOutlineIndices;
@property (nonatomic) NSString* texCoordVboCacheKey;
@property (nonatomic) NSString* indicesVboCacheKey;

- (WWTerrainSharedGeometry*) init;

@end