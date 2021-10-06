package com.diana.io.deliveroo;

/**
 * The Cron Parser application.
 * 
 * @author Diana Araujo
 *
 */
public class App
{
    /**
     * The program entry point.
     * 
     * @param args
     *            the program arguments
     *            <ul>
     *            <li>a string holding a Cron expression</li>
     *            </ul>
     */
    public static void main(String[] args)
    {
        CronParser.parse(args[0]);
    }
}
