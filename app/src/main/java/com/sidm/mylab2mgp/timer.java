package com.sidm.mylab2mgp;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by - on 5/12/2016.
 */

public class timer {
    static private int timerpassed = 0;

    static private Timer myTimer = new Timer();
    static private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            timerpassed++;
        }
    };

    public timer ()
    {
        timerpassed = 0;
    }
    public timer (int i)
    {
        timerpassed = i;
    }

    //public static void

    public static void startTimer(int delayMS, int rateMS)
    {
        myTimer.schedule(task,delayMS,rateMS);
    }

    public static int getTimeSeconds()
    {
        return timerpassed;
    }

}
