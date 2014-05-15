/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.

 @version $Id: AircraftLayer.h 1800 2014-01-07 18:25:24Z dcollins $
 */

#import <Foundation/Foundation.h>
#import "WorldWind/Layer/WWLayer.h"

@class FlightRoute;

@interface AircraftLayer : WWLayer
{
@protected
    id aircraftShape;
    id simulatedFlightRoute;
}

- (AircraftLayer*) init;

@end