/*
 * Copyright (C) 2011 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package gov.nasa.worldwind.util;

import gov.nasa.worldwind.WWObjectImpl;
import gov.nasa.worldwind.avlist.*;
import gov.nasa.worldwind.geom.*;
import org.w3c.dom.Element;

import javax.xml.xpath.XPath;
import java.net.*;
import java.util.*;

/**
 * @author dcollins
 * @version $Id: LevelSet.java 67 2011-09-26 21:25:27Z pabercrombie $
 */
public class LevelSet extends WWObjectImpl
{
    public static final class SectorResolution
    {
        protected final int levelNumber;
        protected final Sector sector;

        public SectorResolution(Sector sector, int levelNumber)
        {
            this.levelNumber = levelNumber;
            this.sector = sector;
        }

        public final int getLevelNumber()
        {
            return this.levelNumber;
        }

        public final Sector getSector()
        {
            return this.sector;
        }
    }

    protected final Sector sector;
    protected final LatLon levelZeroTileDelta;
    protected final LatLon tileOrigin;
    protected final int numLevelZeroColumns;
    protected final java.util.ArrayList<Level> levels = new java.util.ArrayList<Level>();
    protected final SectorResolution[] sectorLevelLimits;

    public LevelSet(AVList params)
    {
        StringBuffer sb = new StringBuffer();

        Object o = params.getValue(AVKey.LEVEL_ZERO_TILE_DELTA);
        if (o == null || !(o instanceof LatLon))
            sb.append(Logging.getMessage("term.tileDelta")).append(" ");

        o = params.getValue(AVKey.SECTOR);
        if (o == null || !(o instanceof Sector))
            sb.append(Logging.getMessage("term.sector")).append(" ");

        int numLevels = 0;
        o = params.getValue(AVKey.NUM_LEVELS);
        if (o == null || !(o instanceof Integer) || (numLevels = (Integer) o) < 1)
            sb.append(Logging.getMessage("term.numLevels")).append(" ");

        int numEmptyLevels = 0;
        o = params.getValue(AVKey.NUM_EMPTY_LEVELS);
        if (o != null && o instanceof Integer && (Integer) o > 0)
            numEmptyLevels = (Integer) o;

        String[] inactiveLevels = null;
        o = params.getValue(AVKey.INACTIVE_LEVELS);
        if (o != null && !(o instanceof String))
            sb.append(Logging.getMessage("term.inactiveLevels")).append(" ");
        else if (o != null)
            inactiveLevels = ((String) o).split(",");

        SectorResolution[] sectorLimits = null;
        o = params.getValue(AVKey.SECTOR_RESOLUTION_LIMITS);
        if (o != null && !(o instanceof SectorResolution[]))
        {
            sb.append(Logging.getMessage("term.sectorResolutionLimits")).append(" ");
        }
        else if (o != null)
        {
            sectorLimits = (SectorResolution[]) o;
            for (SectorResolution sr : sectorLimits)
            {
                if (sr.levelNumber > numLevels - 1)
                {
                    String message =
                        Logging.getMessage("LevelSet.sectorResolutionLimitsTooHigh", sr.levelNumber, numLevels - 1);
                    Logging.warning(message);
                    break;
                }
            }
        }
        this.sectorLevelLimits = sectorLimits;

        if (sb.length() > 0)
        {
            String message = Logging.getMessage("layers.LevelSet.InvalidLevelDescriptorFields", sb.toString());
            Logging.error(message);
            throw new IllegalArgumentException(message);
        }

        this.sector = (Sector) params.getValue(AVKey.SECTOR);
        this.levelZeroTileDelta = (LatLon) params.getValue(AVKey.LEVEL_ZERO_TILE_DELTA);

        o = params.getValue(AVKey.TILE_ORIGIN);
        if (o != null && o instanceof LatLon)
            this.tileOrigin = (LatLon) o;
        else
            this.tileOrigin = LatLon.fromDegrees(-90, -180);

        params = params.copy(); // copy so as not to modify the user's params

        TileUrlBuilder tub = (TileUrlBuilder) params.getValue(AVKey.TILE_URL_BUILDER);
        if (tub == null)
        {
            params.setValue(AVKey.TILE_URL_BUILDER, new TileUrlBuilder()
            {
                public URL getURL(Tile tile, String altImageFormat) throws MalformedURLException
                {
                    String service = tile.getLevel().getService();
                    if (service == null || service.length() < 1)
                        return null;

                    StringBuffer sb = new StringBuffer(tile.getLevel().getService());
                    if (sb.lastIndexOf("?") != sb.length() - 1)
                        sb.append("?");
                    sb.append("T=");
                    sb.append(tile.getLevel().getDataset());
                    sb.append("&L=");
                    sb.append(tile.getLevel().getLevelName());
                    sb.append("&X=");
                    sb.append(tile.getColumn());
                    sb.append("&Y=");
                    sb.append(tile.getRow());

                    // Convention for NASA WWN tiles is to request them with common dataset name but without dds.
                    return new URL(altImageFormat == null ? sb.toString() : sb.toString().replace("dds", ""));
                }
            });
        }

        if (this.sectorLevelLimits != null)
        {
            Arrays.sort(this.sectorLevelLimits, new Comparator<SectorResolution>()
            {
                public int compare(SectorResolution sra, SectorResolution srb)
                {
                    // sort order is deliberately backwards in order to list higher-resolution sectors first
                    return sra.levelNumber < srb.levelNumber ? 1 : sra.levelNumber == srb.levelNumber ? 0 : -1;
                }
            });
        }

        // Compute the number of level zero columns. This value is guaranteed to be a nonzero number, since there is
        // generally at least one level zero tile.
        int firstLevelZeroCol = Tile.computeColumn(this.levelZeroTileDelta.longitude,
            this.sector.minLongitude, this.tileOrigin.longitude);
        int lastLevelZeroCol = Tile.computeColumn(this.levelZeroTileDelta.longitude, this.sector.maxLongitude,
            this.tileOrigin.longitude);
        this.numLevelZeroColumns = Math.max(1, lastLevelZeroCol - firstLevelZeroCol + 1);

        for (int i = 0; i < numLevels; i++)
        {
            params.setValue(AVKey.LEVEL_NAME, i < numEmptyLevels ? "" : Integer.toString(i - numEmptyLevels));
            params.setValue(AVKey.LEVEL_NUMBER, i);

            Angle latDelta = this.levelZeroTileDelta.latitude.divide(Math.pow(2, i));
            Angle lonDelta = this.levelZeroTileDelta.longitude.divide(Math.pow(2, i));
            params.setValue(AVKey.TILE_DELTA, new LatLon(latDelta, lonDelta));

            this.levels.add(new Level(params));
        }

        if (inactiveLevels != null)
        {
            for (String s : inactiveLevels)
            {
                int i = Integer.parseInt(s);
                this.getLevel(i).setActive(false);
            }
        }
    }

    public LevelSet(LevelSet source)
    {
        if (source == null)
        {
            String msg = Logging.getMessage("nullValue.LevelSetIsNull");
            Logging.error(msg);
            throw new IllegalArgumentException(msg);
        }

        this.sector = source.sector;
        this.levelZeroTileDelta = source.levelZeroTileDelta;
        this.tileOrigin = source.tileOrigin;
        this.numLevelZeroColumns = source.numLevelZeroColumns;
        this.sectorLevelLimits = source.sectorLevelLimits;

        for (Level level : source.levels)
        {
            this.levels.add(level); // Levels are final, so it's safe to copy references.
        }
    }

    // TODO make sure this is necessary. may be redundant to DataConfigurationUtils
    public static AVList paramsFromConfigDoc(Element element)
    {
        if (element == null)
        {
            String msg = Logging.getMessage("nullValue.ElementIsNull");
            Logging.error(msg);
            throw new IllegalArgumentException(msg);
        }

        AVList params = new AVListImpl();
        XPath xpath = WWXML.makeXPath();

        params.setValue(AVKey.SECTOR, WWXML.getSector(element, "Sector", xpath));
        // TODO would this make more sense as AVKey.GET_MAP_URL?
        params.setValue(AVKey.SERVICE, WWXML.getText(element, "Service/GetMapURL", xpath));
        params.setValue(AVKey.LEVEL_ZERO_TILE_DELTA, WWXML.getLatLon(element, "LevelZeroTileDelta/LatLon", xpath));
        params.setValue(AVKey.TILE_WIDTH, WWXML.getInteger(element, "TileSize/Dimension/@width", xpath));
        params.setValue(AVKey.TILE_HEIGHT, WWXML.getInteger(element, "TileSize/Dimension/@height", xpath));
        params.setValue(AVKey.NUM_LEVELS, WWXML.getInteger(element, "NumLevels/@count", xpath));
        params.setValue(AVKey.NUM_EMPTY_LEVELS, WWXML.getInteger(element, "NumLevels/@numEmpty", xpath));
        params.setValue(AVKey.DATA_CACHE_NAME, WWXML.getText(element, "DataCacheName", xpath));
        params.setValue(AVKey.FORMAT_SUFFIX, WWXML.getText(element, "FormatSuffix", xpath));

        return params;
    }

    @Override
    public Object setValue(String key, Object value)
    {
        // Propogate the setting to all levels
        for (Level level : this.levels)
        {
            level.setValue(key, value);
        }

        return super.setValue(key, value);
    }

    @Override
    public Object getValue(String key)
    {
        Object value = super.getValue(key);

        if (value != null)
            return value;

        // See if any level has it
        for (Level level : this.getLevels())
        {
            if (level != null && (value = level.getValue(key)) != null)
                return value;
        }

        return null;
    }

    public final Sector getSector()
    {
        return this.sector;
    }

    public final LatLon getLevelZeroTileDelta()
    {
        return this.levelZeroTileDelta;
    }

    public final LatLon getTileOrigin()
    {
        return this.tileOrigin;
    }

    public final SectorResolution[] getSectorLevelLimits()
    {
        if (this.sectorLevelLimits == null)
            return null;

        // The SectorResolution instances themselves are immutable. However the entries in a Java array cannot be made
        // immutable, therefore we create a copy to insulate ourselves from changes by the caller.
        SectorResolution[] copy = new SectorResolution[this.sectorLevelLimits.length];
        System.arraycopy(this.sectorLevelLimits, 0, copy, 0, this.sectorLevelLimits.length);

        return copy;
    }

    public final ArrayList<Level> getLevels()
    {
        return this.levels;
    }

    public final Level getLevel(int levelNumber)
    {
        return (levelNumber >= 0 && levelNumber < this.levels.size()) ? this.levels.get(levelNumber) : null;
    }

    public final int getNumLevels()
    {
        return this.levels.size();
    }

    public final Level getFirstLevel()
    {
        return this.getLevel(0);
    }

    public final Level getLastLevel()
    {
        return this.getLevel(this.getNumLevels() - 1);
    }

    public final Level getNextToLastLevel()
    {
        return this.getLevel(this.getNumLevels() > 1 ? this.getNumLevels() - 2 : 0);
    }

    public final Level getLastLevel(Sector sector)
    {
        if (sector == null)
        {
            String msg = Logging.getMessage("nullValue.SectorIsNull");
            Logging.error(msg);
            throw new IllegalArgumentException(msg);
        }

        if (!this.getSector().intersects(sector))
            return null;

        Level level = this.getLevel(this.getNumLevels() - 1);

        if (this.sectorLevelLimits != null)
            for (SectorResolution sr : this.sectorLevelLimits)
            {
                if (sr.sector.intersects(sector) && sr.levelNumber <= level.getLevelNumber())
                {
                    level = this.getLevel(sr.levelNumber);
                    break;
                }
            }

        return level;
    }

    public final Level getLastLevel(Angle latitude, Angle longitude)
    {
        Level level = this.getLevel(this.getNumLevels() - 1);

        if (this.sectorLevelLimits != null)
            for (SectorResolution sr : this.sectorLevelLimits)
            {
                if (sr.sector.contains(latitude, longitude) && sr.levelNumber <= level.getLevelNumber())
                {
                    level = this.getLevel(sr.levelNumber);
                    break;
                }
            }

        return level;
    }

    public final boolean isFinalLevel(int levelNum)
    {
        return levelNum == this.getNumLevels() - 1;
    }

    public final boolean isLevelEmpty(int levelNumber)
    {
        return this.levels.get(levelNumber).isEmpty();
    }

    protected int numColumnsInLevel(Level level)
    {
        int levelDelta = level.getLevelNumber() - this.getFirstLevel().getLevelNumber();
        double twoToTheN = Math.pow(2, levelDelta);
        return (int) (twoToTheN * this.numLevelZeroColumns);
    }

    protected long getTileNumber(Tile tile)
    {
        return tile.getRow() < 0 ? -1 : tile.getRow() * this.numColumnsInLevel(tile.getLevel()) + tile.getColumn();
    }

    protected long getTileNumber(TileKey tileKey)
    {
        return tileKey.getRow() < 0 ? -1 :
            tileKey.getRow() * this.numColumnsInLevel(this.getLevel(tileKey.getLevelNumber())) + tileKey.getColumn();
    }

    /**
     * Instructs the level set that a tile is likely to be absent.
     *
     * @param tile The tile to mark as having an absent resource.
     *
     * @throws IllegalArgumentException if <code>tile</code> is null
     */
    public final void markResourceAbsent(Tile tile)
    {
        if (tile == null)
        {
            String msg = Logging.getMessage("nullValue.TileIsNull");
            Logging.error(msg);
            throw new IllegalArgumentException(msg);
        }

        tile.getLevel().markResourceAbsent(this.getTileNumber(tile));
    }

    /**
     * Indicates whether a tile has been marked as absent.
     *
     * @param tileKey The key of the tile in question.
     *
     * @return <code>true</code> if the tile is marked absent, otherwise <code>false</code>.
     *
     * @throws IllegalArgumentException if <code>tile</code> is null
     */
    public final boolean isResourceAbsent(TileKey tileKey)
    {
        if (tileKey == null)
        {
            String msg = Logging.getMessage("nullValue.TileKeyIsNull");
            Logging.error(msg);
            throw new IllegalArgumentException(msg);
        }

        Level level = this.getLevel(tileKey.getLevelNumber());
        return level.isEmpty() || level.isResourceAbsent(this.getTileNumber(tileKey));
    }

    /**
     * Indicates whether a tile has been marked as absent.
     *
     * @param tile The tile in question.
     *
     * @return <code>true</code> if the tile is marked absent, otherwise <code>false</code>.
     *
     * @throws IllegalArgumentException if <code>tile</code> is null
     */
    public final boolean isResourceAbsent(Tile tile)
    {
        if (tile == null)
        {
            String msg = Logging.getMessage("nullValue.TileIsNull");
            Logging.error(msg);
            throw new IllegalArgumentException(msg);
        }

        return tile.getLevel().isEmpty() || tile.getLevel().isResourceAbsent(this.getTileNumber(tile));
    }

    /**
     * Removes the absent-tile mark associated with a tile, if one is associatied.
     *
     * @param tile The tile to unmark.
     *
     * @throws IllegalArgumentException if <code>tile</code> is null
     */
    public final void unmarkResourceAbsent(Tile tile)
    {
        if (tile == null)
        {
            String msg = Logging.getMessage("nullValue.TileIsNull");
            Logging.error(msg);
            throw new IllegalArgumentException(msg);
        }

        tile.getLevel().unmarkResourceAbsent(this.getTileNumber(tile));
    }

    // Create the tile corresponding to a specified key.
    public Sector computeSectorForKey(TileKey key)
    {
        if (key == null)
        {
            String msg = Logging.getMessage("nullValue.KeyIsNull");
            Logging.error(msg);
            throw new IllegalArgumentException(msg);
        }

        Level level = this.getLevel(key.getLevelNumber());

        // Compute the tile's SW lat/lon based on its row/col in the level's data set.
        Angle dLat = level.getTileDelta().latitude;
        Angle dLon = level.getTileDelta().longitude;
        Angle latOrigin = this.tileOrigin.latitude;
        Angle lonOrigin = this.tileOrigin.longitude;

        Angle minLatitude = Tile.computeRowLatitude(key.getRow(), dLat, latOrigin);
        Angle minLongitude = Tile.computeColumnLongitude(key.getColumn(), dLon, lonOrigin);

        return new Sector(minLatitude, minLatitude.add(dLat), minLongitude, minLongitude.add(dLon));
    }

    // Create the tile corresponding to a specified key.
    public Tile createTile(TileKey key)
    {
        if (key == null)
        {
            String msg = Logging.getMessage("nullValue.KeyIsNull");
            Logging.error(msg);
            throw new IllegalArgumentException(msg);
        }

        Level level = this.getLevel(key.getLevelNumber());

        // Compute the tile's SW lat/lon based on its row/col in the level's data set.
        Angle dLat = level.getTileDelta().latitude;
        Angle dLon = level.getTileDelta().longitude;
        Angle latOrigin = this.tileOrigin.latitude;
        Angle lonOrigin = this.tileOrigin.longitude;

        Angle minLatitude = Tile.computeRowLatitude(key.getRow(), dLat, latOrigin);
        Angle minLongitude = Tile.computeColumnLongitude(key.getColumn(), dLon, lonOrigin);

        Sector tileSector = new Sector(minLatitude, minLatitude.add(dLat), minLongitude, minLongitude.add(dLon));

        return new Tile(tileSector, level, key.getRow(), key.getColumn());
    }

    public void setExpiryTime(long expiryTime)
    {
        for (Level level : this.levels)
        {
            level.setExpiryTime(expiryTime);
        }
    }
}
