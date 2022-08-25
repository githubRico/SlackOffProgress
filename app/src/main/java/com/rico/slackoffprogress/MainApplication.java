package com.rico.slackoffprogress;

import android.app.Application;

public class MainApplication extends Application
{
    @Override
    public void onCreate()
    {
        Constant.APPLICATION_PATH = this.getFilesDir().getPath();
        super.onCreate();

    }
}
