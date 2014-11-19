/*
 * Copyright (C) 2014 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration. All Rights Reserved.
 */
/**
 * @version $Id: WorldWind.js 2439 2014-11-19 01:59:25Z tgaskins $
 */
define(['src/WorldWindow'], function (WorldWindow) {
    "use strict";

    var WorldWind = {
        VERSION: 0.0
    };

    WorldWind['WorldWindow'] = WorldWindow;

    window.WorldWind = WorldWind;

    return WorldWind;
});