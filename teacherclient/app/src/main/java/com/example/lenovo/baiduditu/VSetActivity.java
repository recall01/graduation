package com.example.lenovo.baiduditu;

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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lenovo.baiduditu.adapter.VSetAdapter;
import com.example.lenovo.baiduditu.model.VSet;
import com.example.lenovo.baiduditu.myClass.HttpUtil;
import com.example.lenovo.baiduditu.myClass.common;
import com.example.lenovo.baiduditu.myClass.good;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class VSetActivity extends AppCompatActivity {
    RecyclerView mRvMain;
    private List<VSet>vSets;
    LoadingDialog ld;
    String mUrl,user_id;
    private SwipeRefreshLayout swipeRefresh;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:ld.close();changeView();
                    break;
                case 2:ld.close();Toast.makeText(VSetActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;
                case 3:ld.close();Toast.makeText(VSetActivity.this,"网络状况不好，请稍后再试！",Toast.LENGTH_SHORT).show();
                    break;
                default:break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuzujie);
        common.setHeadBackground(getWindow());

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        vSets = new ArrayList<>();
        vSets = this.getvSetsData();
//        mUrl ="http://1.873717549.applinzi.com/Android_wuping.php";
        swipeRefresh =findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorhuise);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                vSets.clear();
//                refreshGood(mUrl);
            }
        });

//        HttpUtil.getOkHttpRequest(mUrl, new Callback() {
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
//        ld = new LoadingDialog(VSetActivity.this);
//        ld.setLoadingText("加载中...").show();
        btn();
    }//onCreat

    private void btn(){
        Button back = findViewById(R.id.back2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        LinearLayout layout = findViewById(R.id.shousuo);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VSetActivity.this, ClassInfoActivity.class);
                startActivity(intent);
            }
        });
    }



    private void refreshGood(String mUrl){
        HttpUtil.getOkHttpRequest(mUrl, new Callback() {
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
        swipeRefresh.setRefreshing(false);
    }  //refreshGood


    public void changeView(){
        mRvMain =findViewById(R.id.rv_main);
        mRvMain.setLayoutManager(new LinearLayoutManager(VSetActivity.this));
 //       mRvMain.addItemDecoration(new MyDecoration());
        mRvMain.setAdapter(new VSetAdapter(VSetActivity.this, vSets, new VSetAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos,String good_id) {

                Intent mIntent= new Intent();
                mIntent.putExtra("good_id",good_id);
                mIntent.putExtra("user_id",user_id);
                mIntent.setClass(VSetActivity.this, Zhujie_xinxiActivity.class);
            startActivityForResult(mIntent,0);
            }
        }));
    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        vSets.clear();
//        HttpUtil.getOkHttpRequest(mUrl, new Callback() {
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
//    }

    private void parseJSONWithJSONObject(String jsonData) {
        //good_name.clear();
        try{
            JSONArray allGoods = new JSONArray(jsonData);
            for (int i=0;i<allGoods.length();i++){
                JSONObject jsgood = allGoods.getJSONObject(i);
                int id =Integer.parseInt(jsgood.getString("good_id"));
                String name =jsgood.getString("good_name");
                String url =jsgood.getString("tupian_url");
                String num =jsgood.getString("good_num");
                String time =jsgood.getString("up_time");
                good oneGood =new good(id,name,url,num);
                oneGood.setTime(time);
//                vSets.add(oneGood);
            }
            Message message = new Message();
            message.what = 1;
            message.obj =vSets.size();
            handler.sendMessage(message);

        }catch (Exception e){
            Toast.makeText(VSetActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            Message message = new Message();
            message.what = 2;
            message.obj =e.getMessage();
            handler.sendMessage(message);
        }
    }
    private List<VSet> getvSetsData(){
        List<VSet> vSetList = new ArrayList<>();
        for (int i=0;i<10;i++){
            VSet set = new VSet();
            set.setClaId("claId"+i);
            set.setClaName("claName"+i);
            set.setSetName("设置签到名"+i);
            set.setLongitude("100"+i);
            set.setLatitude("100"+i);
            set.setScope("10"+i);
            set.setStartSigTime("2018-12-24 13:40:39");
            set.setEndSigTime("2018-12-25 13:40:39");
            set.setCreaterName("李四"+i);
            vSetList.add(set);
        }
        return vSetList;
    }
}
