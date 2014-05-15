/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.
 
 @version $Id: ScaleBarView.h 1664 2013-10-11 18:45:05Z tgaskins $
 */

#import <Foundation/Foundation.h>
#import "WorldWindViewDelegate.h"

@class WorldWindView;


@interface ScaleBarView : UIView <WorldWindViewDelegate>

- (ScaleBarView*) initWithFrame:(CGRect)frame worldWindView:(WorldWindView*)worldWindView;

@end