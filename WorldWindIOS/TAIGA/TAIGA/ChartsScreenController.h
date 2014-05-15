/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.
 
 @version $Id: ChartsScreenController.h 1720 2013-11-13 23:27:44Z tgaskins $
 */

#import <Foundation/Foundation.h>

@interface ChartsScreenController : UIViewController <UISearchBarDelegate>

- (ChartsScreenController*) initWithFrame:(CGRect)frame;

- (void) loadChart:(NSString*)chartPath chartName:(NSString*)chartName;

@end