/*
 * Copyright (C) 2011 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package gov.nasa.worldwind.util;

import java.net.*;

/**
 * @version $Id: TileUrlBuilder.java 67 2011-09-26 21:25:27Z pabercrombie $
 */
public interface TileUrlBuilder
{
    URL getURL(Tile tile, String imageFormat) throws MalformedURLException;
}
