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
import com.example.lenovo.baiduditu.adapter.DingdanAdapter;
import com.example.lenovo.baiduditu.model.Student;
import com.example.lenovo.baiduditu.model.VSign;
import com.example.lenovo.baiduditu.myClass.HttpUtil;
import com.example.lenovo.baiduditu.myClass.common;
import com.example.lenovo.baiduditu.utils.Constants;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class dingdanActivity extends AppCompatActivity {
    private Student student = new Student();
    private Button backBT;
    RecyclerView mRvMain;
    private SwipeRefreshLayout swipeRefresh;
    LoadingDialog ld;
    private List<VSign> signLists = new ArrayList<>();
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:ld.close();changeView();
                    break;
                case 2:ld.close();
                    Toast.makeText(dingdanActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(dingdanActivity.this,"系统异常",Toast.LENGTH_SHORT).show();
                    break;
                default:break;
            }
        }
    };
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dingdan);
        common.setHeadBackground(getWindow());

        student = (Student) getIntent().getSerializableExtra("student");
        if(student == null){
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
                signLists.clear();
                initView();
                swipeRefresh.setRefreshing(false);
            }
        });
        initView();
        ld = new LoadingDialog(dingdanActivity.this);
        ld.setLoadingText("加载中...").show();
    }
    //请求查看签到记录
    private void initView(){
        HttpUtil.getOkHttpRequest(Constants.STUGETRECORD_URL+"?stuNumber="+student.getStuNumber(), new Callback() {
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
        mRvMain.setLayoutManager(new LinearLayoutManager(dingdanActivity.this));
        mRvMain.setAdapter(new DingdanAdapter(dingdanActivity.this, signLists, new DingdanAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                System.out.println("onClick执行啦 "+signLists.get(position).getSetName());
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
                    VSign sign = new VSign();
                    JSONObject jo = jsonArray.getJSONObject(i);
                    if(jo!=null){
                        sign.setStuId(jo.getString("stuId"));
                        sign.setStuNumber(jo.getString("stuNumber"));
                        sign.setStuName(jo.getString("stuName"));
                        sign.setSetId(jo.getString("setId"));
                        sign.setSetName(jo.getString("setName"));
                        sign.setSigTime(jo.getString("sigTime"));
                        signLists.add(sign);
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
