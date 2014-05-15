/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.
 
 @version $Id: WeatherCamViewController.h 1744 2013-12-09 23:39:53Z tgaskins $
 */

#import <Foundation/Foundation.h>

@interface WeatherCamViewController : UIViewController <UIScrollViewDelegate>

-(WeatherCamViewController*)init;

- (void) setSiteInfo:(NSArray*) siteCameras;

@end