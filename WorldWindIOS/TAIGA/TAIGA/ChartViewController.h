/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.
 
 @version $Id: ChartViewController.h 1597 2013-09-13 20:19:17Z tgaskins $
 */

#import <Foundation/Foundation.h>


@interface ChartViewController : UIViewController <UIScrollViewDelegate>

@property (nonatomic, readonly) UIImageView* imageView;

- (ChartViewController*) initWithFrame:(CGRect)frame;

@end