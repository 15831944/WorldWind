/*
 * Copyright (C) 2011 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package gov.nasa.worldwind;

/**
 * General factory interface.
 *
 * @author dcollins
 * @version $Id: Factory.java 59 2011-09-20 18:41:08Z dcollins $
 */
public interface Factory
{
    /**
     * Creates an object from a general configuration source.
     *
     * @param configSource the configuration source.
     *
     * @return the new object.
     *
     * @throws IllegalArgumentException if the configuration source is null or an empty string.
     * @throws gov.nasa.worldwind.exception.WWUnrecognizedException
     *                                  if the type of source or some object-specific value is unrecognized.
     * @throws gov.nasa.worldwind.exception.WWRuntimeException
     *                                  if object creation fails. The exception indicating the source of the failure is
     *                                  included as the {@link Exception#initCause(Throwable)}.
     */
    Object createFromConfigSource(Object configSource);
}