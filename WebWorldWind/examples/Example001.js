/*
 * Copyright (C) 2014 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration. All Rights Reserved.
 */
/**
 * @version $Id: Example001.js 2439 2014-11-19 01:59:25Z tgaskins $
 */
requirejs.config({
    baseUrl : ".."
});

requirejs(['src/WorldWind'], function () {
    "use strict";

    var wwd = new WorldWind.WorldWindow("canvasOne");
    wwd.drawScreen();
});