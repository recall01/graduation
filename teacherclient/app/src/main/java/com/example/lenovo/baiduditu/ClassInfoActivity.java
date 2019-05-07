package com.example.lenovo.baiduditu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.lenovo.baiduditu.model.Classes;
import com.example.lenovo.baiduditu.model.VO.TeacherVO;
import com.example.lenovo.baiduditu.myClass.HttpUtil;
import com.example.lenovo.baiduditu.myClass.common;
import com.example.lenovo.baiduditu.utils.Constants;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ClassInfoActivity extends AppCompatActivity {
    EditText claIdET,createTimeET,claNameET,claNumberET,dynamicET;
    private TextView saveTV;
    private Button backBT;
    private TeacherVO teacher = new TeacherVO();
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:common.myToast(ClassInfoActivity.this,msg.obj.toString());break;
                default:break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classinfo);
        common.setHeadBackground(getWindow());

        teacher = (TeacherVO) getIntent().getSerializableExtra("teacher");
        if(teacher == null){
            return;
        }
        //加载数据
        String teaNumber = teacher.getTeaNumber();
        initView();
        loadData(teaNumber);
    }

    private void initView(){
        backBT = findViewById(R.id.xinxi_back);
        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        claIdET = findViewById(R.id.et_claId);
        claIdET.setText(teacher.getAClass().getClaID());
        claIdET.setEnabled(false);
        createTimeET = findViewById(R.id.et_claCreateTime);
        createTimeET.setText(teacher.getAClass().getCreateTime());
        createTimeET.setEnabled(false);
        claNumberET = findViewById(R.id.et_claNumber);
        claNumberET.setEnabled(false);
        claNameET = findViewById(R.id.et_claName);
        claNameET.setText(teacher.getAClass().getClaName());
        saveTV = findViewById(R.id.tv_save);
        saveTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String claId = claIdET.getText().toString();
                String createTime = createTimeET.getText().toString();
                String name = claNameET.getText().toString();
                if(StringUtils.isEmpty(claId)||StringUtils.isEmpty(createTime)||StringUtils.isEmpty(name)){
                    common.myToast(ClassInfoActivity.this,"入参不能为空");
                    return;
                }
                Classes cla = teacher.getAClass();
                cla.setClaName(name);
                changeClaInfo(cla);
            }
        });
        dynamicET = findViewById(R.id.et_dynamic);
        dynamicET.setText(teacher.getAClass().getDynamic());
        dynamicET.setEnabled(false);
    }

    private void loadData(String number){
        HttpUtil.getOkHttpRequest(Constants.GETCLASSINFO_URL+"?teaNumber="+number, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                parseJSONData(responseData);
            }
        });
    }
    private void parseJSONData(String jsonData) {
        Message message = new Message();
        try{
            JSONObject data = new JSONObject(jsonData);
            int status = data.getInt("status");
            String msg = data.getString("msg");
            if (status == 200){
                data = data.getJSONObject("data");
                String name = data.getString("claName");
                String dynamic = data.getString("dynamic");
                claNameET.setText(name);
                dynamicET.setText(dynamic);
                //重新加载teacher数据
                int count = data.getInt("number");
                claNumberET.setText(String.valueOf(count));
            }
            message.what = 2;
            message.obj = msg;
            handler.sendMessage(message);
        }catch (Exception e){
            e.printStackTrace();
            message.what = 1;
            message.obj = e.getMessage();
            handler.sendMessage(message);
        }
    } //parseJSONWithJSONObject


    private void changeClaInfo(Classes cla){
        RequestBody body = RequestBody.create(Constants.JSONTYPE,new Gson().toJson(cla));
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 1;
                message.obj = e.getMessage();
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                parseJSONWithJSONObject(responseData);
            }
        };
        HttpUtil.postEnqueueRequest(body, Constants.CHANGECLASS_URL, callback);
    }

    private void parseJSONWithJSONObject(String jsonData) {
        Message message = new Message();
        try{
            JSONObject data = new JSONObject(jsonData);
            int status = data.getInt("status");
            if (status == 200){
                //重新加载teacher数据
            }
            String msg = data.getString("msg");
            message.what = 1;
            message.obj = msg;
            handler.sendMessage(message);
        }catch (Exception e){
            e.printStackTrace();
            message.what = 1;
            message.obj = e.getMessage();
            handler.sendMessage(message);
        }
    } //parseJSONWithJSONObject
}
