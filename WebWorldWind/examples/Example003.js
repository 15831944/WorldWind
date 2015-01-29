/*
 * Copyright (C) 2014 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration. All Rights Reserved.
 */
/**
 * @version $Id: Example003.js 2625 2014-12-30 22:54:44Z tgaskins $
 */

requirejs(['../src/WorldWind',
        './LayerManager/LayerManager'],
    function (ww,
              LayerManager) {
        "use strict";

        WorldWind.Logger.setLoggingLevel(WorldWind.Logger.LEVEL_WARNING);

        var wwd = new WorldWind.WorldWindow("canvasOne");
        wwd.addLayer(new WorldWind.BMNGLandsatLayer());
        wwd.addLayer(new WorldWind.BingWMSLayer());
        wwd.redraw();

        var layerManger = new LayerManager('divLayerManager', wwd);
    });