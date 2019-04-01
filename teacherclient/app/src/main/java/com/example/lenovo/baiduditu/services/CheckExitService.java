package com.example.lenovo.baiduditu.services;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.example.lenovo.baiduditu.myClass.common;

import java.util.List;

public class CheckExitService extends Service {
    private String packageName = "com.example.lenovo.baiduditu";
    private MyBinder myBinder = new MyBinder();
    public CheckExitService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        common.myToast(this,"onBind!");
        return myBinder;
    }

    public class MyBinder extends Binder{

    }
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        common.myToast(this,"APP推出啦");
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ActivityManager activtyManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activtyManager.getRunningAppProcesses();
        for (int i = 0; i < runningAppProcesses.size(); i++) {
            if (packageName.equals(runningAppProcesses.get(i).processName)) {
                Toast.makeText(this, "app还在运行中", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "app已停止运行", Toast.LENGTH_LONG).show();
            }
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(CheckExitService.this, "App检测服务开启了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(CheckExitService.this, "服务已经退出", Toast.LENGTH_SHORT).show();
    }
}
