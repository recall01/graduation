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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lenovo.baiduditu.model.Student;
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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static cn.smssdk.SMSSDK.getSupportedCountries;
import static cn.smssdk.SMSSDK.getVerificationCode;

public class Zhuce extends AppCompatActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {
    private EventHandler eh;
    private RadioGroup genderRG;
    private Student student = new Student();
    //View控件
    private Button bt_getCode,bt_vertify,bt_back;
    private EditText phoneNum;
    //手机号码
    private String phone="",code="",stuId="",stuName="",password="",repassword="",claId="",stuMail="";
    //控制按钮样式是否改变
    private boolean tag = true;
    //每次验证请求需要间隔60S
    private int i=60;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 0:
                    //客户端验证成功，可以进行注册,返回校验的手机和国家代码phone/country
                    Toast.makeText(Zhuce.this, "验证成功", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    //获取验证码成功
                    Toast.makeText(Zhuce.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                    //返回支持发送验证码的国家列表
                case 2:
                    Toast.makeText(Zhuce.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                    //注册成功
                case 3:
                    common.myDailog(msg.obj.toString(),Zhuce.this);
                    break;
                 default:common.myDailog("系统异常 "+msg.obj.toString(),Zhuce.this);break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
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
                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        //返回支持发送验证码的国家列表
 /*                      msg.arg1 = 2;
                        msg.obj = "返回支持发送验证码的国家列表";
                        handler.sendMessage(msg); */
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
        bt_getCode= findViewById(R.id.bt_getCode);
        bt_back = findViewById(R.id.back);
        bt_back.setOnClickListener(this);
        genderRG = findViewById(R.id.gender);
        genderRG.setOnCheckedChangeListener(this);
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
    @Override
    public void onCheckedChanged(RadioGroup group,int checkedId){
        switch (checkedId){
            case R.id.male:
                student.setStuSex(0);break;
            case R.id.female:
                student.setStuSex(1);break;
        }
    }



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
        stuId=((EditText)findViewById(R.id.stuId)).getText().toString();
        claId = ((EditText)findViewById(R.id.claId)).getText().toString();
        stuName=((EditText)findViewById(R.id.stuName)).getText().toString();
        stuMail = ((EditText)findViewById(R.id.stuMail)).getText().toString();
        password=((EditText)findViewById(R.id.password)).getText().toString();
        repassword=((EditText)findViewById(R.id.repassword)).getText().toString();
        phone=((EditText)findViewById(R.id.et_phone)).getText().toString();
        student.setStuPhone(phone);
        if(stuId.length()<6||stuId.length()>12){
            Toast.makeText(Zhuce.this,"学号输入错误！",Toast.LENGTH_SHORT).show();
            return;
        }
        student.setStuNumber(stuId);
        if(claId.length()<4||claId.length()>12){
            Toast.makeText(Zhuce.this,"班级编号错误！",Toast.LENGTH_SHORT).show();
            return;
        }
        student.setClaID(claId);
        boolean i = common.isConSpeCharacters(stuName);
        if(!i||"".equals(stuName)){
            Toast.makeText(Zhuce.this,"名字不正确！",Toast.LENGTH_SHORT).show();
            return;
        }
        student.setStuName(stuName);
        if(stuMail.length()<6||stuMail.length()>20){
            Toast.makeText(Zhuce.this,"邮箱有误！",Toast.LENGTH_SHORT).show();
            return;
        }
        student.setStuMail(stuMail);
        boolean result1=password.matches("[0-9]+");
        boolean result2=password.matches("[a-zA-Z]+");
        if(result1||result2||password.length()<6||password.length()>20){
            Toast.makeText(Zhuce.this,"密码输入错误！",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.equals(repassword)){
            Toast.makeText(Zhuce.this,"两次密码不一样！",Toast.LENGTH_SHORT).show();
            return;
        }
        password = common.md5(password);
        student.setStuPassword(password);
        if("".equals(code)){
            Toast.makeText(Zhuce.this,"验证码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        //填写了验证码，进行验证
        sendRequestWithOkHttp();
    }

    //获得验证码函数
    public void getCode(){
        phone=((EditText)findViewById(R.id.et_phone)).getText().toString();
        if("".equals(phone)){
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
                    final OkHttpClient client = new OkHttpClient();
                    //1.先验证验证码是否正确Get
                    Request request = new Request.Builder().url(Constants.VERIFY_URL+"?code="+code+"&phone="+phone).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Message message = new Message();
                            message.arg1 = 4;
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
                                    //2.验证成功，进行注册
                                    RequestBody body = RequestBody.create(Constants.JSONTYPE,new Gson().toJson(student));
                                    Request request = new Request.Builder().url(Constants.REGIST_URL).post(body).build();
                                    Response response2 = client.newCall(request).execute();
                                    String response2Data = response2.body().string();
                                    parseJSONWithJSONObject(response2Data);
                                }else {
                                    message.arg1 = 3;
                                    message.obj = "验证码错误 "+jsonObject.getString("msg");
                                }
                            }catch (Exception e){
                                message.arg1 = 4;
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
            //int status = jsonObject.getInt("status");
            msg.arg1 = 3;
            msg.obj = jsonObject.getString("msg");
        }catch (Exception e){
            e.printStackTrace();
            msg.arg1 = 4;
            msg.obj = e.getMessage()+"|"+jsonData;
        }finally {
            handler.sendMessage(msg);
        }
    }
}
