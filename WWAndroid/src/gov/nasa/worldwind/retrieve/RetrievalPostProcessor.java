/*
 * Copyright (C) 2011 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package gov.nasa.worldwind.retrieve;

import java.nio.ByteBuffer;

/**
 * @author Tom Gaskins
 * @version $Id: RetrievalPostProcessor.java 59 2011-09-20 18:41:08Z dcollins $
 */
public interface RetrievalPostProcessor
{
    ByteBuffer run(Retriever retriever);
}
