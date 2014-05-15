/*
 * Copyright (C) 2011 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package gov.nasa.worldwind.event;

import android.view.*;
import android.view.View;
import gov.nasa.worldwind.*;

/**
 * @author dcollins
 * @version $Id: NoOpInputHandler.java 59 2011-09-20 18:41:08Z dcollins $
 */
public class NoOpInputHandler extends WWObjectImpl implements InputHandler
{
    public NoOpInputHandler()
    {
    }

    public WorldWindow getEventSource()
    {
        return null;
    }

    public void setEventSource(WorldWindow eventSource)
    {
    }

    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        return false;
    }
}
