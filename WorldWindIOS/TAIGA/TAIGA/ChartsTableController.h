/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.
 
 @version $Id: ChartsTableController.h 1725 2013-11-20 00:58:35Z tgaskins $
 */

#import <Foundation/Foundation.h>


@interface ChartsTableController : UIViewController <UISearchBarDelegate>

- (ChartsTableController*) initWithParent:(id)parent;

- (void) selectChart:(NSString*)chartFileName chartName:(NSString*)chartName;

@end