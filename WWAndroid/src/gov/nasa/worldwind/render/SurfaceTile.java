/*
 * Copyright (C) 2011 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package gov.nasa.worldwind.render;

import gov.nasa.worldwind.geom.*;

/**
 * @author dcollins
 * @version $Id: SurfaceTile.java 59 2011-09-20 18:41:08Z dcollins $
 */
public interface SurfaceTile
{
    Sector getSector();

    boolean bind(DrawContext dc);

    void applyInternalTransform(DrawContext dc, Matrix matrix);
}