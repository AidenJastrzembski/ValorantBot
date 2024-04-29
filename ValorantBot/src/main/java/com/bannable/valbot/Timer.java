package com.bannable.valbot;

public class Timer extends ValorantMatchData {
    public static void Timer(double minutes) throws InterruptedException {
        Thread.sleep((long) (1*1000*60*minutes));
        timesRan++;

    }
}
