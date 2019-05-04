package com.example.lenovo.baiduditu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lenovo.baiduditu.model.VO.TeacherVO;
import com.example.lenovo.baiduditu.model.VRecord;
import com.example.lenovo.baiduditu.model.VSet;
import com.example.lenovo.baiduditu.myClass.HttpUtil;
import com.example.lenovo.baiduditu.myClass.common;
import com.example.lenovo.baiduditu.utils.Constants;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VSetInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText stuIdET;
    private LoadingDialog ld;
    private List<VRecord> vRecords;
    private RecyclerView mRvMain;
    private Button backBT;
    private TextView sigNameTV,longitudeTV,latitudeTV,scopeTV,startTimeTV,endTimeTV;
    private VSet vSet;
    private TeacherVO teacher = new TeacherVO();
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:ld.close();break;
                case 1:ld.close();
                    common.myDailog(msg.obj.toString(),VSetInfoActivity.this);break;
                default:
                    common.myDailog(msg.obj.toString(),VSetInfoActivity.this);break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vsetinfo);
        common.setHeadBackground(getWindow());

        initView();
        teacher = (TeacherVO) getIntent().getSerializableExtra("teacher");
        String setId = (String) getIntent().getSerializableExtra("setId");
        if(teacher == null||StringUtils.isEmpty(setId)){
            return;
        }
        vSet = (VSet) getIntent().getSerializableExtra("vSet");
        if(vSet == null){
            //如果vSet为空就get请求获取数据
            loadVSetData();
        }else {
            changeView();
        }
        loadVRecordData(setId);
        ld = new LoadingDialog(VSetInfoActivity.this);
        ld.setLoadingText("加载中...").show();
    }

    private void loadVRecordData(String setId){
        HttpUtil.getOkHttpRequest(Constants.GETVSETS_URL+"?teaNumber="+teacher.getTeaNumber(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                parseJSONWithJSONObject(responseData);
            }
        });
    }
    private void loadVSetData(){

    }
    private void initView(){
        sigNameTV = findViewById(R.id.tv_sigName);
        longitudeTV = findViewById(R.id.tv_longitude);
        latitudeTV = findViewById(R.id.tv_latitude);
        scopeTV = findViewById(R.id.tv_scope);
        startTimeTV = findViewById(R.id.tv_startTime);
        endTimeTV = findViewById(R.id.tv_endTime);
        backBT = findViewById(R.id.bt_back);
        backBT.setOnClickListener(this);
        mRvMain =findViewById(R.id.dd_main);
    }



    private void changeView(){
        if(vSet == null){
            return;
        }
        sigNameTV.setText(vSet.getSetName());
        longitudeTV.setText(vSet.getLongitude());
        latitudeTV.setText(vSet.getLatitude());
        scopeTV.setText(vSet.getScope());
        startTimeTV.setText(vSet.getStartSigTime());
        endTimeTV.setText(vSet.getEndSigTime());
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
                ld = new LoadingDialog(VSetInfoActivity.this);
                ld.setLoadingText("加载中...").show();
                String stuId = stuIdET.getText().toString();
                RequestBody requestBody =new FormBody.Builder().add("claID",teacher.getAClass().getClaID()).add("stuNumber",stuId).build();
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
                HttpUtil.postEnqueueRequest(requestBody, Constants.ADDSTUDENT_URL, callback);
                break;
            default:break;
        }
    }

}
