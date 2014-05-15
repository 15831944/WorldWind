/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.

 @version $Id: SimulationViewController.h 1814 2014-01-13 22:51:05Z dcollins $
 */

#import <Foundation/Foundation.h>

@class FlightRoute;
@class WorldWindView;

@interface SimulationViewController : UIViewController
{
@protected
    UIButton* doneButton;
    UILabel* titleLabel;
    UISlider* aircraftSlider;
}

@property (nonatomic, readonly) UIControl* doneControl;

@property (nonatomic) FlightRoute* flightRoute;

- (SimulationViewController*) init;

@end