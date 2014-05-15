/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.
 
 @version $Id: DotsView.h 1750 2013-12-12 19:19:02Z tgaskins $
 */

#import <Foundation/Foundation.h>


@interface DotsView : UIView

@property (nonatomic, readonly) CGPoint viewCenter;
@property (nonatomic, readonly) int dotCount;
@property (nonatomic) int dotSize;
@property (nonatomic) int highlightedDot;

- (DotsView*)initWithCenter:(CGPoint)center dotCount:(int)dotCount;

@end