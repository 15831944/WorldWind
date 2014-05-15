/*
 * Copyright (C) 2011 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package gov.nasa.worldwind.retrieve;

/**
 * @author Tom Gaskins
 * @version $Id: RetrievalFuture.java 59 2011-09-20 18:41:08Z dcollins $
 */
public interface RetrievalFuture extends java.util.concurrent.Future<Retriever>
{
    Retriever getRetriever();
}
