package com.example.lenovo.baiduditu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lenovo.baiduditu.model.VSet;
import com.example.lenovo.baiduditu.myClass.common;
import com.example.lenovo.baiduditu.myClass.wp_detail;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class Zhujie_xinxiActivity extends AppCompatActivity {

    wp_detail good_detael;
    String user_id;
    Button zujie,button_determine,button_cancel;
    int good_id;
    AlertDialog dlg;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:setView();
                    break;
                case 2:Toast.makeText(Zhujie_xinxiActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;
                case 3:Toast.makeText(Zhujie_xinxiActivity.this,"3",Toast.LENGTH_SHORT).show();
                    break;
                case 4:if(msg.obj.toString().equals("1")){
                    dlg.dismiss();
                    common.myDailog("租借成功，在'我的资料'可以查询相关信息。",Zhujie_xinxiActivity.this);
                }else if(msg.obj.toString().equals("2")) {
                    common.myDailog("归还成功，请及时将物品归还物主。",Zhujie_xinxiActivity.this);
                }else {
                    Toast.makeText(Zhujie_xinxiActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                }
                    break;
                default:break;
            }
        }
    };

    private void setView() {
        ImageView tupian =findViewById(R.id.tupian);
        TextView neirong = findViewById(R.id.neirong);
        TextView tiaojian= findViewById(R.id.tiaojian);
        Glide.with(Zhujie_xinxiActivity.this).load("http://873717549-tupisn.stor.sinaapp.com/"+good_detael.getTupian_url()).into(tupian);
        neirong.setText(good_detael.getDet_describe());
        tiaojian.setText(good_detael.getDet_condition());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_zhujie_xinxi);

//        Intent intent = getIntent();
//        good_id = intent.getIntExtra("good_id",0);
//        user_id = intent.getStringExtra("user_id");
//        String mUrl ="http://1.873717549.applinzi.com/Android_wp_detail.php";
//        RequestBody requestBody =new FormBody.Builder().add("good_id", ""+good_id).build();
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
        VSet vSet = (VSet) getIntent().getSerializableExtra("vSet");
        if(vSet!=null){
            common.myDailog("班级名称:"+vSet.getSetName(),Zhujie_xinxiActivity.this);
        }
        zujie = findViewById(R.id.zujie);
        zujie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg = new AlertDialog.Builder(Zhujie_xinxiActivity.this).create();
                dlg.show();
                final Window window = dlg.getWindow();
                window.setGravity(Gravity.CENTER);
                window.setWindowAnimations(R.style.myStyle);
                window.setContentView(R.layout.zujie_tishi);
                button_determine = window.findViewById(R.id.queding_zujie);
                button_cancel = window.findViewById(R.id.quxiao_zujie);
                String mUrl ="http://1.873717549.applinzi.com/Android_zujie.php";
                initEvent(mUrl);
            }
        });
    }  //onCreate

    public void RequestHTTP(String mUrl){
        RequestBody requestBody =new FormBody.Builder().add("good_id", ""+good_id).add("user_id", user_id).build();
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
//                parseJSONWithJSONObject_zujie(responseData);
//            }
//        });
    }

    private void initEvent(final String mUrl) {
        button_determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestHTTP(mUrl);
            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Zhujie_xinxiActivity.this,"真是个明智的选择！",Toast.LENGTH_SHORT).show();
                dlg.dismiss();          //对话框移动到底部消失
            }
        });
    }//initEvent

    private void parseJSONWithJSONObject_zujie(String jsonData) {
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            String status = jsonObject.getString("status");
            Toast.makeText(Zhujie_xinxiActivity.this,status,Toast.LENGTH_SHORT).show();
            Message msg = new Message();
            msg.what = 4;
            msg.obj = status;
            handler.sendMessage(msg);
        }catch (Exception e){
            e.printStackTrace();
            Message msg = new Message();
            msg.what = 2;
            msg.obj = e.getMessage();
            handler.sendMessage(msg);
        }
    }


    private void parseJSONWithJSONObject(String jsonData) {
        //good_name.clear();
        good_detael =new wp_detail();
        try{
            JSONArray allGoods = new JSONArray(jsonData);
            for (int i=0;i<allGoods.length();i++){
                JSONObject jsgood = allGoods.getJSONObject(i);
                String describe =jsgood.getString("det_describe");
                String condition =jsgood.getString("det_condition");
                String url =jsgood.getString("tupian_url");
                good_detael.setDet_condition(condition);
                good_detael.setDet_describe(describe);
                good_detael.setTupian_url(url);
            }
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);
        }catch (Exception e){
          //  Toast.makeText(Tuzujie.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            Message message = new Message();
            message.what = 2;
            message.obj =e.getMessage();
            handler.sendMessage(message);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (event.getKeyCode()== KeyEvent.KEYCODE_BACK){
            Intent mIntent = new Intent();
            // 设置结果，并进行传送
            setResult(1,mIntent);
            finish();
        }
        return super.onKeyDown(keyCode,event);
    }
}
