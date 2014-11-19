/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.
 
 @version $Id: LayerListController.h 2356 2014-10-01 18:16:52Z tgaskins $
 */

#import <Foundation/Foundation.h>

@class WorldWindView;

@interface LayerListController : UITableViewController

@property (nonatomic, readonly) WorldWindView* wwv;

- (LayerListController*) initWithWorldWindView:(WorldWindView*)wwv;

- (void) flashScrollIndicator;

@end