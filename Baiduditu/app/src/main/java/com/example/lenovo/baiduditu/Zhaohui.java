package com.example.lenovo.baiduditu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.baiduditu.myClass.common;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Zhaohui extends AppCompatActivity implements View.OnClickListener {
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 1:
                    //获取验证码成功
                    Toast.makeText(Zhaohui.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    if(msg.obj.toString().equals("0")){
                        common.myDailog("密码修改成功！",Zhaohui.this);
                    }else {
                        common.myDailog(msg.obj.toString(),Zhaohui.this);
                    }
                    break;
            }
        }
    };

    //View控件
    private Button bt_vertify,bt_back;
    private EditText phoneNum;
    //手机号码
    private String phone="",password="",repassword="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_zhaohui);
        common.setHeadBackground(getWindow());

        bt_back = (Button) findViewById(R.id.z_back);
        bt_back.setOnClickListener(this);
        phoneNum = (EditText)findViewById(R.id.z_et_phone);
        bt_vertify= (Button) findViewById(R.id.z_bt_verify);
        bt_vertify.setOnClickListener(this);
    }   //oncreat结束

    public void onClick(View v){
        switch (v.getId()){
            case R.id.z_bt_verify:yanzheng();break;//验证码按钮
            case R.id.z_back:finish();break;//注册按钮
            default:break;
        }
    }
    //验证验证码函数
    public void yanzheng(){
        password=((EditText)findViewById(R.id.z_password)).getText().toString();
        repassword=((EditText)findViewById(R.id.z_repassword)).getText().toString();
        boolean result1=password.matches("[0-9]+");
        boolean result2=password.matches("[a-zA-Z]+");
        if(!result1&&!result2&&password.length()>5&&password.length()<20){
            if(password.equals(repassword)){
                password = common.md5(password);
                //填写了验证码，进行验证
                sendRequestWithOkHttp();
            }else {
                common.myToast(getApplicationContext(),"两次密码不一样！");
            }
        }else {
            common.myToast(getApplicationContext(),"密码输入错误！");
            }
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //已POST方式传递数据
                    OkHttpClient client = new OkHttpClient();
                    if(phone==""){
                        phone=((EditText)findViewById(R.id.z_et_phone)).getText().toString();
                    }
                    RequestBody requestBody = new FormBody.Builder().add("phone", phone).add("use_password", password).build();
                    Request request = new Request.Builder().url("http://1.873717549.applinzi.com/Android_zhaohui.php").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.arg1 = 1;
                    msg.obj = e.getMessage();
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
            msg.arg1 = 1;
            msg.obj = e.getMessage();
            handler.sendMessage(msg);
        }
    }
}
