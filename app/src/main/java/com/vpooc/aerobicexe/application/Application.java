package com.vpooc.aerobicexe.application;

import android.app.Activity;
import android.os.Process;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/31.
 */
public class Application extends android.app.Application {
    public static ArrayList<Activity>
            activityList=new ArrayList();

    public void finishActivity()
    {
        for (Activity activity:activityList)
        {
            activity.finish();
        }
        //结束进程 android.os.Process

        Process.killProcess(Process.myPid());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        Log.i("TApplication", "oncreate");
        CrashHandler crashHandler=
                new CrashHandler(this);

        Thread mainThread=Thread.currentThread();
        mainThread.
                setDefaultUncaughtExceptionHandler
                        (crashHandler);
    }
}
