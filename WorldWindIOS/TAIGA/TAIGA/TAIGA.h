/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.
 
 @version $Id: TAIGA.h 1995 2014-05-14 18:22:40Z dcollins $
 */

#import <Foundation/Foundation.h>

@class AppUpdateController;
@class UnitsFormatter;
@class WaypointDatabase;

@interface TAIGA : NSObject

+ (AppUpdateController*) appUpdateController;

+ (UnitsFormatter*) unitsFormatter;

+ (WaypointDatabase*) waypointDatabase;

@end