/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.
 
 @version $Id: FlightPathsLayer.h 1566 2013-08-29 22:57:33Z tgaskins $
 */

#import <Foundation/Foundation.h>
#import "WWRenderableLayer.h"

@class WorldWindView;

@interface FlightPathsLayer : WWRenderableLayer

- (FlightPathsLayer*) initWithPathsLocation:(NSString*)pathsLocation;

@end