/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.
 
 @version $Id: SettingsScreenController.h 1980 2014-05-03 03:25:46Z tgaskins $
 */

#import <Foundation/Foundation.h>

@interface SettingsScreenController : UIViewController <UITableViewDelegate, UITableViewDataSource, UITextFieldDelegate>

- (SettingsScreenController*) initWithFrame:(CGRect)frame;

@end