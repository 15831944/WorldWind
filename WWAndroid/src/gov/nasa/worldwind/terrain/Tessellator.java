/*
 * Copyright (C) 2011 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package gov.nasa.worldwind.terrain;

import gov.nasa.worldwind.WWObject;
import gov.nasa.worldwind.render.DrawContext;

/**
 * @author dcollins
 * @version $Id: Tessellator.java 59 2011-09-20 18:41:08Z dcollins $
 */
public interface Tessellator extends WWObject
{
    SectorGeometryList tessellate(DrawContext dc);
}
