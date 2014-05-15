/*
 Copyright (C) 2013 United States Government as represented by the Administrator of the
 National Aeronautics and Space Administration. All Rights Reserved.
 
 @version $Id: WWDisposable.h 1469 2013-06-20 23:27:00Z tgaskins $
 */

/**
* A protocol implemented by all objects that must explicitly deallocate resources when the object is no longer
* needed. The relevant resources vary with the class and class instance.
*/
@protocol WWDisposable

/**
* Deallocate resources specific to the object.
*/
- (void) dispose;

@end