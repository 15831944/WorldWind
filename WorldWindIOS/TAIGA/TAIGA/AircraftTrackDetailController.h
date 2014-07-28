/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.

 @version $Id: AircraftTrackDetailController.h 2167 2014-07-22 22:27:58Z dcollins $
 */

#import <Foundation/Foundation.h>

@class AircraftTrackLayer;

@interface AircraftTrackDetailController : UITableViewController
{
@protected
    NSMutableArray* tableCells;
}

@property (readonly, nonatomic, weak) AircraftTrackLayer* layer;

- (id) initWithLayer:(AircraftTrackLayer*)layer;

@end