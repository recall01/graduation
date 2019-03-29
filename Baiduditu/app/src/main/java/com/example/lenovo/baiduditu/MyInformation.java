package com.example.lenovo.baiduditu;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lenovo.baiduditu.model.Student;
import com.example.lenovo.baiduditu.myClass.common;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.json.JSONObject;

import java.util.Calendar;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MyInformation extends AppCompatActivity implements View.OnClickListener {
    String user_id,mUrl,nickname,sex,birthday,school,major,nianji,banji,schoolId;
    EditText et_nickname,et_major,et_nianji,et_myClass,et_email,et_phone;
    TextView et_sex,et_birthday,et_school,queding;
    LoadingDialog ld;
    private Student student = new Student();
    RequestBody requestBody;
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
//        mUrl ="http://1.873717549.applinzi.com/Android_allInformation.php";
//        RequestBody requestBody =new FormBody.Builder().add("user_id", user_id).add("isbreak", "true").build();
//        HttpUtil.postOkHttpRequest(requestBody,mUrl, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Message message = new Message();
//                message.what = 3;
//                handler.sendMessage(message);
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String responseData = response.body().string();
//                parseJSONWithJSONObject(responseData,false);
//            }
//        });
//        ld = new LoadingDialog(MyInformation.this);
//        ld.setLoadingText("加载中...").show();
        but();
    }
    private void parseJSONWithJSONObject(String jsonData,boolean isChangeInfor) {
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            Message message = new Message();
            if(!isChangeInfor){
                nickname = jsonObject.getString("nickName");
                sex = jsonObject.getString("useSex");
                birthday = jsonObject.getString("birthday");
                school = jsonObject.getString("mySchool");
                String myClass = jsonObject.getString("myClass");
                if(myClass!=null){
                    String[] strs=myClass.split("\\|");
                    major = strs[0];
                    nianji = strs[1];
                    banji = strs[2];
                }
            /*    major = jsonObject.getString("major");
                nianji = jsonObject.getString("nianji");
                banji = jsonObject.getString("banji"); */
                message.what = 0;
            }else {
                String status = jsonObject.getString("status");
                message.obj = status;
                message.what = 1;
            }
            handler.sendMessage(message);
            //            isLoge = "true";
        }catch (Exception e){
            e.printStackTrace();
            Message message = new Message();
            message.what = 5;
            handler.sendMessage(message);
        }
    }  //parseJSONWithJSONObject
    private void but() {
        Button back = findViewById(R.id.xinxi_back);
        back.setOnClickListener(this);
        TextView head = findViewById(R.id.changeHead);
        head.setOnClickListener(this);
        et_nickname = findViewById(R.id.et_nickname);
        et_nickname.setText(student.getStuName());
        et_sex = findViewById(R.id.et_sex);
        et_sex.setOnClickListener(this);
        if(student.getStuSex()==0){
            et_sex.setText("男");
        }else {
            et_sex.setText("女");
        }
        et_birthday = findViewById(R.id.et_birthday);
        et_birthday.setOnClickListener(this);
        et_email = findViewById(R.id.et_email);
        et_email.setText(student.getStuMail());
        et_email.setEnabled(false);
        et_school = findViewById(R.id.et_school);
        et_school.setOnClickListener(this);
        et_school.setText("成都信息工程大学");
        et_phone = findViewById(R.id.et_phone);
        et_phone.setText(student.getStuPhone());
        et_phone.setEnabled(false);
        et_major = findViewById(R.id.et_major);
        et_major.setText("计算机科学与技术");
        et_nianji = findViewById(R.id.et_nianji);
        et_myClass = findViewById(R.id.et_myClass);
        queding = findViewById(R.id.xinxi_queding);
        queding.setOnClickListener(this);
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
                et_birthday.setText(i+"-"+(i1+1)+"-"+i2);
            }
        };
        DatePickerDialog dialog=new DatePickerDialog(MyInformation.this, 0,listener,year,month,day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
        dialog.show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.xinxi_queding:
                changeInfor();
                break;
            case R.id.xinxi_back:finish();break;//返回按钮
            case R.id.changeHead:
                Intent intent = new Intent(MyInformation.this, ChangeHead.class);
                intent.putExtra("user_id",user_id);
                startActivity(intent);break;//修改头像
            case R.id.et_sex:selectSex();break;
            case R.id.et_birthday:
                getDate();break;
            case R.id.et_school:
                intent = new Intent(MyInformation.this, School.class);
                intent.putExtra("user_id",user_id);
                startActivityForResult(intent,3);break;//修改头像
            default:break;
        }
    }
    private void changeInfor(){
        nickname = et_nickname.getText().toString();
        sex = et_sex.getText().toString();
        birthday = et_birthday.getText().toString();
        if(et_major.getText().toString()!=null&&et_nianji.getText().toString()!=null&&et_myClass.getText().toString()!=null){
            major = et_major.getText().toString()+"|"+et_nianji.getText().toString()+"|"+et_myClass.getText().toString();
            mUrl = "http://1.873717549.applinzi.com/Android/Android_handInfor.php";

            if(schoolId != null){
                requestBody =new FormBody.Builder().add("user_id", user_id)
                        .add("nickname", nickname)
                        .add("sex", sex)
                        .add("school", schoolId)
                        .add("major", major)
                        .add("birthday", birthday).build();
            }else {
                requestBody =new FormBody.Builder().add("user_id", user_id)
                        .add("nickname", nickname)
                        .add("sex", sex)
                        .add("major", major)
                        .add("birthday", birthday).build();
            }

//            HttpUtil.postOkHttpRequest(requestBody,mUrl, new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    Message message = new Message();
//                    message.what = 3;
//                    handler.sendMessage(message);
//                }
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    String responseData = response.body().string();
//                    parseJSONWithJSONObject(responseData,true);
//                }
//            });
        }else {
            common.myToast(MyInformation.this,"请输入学校信息！");
        }

    }


    private void selectSex(){
        final String[] sexArray = new String[]{"男","女"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MyInformation.this);
        builder.setTitle("选择性别").setSingleChoiceItems(sexArray,0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                et_sex.setText(sexArray[i]);
            }
        }).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 3:
                String mySchool = data.getStringExtra("mySchoolName");
                schoolId = data.getStringExtra("mySchoolId");
                et_school.setText(mySchool);break;
            default:break;
        }
    }
}
