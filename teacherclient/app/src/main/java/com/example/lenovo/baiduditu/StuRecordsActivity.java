package com.example.lenovo.baiduditu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lenovo.baiduditu.adapter.StuRecordsAdapter;
import com.example.lenovo.baiduditu.model.Student;
import com.example.lenovo.baiduditu.model.VRecord;
import com.example.lenovo.baiduditu.myClass.HttpUtil;
import com.example.lenovo.baiduditu.utils.common;
import com.example.lenovo.baiduditu.utils.Constants;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class StuRecordsActivity extends AppCompatActivity {
    private Student student;
    private Button backBT,addBT;
    RecyclerView mRvMain;
    private SwipeRefreshLayout swipeRefresh;
    LoadingDialog ld;
    private List<VRecord> vRecords = new ArrayList<>();
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:ld.close();changeView();
                    break;
                case 2:ld.close();
                    Toast.makeText(StuRecordsActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;
                default:break;
            }
        }
    };
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sturecords);
        common.setHeadBackground(getWindow());

        student = (Student) getIntent().getSerializableExtra("student");
        String stuNumber = (String) getIntent().getSerializableExtra("stuNumber");
        if(StringUtils.isEmpty(stuNumber)){
            return;
        }
        if(student == null){
            loadStudentData(stuNumber);
        }

        backBT = findViewById(R.id.bt_back);
        backBT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeColors(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                vRecords.clear();
                initView();
                swipeRefresh.setRefreshing(false);
            }
        });
        ld = new LoadingDialog(StuRecordsActivity.this);
        ld.setLoadingText("加载中...").show();
        initView();
    }

    private void loadStudentData(String stuNumber){

    }
    //请求查看签到记录
    private void initView(){
        HttpUtil.getOkHttpRequest(Constants.STUGETRECORD_URL+"?stuNumber="+student.getStuNumber(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                parseJSONWithJSONObject(responseData);
            }
        });
    }

    public void changeView(){
        mRvMain =findViewById(R.id.dd_main);
        mRvMain.setLayoutManager(new LinearLayoutManager(StuRecordsActivity.this));
        mRvMain.setAdapter(new StuRecordsAdapter(StuRecordsActivity.this, vRecords));
    }//changeView()
    private void parseJSONWithJSONObject(String jsonData) {
        Message message = new Message();
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            int status = jsonObject.getInt("status");
            if(status == 200){
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for(int i=0;i<jsonArray.length();i++){
                    VRecord record = new VRecord();
                    JSONObject jo = jsonArray.getJSONObject(i);
                    if(jo!=null){
                        record.setStuId(jo.getString("stuId"));
                        record.setStuNumber(jo.getString("stuNumber"));
                        record.setStuName(jo.getString("stuName"));
                        record.setSetId(jo.getString("setId"));
                        record.setSetName(jo.getString("setName"));
                        record.setSigTime(jo.getString("sigTime"));
                        vRecords.add(record);
                    }
                }
            }
            message.what = 1;
            message.obj =  status;
        }catch (Exception e){
            e.printStackTrace();
            message.what = 2;
            message.obj = e.getMessage();
        }finally {
            handler.sendMessage(message);
        }

    } //parseJSONWithJSONObject

}
