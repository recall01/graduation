package com.example.lenovo.baiduditu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lenovo.baiduditu.model.Student;
import com.example.lenovo.baiduditu.myClass.HttpUtil;
import com.example.lenovo.baiduditu.myClass.activityCollector;
import com.example.lenovo.baiduditu.myClass.common;
import com.example.lenovo.baiduditu.services.ILoginService;
import com.example.lenovo.baiduditu.services.impl.LoginServiceImpl;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private final static String LOGIN_URL = "http://10.18.42.63:8801/student/login";
    TextView register,forget;
    Button login;
    private SharedPreferences pref;
    EditText account,password;
    String name;
    Student student = new Student();
    LoadingDialog ld;
    ILoginService loginService = new LoginServiceImpl();

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    common.myDailog("系统异常！"+msg.obj,LoginActivity.this);
                    ld.close();
                    break;
                case 1:
                    common.myDailog("连接服务器失败！",LoginActivity.this);
                    ld.close();
                    break;
                case 2:
                    common.myDailog("验证失败，请检查账号和密码！",LoginActivity.this);
                    ld.close();
                    break;
                case 3:ld.close();
                    login();
                    break;
                default:break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityCollector.addActivity(this);

        common.setHeadBackground(getWindow());
        loadView();
    }   //onCreat

    //view
    public void loadView(){
        login =  findViewById(R.id.login);
        register =  findViewById(R.id.register);
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        forget = findViewById(R.id.forget);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Zhaohui.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,Zhuce.class);
                startActivity(intent);
            }
        });
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        String zhanghao = pref.getString("account","");
        String mima = pref.getString("password","");
        account.setText(zhanghao);
        password.setText(mima);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginService.rememberPassword(pref,account.getText().toString(),password.getText().toString());
                RequestBody requestBody =new FormBody.Builder().add("account",account.getText().toString()).add("password",common.md5(password.getText().toString())).build();
//                MediaType mt = MediaType.parse("application/json; charset=utf-8");
//                Student student = new Student();
//                student.setStuSex(1);
//                student.setStuPhone("18381173671");
//                student.setStuPassword("password");
//                student.setStuNumber("20181221");
//                student.setStuName("张三丰");
//                student.setStuMail("873718549@qq.com");
//                student.setStuClass("三年级二班");
//                String json = new Gson().toJson(student);
//                System.out.println(json);
//                RequestBody requestBody = RequestBody.create(mt,json);
                //                String mUrl ="http://1.873717549.applinzi.com/Android_text.php";
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
                HttpUtil.postEnqueueRequest(requestBody, LOGIN_URL, callback);
                ld = new LoadingDialog(LoginActivity.this);
                ld.setLoadingText("登陆中...").show();
            }
        });
    }

    //重写按键函数
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (event.getKeyCode()== KeyEvent.KEYCODE_BACK){
            activityCollector.removeActivity(this);
        }
        return super.onKeyDown(keyCode,event);
    }

    private void parseJSONWithJSONObject(String jsonData) {
        Message message = new Message();
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            int status = jsonObject.getInt("status");
            if(status==200){
                JSONObject js = jsonObject.getJSONObject("data");
                if(js!=null){
                    student.setStuName(js.getString("stuName"));
                    student.setStuId(js.getString("stuId"));
                    student.setStuSex(js.getInt("stuSex"));
                    student.setStuNumber(js.getString("stuNumber"));
                    student.setStuPassword(js.getString("stuPassword"));
                    student.setStuPhone(js.getString("stuPhone"));
                    student.setStuMail(js.getString("stuMail"));
                    student.setClaID(js.getString("claID"));
                    student.setClassName(js.getString("claName"));
                    student.setRegisterTime(js.getString("registerTime"));
                    student.setPermissions(js.getString("permissions"));
                    message.what = 3;
                }else {
                    message.what = 2;
                }
            }else {
                message.what = 2;
            }
        }catch (Exception e){
            e.printStackTrace();
            message.obj = e.getMessage();
            message.what = 0;
        }finally {
            handler.sendMessage(message);
        }
    }

    //正确登陆页面跳转函数
    public void login(){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("student",student);
        intent.putExtras(mBundle);
        startActivity(intent);
        LoginActivity.this.finish();
    }//change
}
