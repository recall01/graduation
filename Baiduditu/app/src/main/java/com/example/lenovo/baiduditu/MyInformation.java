package com.example.lenovo.baiduditu;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lenovo.baiduditu.fragment.frag_a;
import com.example.lenovo.baiduditu.model.Student;
import com.example.lenovo.baiduditu.myClass.common;
import com.example.lenovo.baiduditu.utils.Constants;
import com.google.gson.Gson;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyInformation extends AppCompatActivity implements View.OnClickListener {
    EditText nameET,classET,emailET,phoneET,stuNumET;
    TextView sexTV,birthdayET,saveTV;
    LoadingDialog ld;
    private boolean isFresh = false;  //是否刷新
    private Student student = new Student();
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:ld.close();break;
                case 1:
                    common.myDailog(msg.obj.toString(),MyInformation.this);break;
                default:
                    common.myDailog(msg.obj.toString(),MyInformation.this);break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);
        common.setHeadBackground(getWindow());

        student = (Student) getIntent().getSerializableExtra("student");
        if(student == null){
            return;
        }
        init();//初始化按键信息
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
                    isFresh = true;
                }
            }
            message.what = 1;
            message.obj = jsonObject.getString("msg");
        }catch (Exception e){
            e.printStackTrace();
            message.obj = e.getMessage();
            message.what = 0;
        }finally {
            handler.sendMessage(message);
        }

    }  //parseJSONWithJSONObject
    private void init() {
        Button back = findViewById(R.id.xinxi_back);
        back.setOnClickListener(this);
        TextView head = findViewById(R.id.changeHead);
        head.setOnClickListener(this);
        nameET = findViewById(R.id.et_name);
        nameET.setText(student.getStuName());
        sexTV = findViewById(R.id.et_sex);
        sexTV.setOnClickListener(this);
        if(student.getStuSex()==0){
            sexTV.setText("男");
        }else {
            sexTV.setText("女");
        }
        birthdayET = findViewById(R.id.et_birthday);
        birthdayET.setOnClickListener(this);
        emailET = findViewById(R.id.et_email);
        emailET.setText(student.getStuMail());
        emailET.setEnabled(false);
        stuNumET = findViewById(R.id.et_stuNum);
        stuNumET.setText(student.getStuNumber());
        stuNumET.setEnabled(false);
        phoneET = findViewById(R.id.et_phone);
        phoneET.setText(student.getStuPhone());
        phoneET.setEnabled(false);
        classET = findViewById(R.id.et_myClass);
        classET.setText(student.getClassName());
        classET.setEnabled(false);
        saveTV = findViewById(R.id.et_save);
        saveTV.setOnClickListener(this);
    }
    private void getDate() {
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);       //获取年月日时分秒
        int month=cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        int day=cal.get(Calendar.DAY_OF_MONTH);
        showDatePickerDialog(year,month,day);
    }//getDate
    private void showDatePickerDialog(int year,int month,int day){
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                birthdayET.setText(i+"-"+(i1+1)+"-"+i2);
            }
        };
        DatePickerDialog dialog=new DatePickerDialog(MyInformation.this, 0,listener,year,month,day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
        dialog.show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.et_save:
                changeInfor();
                break;
            case R.id.xinxi_back:
                Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("student",student);
                intent.putExtras(mBundle);
                LocalBroadcastManager.getInstance(MyInformation.this).sendBroadcast(intent);
                sendBroadcast(intent);
                finish();break;//返回按钮
            case R.id.changeHead:
                common.myDailog("作者太懒,该功能还未实现",MyInformation.this);
                break;//修改头像
            case R.id.et_sex:selectSex();break;
            case R.id.et_birthday:
                getDate();break;
            default:break;
        }
    }
    private void changeInfor(){
        student.setStuName(nameET.getText().toString());
        if(birthdayET.getText().toString()!=null&&!"".equals(birthdayET.getText().toString())){
            System.out.println("生日:"+birthdayET.getText().toString());
        }
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(Constants.JSONTYPE,new Gson().toJson(student));
        Request request = new Request.Builder().url(Constants.CHANGE_URL).post(body).build();
        try {
            client.newCall(request).enqueue(new Callback(){

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectSex(){
        final String[] sexArray = new String[]{"男","女"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MyInformation.this);
        builder.setTitle("选择性别").setSingleChoiceItems(sexArray,0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                student.setStuSex(i);
                sexTV.setText(sexArray[i]);
            }
        }).show();
    }

}
