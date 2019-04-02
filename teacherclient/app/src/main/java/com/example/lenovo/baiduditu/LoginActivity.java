package com.example.lenovo.baiduditu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.baiduditu.model.Student;
import com.example.lenovo.baiduditu.model.Teacher;
import com.example.lenovo.baiduditu.myClass.HttpUtil;
import com.example.lenovo.baiduditu.myClass.activityCollector;
import com.example.lenovo.baiduditu.myClass.common;
import com.example.lenovo.baiduditu.services.ILoginService;
import com.example.lenovo.baiduditu.services.impl.LoginServiceImpl;
import com.example.lenovo.baiduditu.utils.Constants;
import com.mob.MobSDK;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.json.JSONObject;

import java.io.IOException;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static cn.smssdk.SMSSDK.getSupportedCountries;
import static cn.smssdk.SMSSDK.getVerificationCode;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button loginBT,getCodeBT;
    EditText codeET,phoneET;
    Teacher teacher = new Teacher();
    private EventHandler eh;
    LoadingDialog ld;
    //控制按钮样式是否改变
    private boolean tag = true;
    //每次验证请求需要间隔60S
    private int i=60;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    ld.close();
                    common.myDailog("系统异常!"+msg.obj,LoginActivity.this);
                    break;
                case 1:
                    ld.close();
                    common.myDailog("连接服务器失败!",LoginActivity.this);
                    break;
                case 2:
                    ld.close();
                    common.myDailog(msg.obj.toString(),LoginActivity.this);
                    break;
                case 3:
                    ld.close();
                    login();
                    break;
                case 4://发短信逻辑
                    common.myToast(LoginActivity.this,msg.obj.toString());
                    break;
                default:break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activityCollector.addActivity(this);
        common.setHeadBackground(getWindow());
        // 启动短信验证sdk
        MobSDK.init(this, Constants.mobAppKey, Constants.mobAppSecret);
        eh=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.what = 4;
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        msg.obj = "获取验证码成功";
                        handler.sendMessage(msg);
                    }
                }else{
                    //返回支持发送验证码的国家列表
                    msg.obj = "获取验证失败";
                    ((Throwable)data).printStackTrace();
                    handler.sendMessage(msg);
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
        loadView();
    }   //onCreat

    //view
    public void loadView(){

        codeET = findViewById(R.id.et_code);
        getCodeBT = findViewById(R.id.bt_getCode);
        getCodeBT.setOnClickListener(this);
        phoneET = findViewById(R.id.et_phone);
        phoneET.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().length()==11){
                    getCodeBT.setEnabled(true);
                    getCodeBT.setBackgroundResource(R.drawable.bg_but_yanzheng_ok);
                }else {
                    getCodeBT.setEnabled(false);
                    getCodeBT.setBackgroundResource(R.drawable.bg_but_yanzheng_no);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        loginBT =  findViewById(R.id.bt_login);
        loginBT.setOnClickListener(this);
    }

    //重写按键函数
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (event.getKeyCode()== KeyEvent.KEYCODE_BACK){
            activityCollector.removeActivity(this);
        }
        return super.onKeyDown(keyCode,event);
    }

    //正确登陆页面跳转函数
    public void login(){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("teacher",teacher);
        intent.putExtras(mBundle);
        startActivity(intent);
        LoginActivity.this.finish();
    }//change

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_getCode:
                getCode();break;
            case R.id.bt_login:
                ld = new LoadingDialog(LoginActivity.this);
                ld.setLoadingText("加载中...").show();
                loginRequest();break;
            default:break;
        }
    }
    private void loginRequest(){
        RequestBody requestBody =new FormBody.Builder().add("phone",phoneET.getText().toString()).add("code",codeET.getText().toString()).build();
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
        HttpUtil.postEnqueueRequest(requestBody, Constants.LOGIN_URL, callback);
    }
    private void parseJSONWithJSONObject(String jsonData) {
        Message message = new Message();
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            int status = jsonObject.getInt("status");
            if(status==200){
                JSONObject js = jsonObject.getJSONObject("data");
                if(js!=null){
                    teacher.setTeaId(js.getString("teaID"));
                    teacher.setTeaName(js.getString("teaName"));
                    teacher.setTeaPhone(js.getString("teaPhone"));
                    teacher.setRegisterTime(js.getString("registerTime"));
                    teacher.setPermissions(js.getString("permissions"));
                    message.what = 3;
                }else {
                    message.what = 2;
                }

            }else {
                message.what = 2;
            }
            message.obj = jsonObject.getString("msg");
        }catch (Exception e){
            e.printStackTrace();
            message.obj = e.getMessage();
            message.what = 0;
        }finally {
            handler.sendMessage(message);
        }
    }
    //获取验证码
    private void getCode(){
        String phoneNum = phoneET.getText().toString();
        if("".equals(phoneNum)||phoneNum == null){
            Toast.makeText(LoginActivity.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
        }else{
            //填写了手机号码
            if(common.isMobileNO(phoneNum)){
                //如果手机号码无误，则发送验证请求
                getCodeBT.setClickable(true);
                this.changeBtnGetCode();
                getSupportedCountries();
                getVerificationCode("86", phoneET.getText().toString());
            }else{
                //手机号格式有误
                Toast.makeText(LoginActivity.this,"手机号格式错误，请检查",Toast.LENGTH_SHORT).show();
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
                        if (LoginActivity.this == null) {
                            break;
                        }

                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getCodeBT.setText("获取验证码(" + i + ")");
                                getCodeBT.setClickable(false);
                                getCodeBT.setBackgroundResource(R.drawable.bg_but_yanzheng_no);
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

                if (LoginActivity.this != null) {
                    LoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getCodeBT.setText("获取验证码");
                            getCodeBT.setClickable(true);
                            getCodeBT.setBackgroundResource(R.drawable.bg_but_yanzheng_ok);
                        }
                    });
                }
            }
        };
        thread.start();
    }
}
