/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.

 @version $Id: FlightRouteDetailController.h 1995 2014-05-14 18:22:40Z dcollins $
 */

#import <Foundation/Foundation.h>

@class FlightRoute;
@class WaypointFileControl;
@class BulkRetrieverController;
@class WorldWindView;

@interface FlightRouteDetailController : UIViewController<UITableViewDataSource, UITableViewDelegate>
{
@protected
    UITableView* flightRouteTable;
    WaypointFileControl* waypointFileControl;
    BulkRetrieverController* bulkRetrieverController;
    NSArray* normalConstraints;
    NSArray* editingConstraints;
}

@property (nonatomic, readonly, weak) FlightRoute* flightRoute;

@property (nonatomic, readonly, weak) WorldWindView* wwv;

- (id) initWithFlightRoute:(FlightRoute*)flightRoute worldWindView:(WorldWindView*)wwv;

@end