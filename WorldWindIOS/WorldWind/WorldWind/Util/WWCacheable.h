/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.
 
 @version $Id: WWCacheable.h 1469 2013-06-20 23:27:00Z tgaskins $
 */

/**
* A protocol implemented by all cacheable objects.
*/
@protocol WWCacheable

/// The size of the cachable object, in bytes.
- (long) sizeInBytes;

@end