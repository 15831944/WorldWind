/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.
 
 @version $Id: WeatherCamLayer.h 1999 2014-05-14 23:55:07Z tgaskins $
 */

#import <Foundation/Foundation.h>
#import "WWRenderableLayer.h"


@interface WeatherCamLayer :  WWRenderableLayer <NSXMLParserDelegate>

@property (atomic) NSNumber* refreshInProgress;

- (WeatherCamLayer*) init;

@end