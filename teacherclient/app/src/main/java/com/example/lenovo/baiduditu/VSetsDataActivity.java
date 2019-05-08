package com.example.lenovo.baiduditu;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import com.example.lenovo.baiduditu.adapter.VSetsAdapter;
import com.example.lenovo.baiduditu.model.VO.TeacherVO;
import com.example.lenovo.baiduditu.model.VSet;
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

public class VSetsDataActivity extends AppCompatActivity {
    private TeacherVO teacher = new TeacherVO();
    private Button backBT;
    RecyclerView mRvMain;
    private SwipeRefreshLayout swipeRefresh;
    LoadingDialog ld;
    private List<VSet> vSets = new ArrayList<>();
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:ld.close();changeView();
                    break;
                case 2:ld.close();
                    Toast.makeText(VSetsDataActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(VSetsDataActivity.this,"系统异常",Toast.LENGTH_SHORT).show();
                    break;
                default:break;
            }
        }
    };
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vsets);
        common.setHeadBackground(getWindow());

        teacher = (TeacherVO) getIntent().getSerializableExtra("teacher");
        if(teacher == null){
            return;
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
                vSets.clear();
                initView();
                swipeRefresh.setRefreshing(false);
            }
        });
        if(teacher.getAClass()!=null){
            initView();
            ld = new LoadingDialog(VSetsDataActivity.this);
            ld.setLoadingText("加载中...").show();
        }

    }
    //请求查看签到记录
    private void initView(){
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

    public void changeView(){
        mRvMain =findViewById(R.id.dd_main);
        mRvMain.setLayoutManager(new LinearLayoutManager(VSetsDataActivity.this));
        mRvMain.setAdapter(new VSetsAdapter(VSetsDataActivity.this, vSets, new VSetsAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position,String setId) {
                if(!StringUtils.isEmpty(setId)){
                    Intent intent = new Intent(VSetsDataActivity.this,VSetInfoActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("teacher",teacher);
                    for (int i=0;i<vSets.size();i++){
                        if(vSets.get(i).getSetId() == setId){
                            VSet vSet = vSets.get(i);
                            mBundle.putSerializable("vSet",vSet);
                            break;
                        }
                    }
                    mBundle.putSerializable("setId",setId);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }else {
                    common.myToast(VSetsDataActivity.this,"传递数据不能为空");
                }

            }
        }));
    }//changeView()
    private void parseJSONWithJSONObject(String jsonData) {
        Message message = new Message();
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            int status = jsonObject.getInt("status");
            if(status == 200){
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for(int i=0;i<jsonArray.length();i++){
                    VSet vSet = new VSet();
                    JSONObject jo = jsonArray.getJSONObject(i);
                    if(jo!=null){
                        vSet.setSetId(jo.getString("setID"));
                        vSet.setSetName(jo.getString("setName"));
                        vSet.setLongitude(jo.getString("longitude"));
                        vSet.setLatitude(jo.getString("latitude"));
                        vSet.setScope(jo.getString("scope"));
                        vSet.setStartSigTime(jo.getString("startSigTime"));
                        vSet.setEndSigTime(jo.getString("endSigTime"));
                        vSets.add(vSet);
                    }
                }
            }
            message.what = 1;
            message.obj =  status;
        }catch (Exception e){
            e.printStackTrace();
            message.what = 3;
            message.obj = e.getMessage();
        }finally {
            handler.sendMessage(message);
        }

    } //parseJSONWithJSONObject

}
