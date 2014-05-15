/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.

 @version $Id: WaypointLayer.h 1881 2014-03-28 18:51:19Z dcollins $
 */

#import <Foundation/Foundation.h>
#import "WorldWind/Layer/WWRenderableLayer.h"

@class WaypointDatabase;

@interface WaypointLayer : WWRenderableLayer

- (WaypointLayer*) init;

- (void) setWaypointDatabase:(WaypointDatabase*)waypointDatabase;

@end