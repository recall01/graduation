package com.example.lenovo.baiduditu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.lenovo.baiduditu.myClass.HttpUtil;
import com.example.lenovo.baiduditu.myClass.common;
import com.example.lenovo.baiduditu.myactivity.Teacher;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StartActivity extends AppCompatActivity {
    /*
    * 1.验证网络信息
    * 2.验证权限
    * 3.验证版本信息，如有更新，则提示
    * 4.从SharePreferences读取用户信息
        * 4.1.如存在，请求http验证（成功跳转学生页面|老师页面），并更新SharePreferences
        * 4.2.若不存在，跳转登陆界面
    * */

    String xuehao,name;
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
        }else {
            version();
        }
    }
    //校验版本及其信息
    private void version(){
        readInfo();
    }

    //从SharePreferences读取用户信息
    private void readInfo(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        if(pref!=null){
            xuehao = pref.getString("stu_id","");
            String pass = common.md5(pref.getString("password",""));
            verifyInfo(xuehao,pass);
        }else {
            goToLogin(StartActivity.this);
        }

    }//readInfo

    //跳转到登陆界面
    private void goToLogin(Context context){
        Intent mainIntent = new Intent(context,LoginActivity.class);
        context.startActivity(mainIntent);//跳转到MainActivity
    }
    //跳转到学生界面
    private void goToStudentHome(Context context){
        Intent mainIntent = new Intent(context,MainActivity.class);
        mainIntent.putExtra("name",name);
        mainIntent.putExtra("user_id",xuehao);
        context.startActivity(mainIntent);//跳转到Xuanzejiemian
    }
    //跳转到老师界面
    private void goToTeacherHome(Context context){
        Intent mainIntent = new Intent(context,Teacher.class);
        context.startActivity(mainIntent);
    }

    //验证信息
    private void verifyInfo(String xueha,String pass){
        RequestBody requestBody =new FormBody.Builder().add("stu_id",xueha).add("mima",pass).build();
        String mUrl ="http://1.873717549.applinzi.com/Android_text.php";
        HttpUtil.postEnqueueRequest(requestBody, mUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                common.myDailog("连接服务器失败！",StartActivity.this);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                parseJSONWithJSONObject(responseData);
            }
        });
    }  //verifyInfo
    private void parseJSONWithJSONObject(String jsonData) {
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            String status = jsonObject.getString("status");
            if(status.equals("ok")){
                name = jsonObject.getString("name");
                String type = jsonObject.getString("type");
                if(type.equals("0")){
                    goToStudentHome(StartActivity.this);
                }else if(type.equals("1")){
                    goToTeacherHome(StartActivity.this);
                }
            }else {
                goToLogin(StartActivity.this);
            }
        }catch (Exception e){
            goToLogin(StartActivity.this);
        }
    }//parseJSONWithJSONObject
    //更新用户数据




    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }//onDestroy


}
