/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.
 
 @version $Id: LayerListController.h 1742 2013-12-08 00:06:31Z tgaskins $
 */

#import <Foundation/Foundation.h>

@class WorldWindView;

@interface LayerListController : UITableViewController <UINavigationControllerDelegate>

@property (nonatomic, readonly) WorldWindView* wwv;

- (LayerListController*) initWithWorldWindView:(WorldWindView*)wwv;

- (void) flashScrollIndicator;

@end