/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.
 
 @version $Id: WeatherScreenController.h 1849 2014-02-24 23:59:16Z tgaskins $
 */

#import <Foundation/Foundation.h>

@class WorldWindView;

@interface WeatherScreenController : UIViewController <UIGestureRecognizerDelegate,  UIScrollViewDelegate>

- (WeatherScreenController*) initWithFrame:(CGRect)frame;

@end