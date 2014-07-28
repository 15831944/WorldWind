/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.

 @version $Id: AircraftLayer.h 2167 2014-07-22 22:27:58Z dcollins $
 */

#import <Foundation/Foundation.h>
#import "WorldWind/Layer/WWLayer.h"

@interface AircraftLayer : WWLayer
{
@protected
    id aircraftShape;
}

- (id) init;

@end