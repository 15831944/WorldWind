/* Copyright (C) 2001, 2012 United States Government as represented by 
the Administrator of the National Aeronautics and Space Administration. 
All Rights Reserved.
*/
package gov.nasa.worldwindx.examples.performance;

/**
 * @author dcollins
 * @version $Id: PerformanceTest.java 775 2012-09-14 22:11:44Z dcollins $
 */
public interface PerformanceTest
{
    public class PerformanceTestResult
    {
        public long frameCount;
        public long elapsedTime;
    }

    void beginTest();

    void endTest();

    boolean hasNextFrame();

    void drawNextFrame();

    PerformanceTestResult getResult();
}
