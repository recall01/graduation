package com.example.lenovo.baiduditu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.baiduditu.myClass.common;
import com.mob.MobSDK;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static cn.smssdk.SMSSDK.getSupportedCountries;
import static cn.smssdk.SMSSDK.getVerificationCode;

public class Zhuce extends AppCompatActivity implements View.OnClickListener {
    //app key和app secret 需要填自己应用的对应的！这里只是我自己创建的应用。
    private final String appKey="22d8290d63094";
    private final String appSecret="5d190cd04e968ca288e776fe376d84d6";
    private EventHandler eh;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 0:
                    //客户端验证成功，可以进行注册,返回校验的手机和国家代码phone/country
                    Toast.makeText(Zhuce.this, msg.obj.toString()+"验证成功", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    //获取验证码成功
                    Toast.makeText(Zhuce.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    //返回支持发送验证码的国家列表
                    //Toast.makeText(Zhuce.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    if(msg.obj.toString()=="0"){
                        common.myDailog("账号注册成功！",Zhuce.this);
                    }else {
                        common.myDailog(msg.obj.toString(),Zhuce.this);
                    }
                    break;
            }
        }
    };

    //View控件
    private Button bt_getCode,bt_vertify,bt_back;
    private EditText phoneNum;
    //手机号码
    private String phone="",code="",stu_id="",name="",password="",repassword="";
    //控制按钮样式是否改变
    private boolean tag = true;
    //每次验证请求需要间隔60S
    private int i=60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        common.setHeadBackground(getWindow());

        // 启动短信验证sdk
        MobSDK.init(this, appKey, appSecret);

        eh=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        Message msg = new Message();
                        msg.arg1 = 0;
                        msg.obj = data;
                        handler.sendMessage(msg);
                        //提交验证码,并且正确成功
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        Message msg = new Message();
                        msg.arg1 = 1;
                        msg.obj = "获取验证码成功";
                        handler.sendMessage(msg);
                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        //返回支持发送验证码的国家列表
 /*                       Message msg = new Message();
                        msg.arg1 = 2;
                        msg.obj = "返回支持发送验证码的国家列表";
                        handler.sendMessage(msg); */
                    }
                }else{
                    Message msg = new Message();
                    //返回支持发送验证码的国家列表
                    msg.arg1 = 3;
                    msg.obj = "验证失败";
                    handler.sendMessage(msg);
                    ((Throwable)data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
        bt_getCode= findViewById(R.id.bt_getCode);
        bt_back = findViewById(R.id.back);
        bt_back.setOnClickListener(this);
        phoneNum = findViewById(R.id.et_phone);
        phoneNum.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().length()==11){
                    bt_getCode.setEnabled(true);
                    bt_getCode.setBackgroundResource(R.drawable.bg_but_yanzheng_ok);
                }else {
                    bt_getCode.setEnabled(false);
                    bt_getCode.setBackgroundResource(R.drawable.bg_but_yanzheng_no);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        bt_getCode.setEnabled(false);
        bt_getCode.setClickable(false);
        bt_vertify= findViewById(R.id.bt_verify);
        bt_getCode.setOnClickListener(this);
        bt_vertify.setOnClickListener(this);
    }   //oncreat结束

    public void onClick(View v){
        switch (v.getId()){
            case R.id.bt_verify:yanzheng();break;//验证码按钮
            case R.id.bt_getCode:getCode();break;//注册按钮
            case R.id.back:finish();break;//注册按钮
            default:break;
        }
    }
    //验证验证码函数
    public void yanzheng(){
        code=((EditText)findViewById(R.id.et_code)).getText().toString();
        stu_id=((EditText)findViewById(R.id.account)).getText().toString();
        name=((EditText)findViewById(R.id.wp_shuoming)).getText().toString();
        password=((EditText)findViewById(R.id.password)).getText().toString();
        repassword=((EditText)findViewById(R.id.repassword)).getText().toString();
        if(stu_id.length()>8&&stu_id.length()<12){
            boolean i = common.isConSpeCharacters(name);
            if(i&&!name.equals("")){
                boolean result1=password.matches("[0-9]+");
                boolean result2=password.matches("[a-zA-Z]+");
                if(!result1&&!result2&&password.length()>5&&password.length()<20){
                    if(password.equals(repassword)){
                        password = common.md5(password);
                        if (code.equals("")){
                            Toast.makeText(Zhuce.this,"验证码不能为空",Toast.LENGTH_SHORT).show();
                        }else{
                            //填写了验证码，进行验证
                            sendRequestWithOkHttp();
                        }
                    }else {
                        Toast.makeText(Zhuce.this,"两次密码不一样！",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Zhuce.this,"密码输入错误！",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(Zhuce.this,"名字不正确！",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(Zhuce.this,"学号输入错误！",Toast.LENGTH_SHORT).show();
        }




    }

    //获得验证码函数
    public void getCode(){
        phone=((EditText)findViewById(R.id.et_phone)).getText().toString();
        if(phone.equals("")){
            Toast.makeText(Zhuce.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
        }else{
            //填写了手机号码
            if(common.isMobileNO(phone)){
                //如果手机号码无误，则发送验证请求
                bt_getCode.setClickable(true);
                changeBtnGetCode();
                getSupportedCountries();
                getVerificationCode("86", phone);
            }else{
                //手机号格式有误
                Toast.makeText(Zhuce.this,"手机号格式错误，请检查",Toast.LENGTH_SHORT).show();
            }
        }
    }
    /*
  * 改变按钮样式
  * */
    private void changeBtnGetCode() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                if (tag) {
                    while (i > 0) {
                        i--;
                        //如果活动为空
                        if (Zhuce.this == null) {
                            break;
                        }

                        Zhuce.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bt_getCode.setText("获取验证码(" + i + ")");
                                bt_getCode.setClickable(false);
                                bt_getCode.setBackgroundResource(R.drawable.bg_but_yanzheng_no);
                            }
                        });

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    tag = false;
                }
                i = 60;
                tag = true;

                if (Zhuce.this != null) {
                    Zhuce.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bt_getCode.setText("获取验证码");
                            bt_getCode.setClickable(true);
                            bt_getCode.setBackgroundResource(R.drawable.bg_but_yanzheng_ok);
                        }
                    });
                }
            }
        };
        thread.start();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //已POST方式传递数据
                    OkHttpClient client = new OkHttpClient();
                    if(phone==""){
                        phone=((EditText)findViewById(R.id.et_phone)).getText().toString();
                    }else if(code=="") {
                        code=((EditText)findViewById(R.id.et_code)).getText().toString();
                    }
                    RequestBody requestBody = new FormBody.Builder()
                            .add("phone", phone)
                            .add("zone", "86")
                            .add("code", code)
                            .add("user_id", stu_id)
                            .add("use_name", name)
                            .add("use_password", password).build();
                    Request request = new Request.Builder().url("http://1.873717549.applinzi.com/Android_duanxinyanzheng.php").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.arg1 = 2;
                    msg.obj = e.getMessage()+"---1";
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }
    private void parseJSONWithJSONObject(String jsonData) {
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            String status = jsonObject.getString("status");
            Message msg = new Message();
            msg.arg1 = 2;
            msg.obj = status;
            handler.sendMessage(msg);
        }catch (Exception e){
            e.printStackTrace();
            Message msg = new Message();
            msg.arg1 = 2;
            msg.obj = jsonData;
            handler.sendMessage(msg);
        }
    }
}
