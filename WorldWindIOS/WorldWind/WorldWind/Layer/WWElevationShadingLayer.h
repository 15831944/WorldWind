/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.
 
 @version $Id: WWElevationShadingLayer.h 1579 2013-09-04 23:32:54Z tgaskins $
 */

#import <Foundation/Foundation.h>
#import "WWLayer.h"

@interface WWElevationShadingLayer : WWLayer

/// @name Attributes

/// The elevation threshold above which to display yellow shading. The default is 2000 meters.
@property (nonatomic) float yellowThreshold;

/// The elevation threshold above which to display red shading. The default is 3000 meters.
@property (nonatomic) float redThreshold;

- (WWElevationShadingLayer*) init;

@end