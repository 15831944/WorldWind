/*
 * Copyright (C) 2012 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package gov.nasa.worldwind.terrain;

import gov.nasa.worldwind.avlist.AVList;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.util.Level;
import gov.nasa.worldwind.util.TileKey;

import org.w3c.dom.Element;

public class AdaptiveElevationModel extends BasicElevationModel
{

	public AdaptiveElevationModel(BasicElevationModel em)
	{
		super(em.copy());
	}
	
	public AdaptiveElevationModel(AVList params)
	{
		super(params);
	}
	
	public AdaptiveElevationModel(Element domElement, AVList params)
	{
		super(domElement, params);
	}

	public void fixElevation(Position position)
	{
        
		if (!this.contains(position.latitude, position.longitude))
            return ;
        
        Level lastLevel = this.levels.getLastLevel(position.latitude, position.longitude);
        final TileKey tileKey = new TileKey(position.latitude, position.longitude, this.levels, lastLevel.getLevelNumber());
        ElevationTile tile = this.getTileFromMemory(tileKey);

        if (tile == null)
        {
            int fallbackRow = tileKey.getRow();
            int fallbackCol = tileKey.getColumn();
            for (int fallbackLevelNum = tileKey.getLevelNumber() - 1; fallbackLevelNum >= 0; fallbackLevelNum--)
            {
                fallbackRow /= 2;
                fallbackCol /= 2;

                TileKey fallbackKey = new TileKey(fallbackLevelNum,
                    fallbackRow, fallbackCol, this.levels.getLevel(fallbackLevelNum).getCacheName());
                tile = this.getTileFromMemory(fallbackKey);
                if (tile != null)
                    break;
            }
        }

        if (tile == null && !this.levels.getFirstLevel().isEmpty())
        {
            // Request the level-zero tile since it's not in memory
            Level firstLevel = this.levels.getFirstLevel();
            final TileKey zeroKey = new TileKey(position.latitude, position.longitude, this.levels, firstLevel.getLevelNumber());
            this.requestTile(zeroKey);
        }

        if(tile != null)
        {
        	int i = tile.computeElevationIndex(new LatLon(position.latitude, position.longitude));
        	tile.elevations[i] = (short) position.elevation;
        }
        
        
    }

}