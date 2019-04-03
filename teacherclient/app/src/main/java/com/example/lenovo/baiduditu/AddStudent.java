package com.example.lenovo.baiduditu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.lenovo.baiduditu.myClass.common;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class AddStudent extends AppCompatActivity {
    String mUrl;
    EditText ser_school;
    private String[] dateName,dateId;
    private Button addBT;
    ListView schoolList;
    LinearLayout layout;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:break;
                case 1:changeView();break;
                case 2:upDate(msg.obj.toString());break;
                default:break;
            }
        }
    };

    private void changeView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddStudent.this,android.R.layout.simple_list_item_1,dateName);
        schoolList.setAdapter(adapter);
        schoolList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mIntent = new Intent();
                // 设置结果，并进行传送
                setResult(3,mIntent);
                mIntent.putExtra("mySchoolName",dateName[i]);
                mIntent.putExtra("mySchoolId",dateId[i]);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstudent);
        common.setHeadBackground(getWindow());

        addBT = findViewById(R.id.bt_add);
        addBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                common.myToast(AddStudent.this,"bt_add");
            }
        });
        /*schoolList = findViewById(R.id.school_list);
        layout = findViewById(R.id.tuijian);
        ser_school = findViewById(R.id.ser_school);
        ser_school.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length()>0){
                    layout.setVisibility(View.VISIBLE);
                    Message message = new Message();
                    message.what = 2;
                    message.obj = editable;
                    handler.sendMessage(message);
                }
            }
        });*/
    }

    private void upDate(String school_name){
        mUrl = "http://1.873717549.applinzi.com/Android/Android_refschool.php";
        RequestBody requestBody =new FormBody.Builder().add("school_name", school_name).build();
//        HttpUtil.postOkHttpRequest(requestBody,mUrl, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Message message = new Message();
//                message.what = 3;
//                handler.sendMessage(message);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String responseData = response.body().string();
//                parseJSONWithJSONObject(responseData);
//            }
//        });
    }

    private void parseJSONWithJSONObject(String jsonData) {
        try{
                JSONArray allGoods = new JSONArray(jsonData);
                dateName = new String[allGoods.length()];
                dateId = new String[allGoods.length()];
                for (int i=0;i<allGoods.length();i++){
                    JSONObject jsgood = allGoods.getJSONObject(i);
                    String name =jsgood.getString("school_name");
                    String id =jsgood.getString("school_id");
                    dateName[i] = name;
                    dateId[i] = id;
                }
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
        }catch (Exception e){
            e.printStackTrace();
            Message msg = new Message();
            msg.what = 3;
            msg.obj = e.getMessage();
            handler.sendMessage(msg);
        }

    } //parseJSONWithJSONObject
}
