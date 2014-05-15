/*
 * Copyright (C) 2011 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package gov.nasa.worldwind.cache;

import gov.nasa.worldwind.util.PerformanceStatistic;

import java.util.Collection;

/**
 * @author dcollins
 * @version $Id: MemoryCacheSet.java 59 2011-09-20 18:41:08Z dcollins $
 */
public interface MemoryCacheSet
{
    MemoryCache get(String key);

    MemoryCache put(String key, MemoryCache cache);

    boolean contains(String key);

    void clear();

    Collection<PerformanceStatistic> getPerformanceStatistics();
}
