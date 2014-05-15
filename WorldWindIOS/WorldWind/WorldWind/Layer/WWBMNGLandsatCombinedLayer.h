/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.
 
 @version $Id: WWBMNGLandsatCombinedLayer.h 1460 2013-06-19 19:43:49Z tgaskins $
 */

#import <Foundation/Foundation.h>
#import "WorldWind/Layer/WWTiledImageLayer.h"

/**
* Provides a layer combining the Blue Marble Next Generation and I3 Landsat imagery.
*/
@interface WWBMNGLandsatCombinedLayer : WWTiledImageLayer

/// @name Initializing the Layer

/**
* Initializes the layer.
*
* @return The initialized layer.
*/
- (WWBMNGLandsatCombinedLayer*) init;

@end