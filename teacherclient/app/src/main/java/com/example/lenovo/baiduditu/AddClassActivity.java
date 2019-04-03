package com.example.lenovo.baiduditu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lenovo.baiduditu.model.Classes;
import com.example.lenovo.baiduditu.model.VO.TeacherVO;
import com.example.lenovo.baiduditu.myClass.HttpUtil;
import com.example.lenovo.baiduditu.myClass.common;
import com.example.lenovo.baiduditu.utils.Constants;
import com.google.gson.Gson;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddClassActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText claNameET;
    private LoadingDialog ld;
    private Button addBT,backBT;
    private TeacherVO teacher = new TeacherVO();
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:ld.close();break;
                case 1:ld.close();
                    common.myDailog(msg.obj.toString(),AddClassActivity.this);break;
                default:
                    common.myDailog(msg.obj.toString(),AddClassActivity.this);break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addclass);
        common.setHeadBackground(getWindow());

        teacher = (TeacherVO) getIntent().getSerializableExtra("teacher");
        if(teacher == null){
            return;
        }
        claNameET = findViewById(R.id.et_claName);
        addBT = findViewById(R.id.bt_add);
        addBT.setOnClickListener(this);
        backBT = findViewById(R.id.bt_back);
        backBT.setOnClickListener(this);
    }
    private void parseJSONWithJSONObject(String jsonData) {
        Message message = new Message();
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            message.what = 1;
            message.obj = jsonObject.getString("msg");
        }catch (Exception e){
            e.printStackTrace();
            message.obj = e.getMessage();
            message.what = 1;
        }finally {
            handler.sendMessage(message);
        }
    }  //parseJSONWithJSONObject


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_back:
                finish();break;
            case R.id.bt_add:
                ld = new LoadingDialog(AddClassActivity.this);
                ld.setLoadingText("加载中...").show();
                String claName = claNameET.getText().toString();
                if(claName == null || "".equals(claName)){
                    common.myToast(AddClassActivity.this,"添加班级名不能为空");
                    ld.close();
                }else {
                    try{
                        Classes aClass = new Classes();
                        aClass.setClaName(claName);
                        teacher.setAClass(aClass);
                        System.out.println("老师数据"+new Gson().toJson(teacher));
                        RequestBody body = RequestBody.create(Constants.JSONTYPE,new Gson().toJson(teacher));
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
                        HttpUtil.postEnqueueRequest(body, Constants.CREATCLASS_URL, callback);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            default:break;
        }
    }

}
