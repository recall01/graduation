package com.example.lenovo.baiduditu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lenovo.baiduditu.adapter.DingdanAdapter;
import com.example.lenovo.baiduditu.myClass.common;
import com.example.lenovo.baiduditu.myClass.dingdan;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class dingdanActivity extends AppCompatActivity {
    String user_id,mUrl;
    RecyclerView mRvMain;
    LoadingDialog ld;
    private List<dingdan> dingdans;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:ld.close();changeView();
                    break;
                case 2:ld.close();
                    Toast.makeText(dingdanActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;
                case 3:ld.close();Toast.makeText(dingdanActivity.this,"网络状况不好，请稍后再试！",Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    common.myDailog("归还成功！",dingdanActivity.this);
                    break;
                default:break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dingdan);

        common.setHeadBackground(getWindow());
        Intent intent = getIntent();
        Button back2 = findViewById(R.id.back2);
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        user_id = intent.getStringExtra("user_id");
        dingdans = new ArrayList<dingdan>();
        mUrl ="http://1.873717549.applinzi.com/Android_dingdan.php";
        RequestBody requestBody =new FormBody.Builder().add("user_id", user_id).build();
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
//                parseJSONWithJSONObject(responseData,false);
//            }
//        });
        ld = new LoadingDialog(dingdanActivity.this);
        ld.setLoadingText("加载中...").show();
    }

    public void changeView(){
        mRvMain =findViewById(R.id.dd_main);
        mRvMain.setLayoutManager(new LinearLayoutManager(dingdanActivity.this));
        //       mRvMain.addItemDecoration(new MyDecoration());
        mRvMain.setAdapter(new DingdanAdapter(dingdanActivity.this, dingdans, new DingdanAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                dingdan oneDingdan =new dingdan();
                oneDingdan.setid(dingdans.get(position).getid());
                oneDingdan.setgname(dingdans.get(position).getgname());
                oneDingdan.settime(dingdans.get(position).gettime());
                oneDingdan.setprice(dingdans.get(position).getprice());
                common.myToast(dingdanActivity.this,oneDingdan.getid()+oneDingdan.getgname()+oneDingdan.gettime()+oneDingdan.getprice());
                String mUrl ="http://1.873717549.applinzi.com/Android_guihuan.php";
                RequestHTTP(mUrl,oneDingdan);
            }
        }));
    }//changeView()
    public void RequestHTTP(String mUrl,dingdan one){
        RequestBody requestBody =new FormBody.Builder().add("good_id", ""+one.getid()).add("order_time", ""+one.gettime()).add("user_id", user_id).build();
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
//                parseJSONWithJSONObject(responseData,true);
//            }
//        });
    }  //RequestHTTP
    private void parseJSONWithJSONObject(String jsonData,boolean isReturn) {
            try{
                if(isReturn){
                    JSONObject jsonObject = new JSONObject(jsonData);
                    String status = jsonObject.getString("status");
                    Message msg = new Message();
                    msg.what = 4;
                    msg.obj = status;
                    handler.sendMessage(msg);
                }else {
                    JSONArray allGoods = new JSONArray(jsonData);
                    for (int i=0;i<allGoods.length();i++){
                        JSONObject jsgood = allGoods.getJSONObject(i);
                        String name =jsgood.getString("good_name");
                        String time =jsgood.getString("order_time");
                        String price =jsgood.getString("order_price");
                        String id =jsgood.getString("good_id");
                        dingdan oneDingdan =new dingdan(name,time,price,id);
                        dingdans.add(oneDingdan);
                    }
                    Message message = new Message();
                    message.what = 1;
                    message.obj =dingdans.size();
                    handler.sendMessage(message);
                }
            }catch (Exception e){
                e.printStackTrace();
                Message msg = new Message();
                msg.what = 2;
                msg.obj = e.getMessage();
                handler.sendMessage(msg);
            }

    } //parseJSONWithJSONObject

}
