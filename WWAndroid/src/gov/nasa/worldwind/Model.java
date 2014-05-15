/*
 * Copyright (C) 2011 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package gov.nasa.worldwind;

import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.layers.LayerList;

/**
 * @author dcollins
 * @version $Id: Model.java 59 2011-09-20 18:41:08Z dcollins $
 */
public interface Model extends WWObject
{
    Globe getGlobe();

    void setGlobe(Globe globe);

    LayerList getLayers();

    void setLayers(LayerList layers);
}
