package com.vpooc.aerobicexe.application;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.vpooc.aerobicexe.activity.MainActivity;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Administrator on 2016/5/31.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    Application tApplication;

    public CrashHandler(Application tApplication) {
        this.tApplication = tApplication;
    }

    // 程序任何地方只要出了异常，又没有加trycatch
    @Override
    public void uncaughtException(Thread thread, Throwable e) {

        // 得到异常信息
        // 打到用户真机的logcat,程序员看不到
        // e.printStackTrace();
        String str = "";
        //得到了一行信息
        //str = e.getMessage();
        //得到详细的异常信息
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        //把异常信息打印到printWtriter,再打到stringWriter
        e.printStackTrace(printWriter);
        str = stringWriter.toString();
        Log.i("出异常", str);
        // 联网发送

        // toast显示一个提示
        new Thread() {
            public void run() {
                //主线程自己有队列有looper,工作线程没有
                //toast.show用到了队列，就得有looper
                //prepare准备，创建一个looper
                Looper.prepare();
                Toast.makeText(tApplication, "程序将重启", Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            ;
        }.start();
        //为了显示toast,晚一点结束当前线程
        try {
            Thread.currentThread().sleep(2000);
        } catch (Exception e2) {
            // TODO: handle exception
        }
        // 自动启动maintivity
        Intent intent = new Intent(tApplication, MainActivity.class);
        //tApplication.startActivity(intent);
        PendingIntent pendingIntent = PendingIntent.getActivity(tApplication, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //过一会儿自动重启
        //Alarm定时
        AlarmManager alarmManager = (AlarmManager) tApplication.getSystemService(Context.ALARM_SERVICE);
        //设置时间
        //AlarmManager.RTC 手机如果锁屏，就不执行
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 2000, pendingIntent);
        // 结束掉所有的运行的activity
        tApplication.finishActivity();
    }


}
