/*
 * Copyright (C) 2011 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package gov.nasa.worldwind.render;

import gov.nasa.worldwind.avlist.AVList;

/**
 * @author dcollins
 * @version $Id: GpuTextureFactory.java 59 2011-09-20 18:41:08Z dcollins $
 */
public interface GpuTextureFactory
{
    GpuTextureData createTextureData(Object source, AVList params);

    GpuTexture createTexture(DrawContext dc, GpuTextureData textureData, AVList params);
}
