/*
 Copyright (C) 2014 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.
 
 @version $Id: ViewController.h 1888 2014-04-02 00:43:51Z tgaskins $
 */

#import <UIKit/UIKit.h>

@class WorldWindView;

@interface ViewController : UIViewController

@property (nonatomic, readonly) WorldWindView* wwv;

@end
