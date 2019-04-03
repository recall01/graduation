package com.example.lenovo.baiduditu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import com.example.lenovo.baiduditu.myClass.common;
import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    /*
    * 1.验证网络信息
    * 2.验证权限
    * 3.验证版本信息，如有更新，则提示
    * 4.跳转登陆界面
    * */
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                intentFilter = new IntentFilter();
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                networkChangeReceiver = new NetworkChangeReceiver();
                registerReceiver(networkChangeReceiver,intentFilter);
            }
        }, 3000);//给postDelayed()方法传递延迟参数
    }  //onCreate

    //判断网络状况
    class NetworkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo !=null &&networkInfo.isAvailable()){
                applyJurisdiction();
            }else {
                common.myToast(StartActivity.this,"network is unavailable");
            }
        }
    } //NetworkChangeReceiver
    //验证权限
    private void applyJurisdiction(){
        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(StartActivity.this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(StartActivity.this,android.Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(StartActivity.this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[] permissions =permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(StartActivity.this,permissions,1);
            this.applyJurisdiction();
        }else {
            Intent mainIntent = new Intent(StartActivity.this,LoginActivity.class);
            StartActivity.this.startActivity(mainIntent);//跳转到MainActivity
        }
    }

    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }//onDestroy
}
