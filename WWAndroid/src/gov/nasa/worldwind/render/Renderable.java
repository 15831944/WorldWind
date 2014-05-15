/*
 * Copyright (C) 2011 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package gov.nasa.worldwind.render;

/**
 * @author dcollins
 * @version $Id: Renderable.java 59 2011-09-20 18:41:08Z dcollins $
 */
public interface Renderable
{
    void render(DrawContext dc);
}
