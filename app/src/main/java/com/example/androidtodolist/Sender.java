package com.example.androidtodolist;


import android.content.ComponentName;
import android.content.Intent;

import java.util.Date;

public class Sender extends Thread
{
    Task task;
    long timeToSleep;
    MainActivity activity;

    public Sender(MainActivity activity, Task task, long timeToSleep)
    {
        this.activity = activity;
        this.task = task;
        this.timeToSleep = timeToSleep;
    }

    public void run()
    {
        try
        {
            if(timeToSleep <= 0 ) return;
            System.out.println("Thread1");
            System.out.println("Time to sleep "+timeToSleep);
            if( timeToSleep > 0)
                sleep(timeToSleep);
            System.out.println("Thread2");
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.example.androidtodolist", "com.example.androidtodolist.ReminderBroadcast"));
            intent.putExtra("title", task.getTitle());
            intent.putExtra("category",task.getCategory());
            intent.putExtra("date", task.getToDoDate());
            intent.putExtra("message", "Expires at ");

            activity.sendBroadcast(intent);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}