package com.example.lenovo.baiduditu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.baiduditu.myClass.HttpUtil;
import com.example.lenovo.baiduditu.myClass.common;
import com.example.lenovo.baiduditu.utils.Constants;
import com.google.gson.Gson;
import com.mob.MobSDK;

import org.json.JSONObject;

import java.io.IOException;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static cn.smssdk.SMSSDK.getSupportedCountries;
import static cn.smssdk.SMSSDK.getVerificationCode;

public class Zhaohui extends AppCompatActivity implements View.OnClickListener {
    private EventHandler eh;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 1:
                    //获取验证码成功
                    Toast.makeText(Zhaohui.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    common.myDailog(msg.obj.toString(),Zhaohui.this);
                    break;
                case 3:
                    common.myDailog("系统异常 "+msg.obj.toString(),Zhaohui.this);
                    break;
            }
        }
    };
    //控制按钮样式是否改变
    private boolean tag = true;
    //每次验证请求需要间隔60S
    private int i=60;
    //View控件
    private Button bt_getCode,bt_back;
    private EditText phoneNum;
    private TextView changeTV;
    //手机号码
    private String phone="",password="",repassword="",code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhaohui);
        common.setHeadBackground(getWindow());
        // 启动短信验证sdk
        MobSDK.init(this, Constants.mobAppKey, Constants.mobAppSecret);
        eh=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        msg.arg1 = 0;
                        //提交验证码,并且正确成功
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        msg.arg1 = 1;
                        msg.obj = "获取验证码成功";
                    }
                }else{
                    //返回支持发送验证码的国家列表
                    msg.arg1 = 2;
                    msg.obj = "验证失败";
                    ((Throwable)data).printStackTrace();
                }
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调

        bt_back =  findViewById(R.id.z_back);
        bt_back.setOnClickListener(this);
        bt_getCode= findViewById(R.id.z_getCode);
        bt_getCode.setOnClickListener(this);
        changeTV = findViewById(R.id.change);
        changeTV.setOnClickListener(this);
        phoneNum = findViewById(R.id.z_et_phone);
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
    }   //oncreat结束

    public void onClick(View v){
        switch (v.getId()){
            case R.id.z_getCode:getCode();break;
            case R.id.change:changePassword();break;
            case R.id.z_back:finish();break;
            default:break;
        }
    }
    //获取验证码
    private void getCode(){
        phone=((EditText)findViewById(R.id.z_et_phone)).getText().toString();
        if("".equals(phone)){
            Toast.makeText(Zhaohui.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
        }else{
            //填写了手机号码
            if(common.isMobileNO(phone)){
                //如果手机号码无误，则发送验证请求
                bt_getCode.setClickable(true);
                this.changeBtnGetCode();
                getSupportedCountries();
                getVerificationCode("86", phone);
            }else{
                //手机号格式有误
                Toast.makeText(Zhaohui.this,"手机号格式错误，请检查",Toast.LENGTH_SHORT).show();
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
                        if (Zhaohui.this == null) {
                            break;
                        }

                        Zhaohui.this.runOnUiThread(new Runnable() {
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

                if (Zhaohui.this != null) {
                    Zhaohui.this.runOnUiThread(new Runnable() {
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

    //修改密码
    private void changePassword(){
        code = ((EditText)findViewById(R.id.z_code)).getText().toString();
        password=((EditText)findViewById(R.id.z_password)).getText().toString();
        repassword=((EditText)findViewById(R.id.z_repassword)).getText().toString();
        boolean result1=password.matches("[0-9]+");
        boolean result2=password.matches("[a-zA-Z]+");
        if(result1||result2||password.length()<6||password.length()>20){
            common.myToast(getApplicationContext(),"密码格式错误！");
            return;
        }
        if(!password.equals(repassword)){
            common.myToast(getApplicationContext(),"两次密码不一样！");
            return;
        }
        if(code==null||"".equals(code)){
            common.myToast(getApplicationContext(),"请输入验证码！");
            return;
        }
        //1.验证码校验
        sendRequestWithOkHttp();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final OkHttpClient client = new OkHttpClient();
                    if(phone==null||"".equals(phone)){
                        phone=((EditText)findViewById(R.id.z_et_phone)).getText().toString();
                    }
                    Request request = new Request.Builder().url(Constants.VERIFY_URL+"?code="+code+"&phone="+phone).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Message message = new Message();
                            message.arg1 = 3;
                            message.obj = e.getMessage();
                            handler.sendMessage(message);
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Message message = new Message();
                            String responseData = response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(responseData);
                                int status = jsonObject.getInt("status");
                                System.out.println("验证结果:"+jsonObject.getInt("status"));
                                if(status == 200){
                                    //2.验证码校验成功，进行注册
                                    RequestBody body =new FormBody.Builder().add("phone",phone).add("password",common.md5(password)).build();
                                    Callback callback = new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                            Message message = new Message();
                                            message.what = 1;
                                            handler.sendMessage(message);
                                        }

                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {
                                            String responseData = response.body().string();
                                            parseJSONWithJSONObject(responseData);
                                        }
                                    };
                                    HttpUtil.postEnqueueRequest(body, Constants.CHANGEPASSWORD_URL, callback);
                                }else {
                                    message.arg1 = 3;
                                    message.obj = "验证码错误 "+jsonObject.getString("msg");
                                }
                            }catch (Exception e){
                                message.arg1 = 3;
                                message.obj = e.getMessage();
                                e.printStackTrace();
                            }finally {
                                handler.sendMessage(message);
                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.arg1 = 4;
                    msg.obj = e.getMessage();
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }
    private void parseJSONWithJSONObject(String jsonData) {
        Message msg = new Message();
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            int status = jsonObject.getInt("status");
            msg.arg1 = 2;
            msg.obj = jsonObject.getString("msg");
        }catch (Exception e){
            e.printStackTrace();
            msg.arg1 = 3;
            msg.obj = e.getMessage();
        }finally {
            handler.sendMessage(msg);
        }
    }
}
