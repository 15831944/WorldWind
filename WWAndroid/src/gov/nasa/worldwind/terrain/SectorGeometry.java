/*
 * Copyright (C) 2011 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package gov.nasa.worldwind.terrain;

import android.graphics.Point;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.render.DrawContext;

/**
 * @author dcollins
 * @version $Id: SectorGeometry.java 75 2011-09-29 17:06:26Z dcollins $
 */
public interface SectorGeometry
{
    Sector getSector();

    Extent getExtent();

    void render(DrawContext dc);

    void renderWireframe(DrawContext dc);

    void renderOutline(DrawContext dc);

    void beginRendering(DrawContext dc);

    void endRendering(DrawContext dc);

    void pick(DrawContext dc, Point pickPoint);
}
