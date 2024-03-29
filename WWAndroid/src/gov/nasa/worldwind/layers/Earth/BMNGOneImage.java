/*
 * Copyright (C) 2011 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package gov.nasa.worldwind.layers.Earth;

import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwind.layers.AbstractLayer;
import gov.nasa.worldwind.render.*;

/**
 * @author dcollins
 * @version $Id: BMNGOneImage.java 75 2011-09-29 17:06:26Z dcollins $
 */
public class BMNGOneImage extends AbstractLayer
{
    protected static final String IMAGE_PATH = "images/world.topo.bathy.200405.3x2048x1024.dds";

    protected SurfaceImage surfaceImage;

    public BMNGOneImage()
    {
        this.setName("layers.Earth.BlueMarbleOneImageLayer.Name");
        this.surfaceImage = new SurfaceImage(IMAGE_PATH, Sector.fromFullSphere());
    }

    @Override
    protected void doRender(DrawContext dc)
    {
        this.surfaceImage.render(dc);
    }
}
