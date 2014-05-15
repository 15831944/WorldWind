/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.
 
 @version $Id: AppUpdateController.h 1625 2013-09-20 20:11:57Z tgaskins $
 */

#import <Foundation/Foundation.h>

@interface AppUpdateController : NSObject <UIAlertViewDelegate>

- (AppUpdateController*) init;

- (void) checkForUpdate:(BOOL)force;

@end