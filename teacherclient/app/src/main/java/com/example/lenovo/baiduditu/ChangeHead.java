package com.example.lenovo.baiduditu;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.baiduditu.myClass.MPermissionsActivity;
import com.example.lenovo.baiduditu.myClass.common;
import com.liji.takephoto.TakePhoto;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangeHead extends MPermissionsActivity implements View.OnClickListener, TakePhoto.onPictureSelected {
    private final int requestCode = 1000;//请求码
    private String [] request ={Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String image_name,user_id,my_path,mUrl,headImage;
    private TakePhoto takePhoto;//用来获取图片
    private OkHttpClient okHttpClient;
    CircleImageView circleImageView;
    TextView hd_queding;
    Button hd_back;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:String path = (String) msg.obj;
                //显示更改后的图像
                circleImageView.setImageBitmap(BitmapFactory.decodeFile(path));my_path=path;break;
                case 2:
                    common.myDailog("上传成功",ChangeHead.this);break;
                case 3:
                    Glide.with(ChangeHead.this).load("http://873717549-touxiang.stor.sinaapp.com/"+headImage).into(circleImageView);break;
                default:
                    common.myDailog(msg.obj.toString(),ChangeHead.this);break;
            }
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_head);
        common.setHeadBackground(getWindow());
        but();
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        mUrl ="http://1.873717549.applinzi.com/Android/Android_referHead.php";
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
    }

    private void but() {
        circleImageView = findViewById(R.id.head);
        takePhoto = new TakePhoto(this);//实例化对象
        takePhoto.setOnPictureSelected(this);//设置回调
        //创建网络请求对象
        okHttpClient = new OkHttpClient.Builder().build();
        circleImageView.setOnClickListener(this);
        hd_back = findViewById(R.id.hd_back);
        hd_back.setOnClickListener(this);
        hd_queding = findViewById(R.id.hd_queding);
        hd_queding.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.head:requestPermission(request,requestCode);;break;
            case R.id.hd_back:finish();break;
            case R.id.hd_queding:upload(my_path);
        }
    }
    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);
        if (requestCode == this.requestCode){
            takePhoto.show();//显示
        }
    }

    @Override
    public void select(final String path, String compresspaht) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    handler.obtainMessage(1,path).sendToTarget();
                } catch (InterruptedException e) {

                }

            }
        }).start();

        //    upload(path);  //-->只差后台数据接口
        //upload(compresspath);
    }//select
    private void upload(final String path) {

        File file = new File(path);//获取文件
        int last_index = path.lastIndexOf("/");
        image_name = path.substring(last_index+1);
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody requestBody = new MultipartBody.Builder()//表单
                .setType(MultipartBody.FORM)//设置类型
                .addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"userdata\""),
                        RequestBody.create(null, "张三"))
                .addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"pic\"; filename="+image_name), fileBody)
                .addFormDataPart("user_id",user_id)
                .addFormDataPart("image_name",image_name)
                .build();//构建
        //RequestBody requestBody =new FormBody.Builder().add("good_name").build();
        Request request = new Request.Builder()//创建请求
                .url("http://1.873717549.applinzi.com/upLoads/Android_head.php")//添加请求链接
                .post(requestBody)//添加请求体
                .build();//构建请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //上传成功后将发送消息让界面中图片刷新
                //                handler.obtainMessage(1,path).sendToTarget();

                String responseData = response.body().string();
                parseJSONWithJSONObject(responseData,true);
            }
        });
    }  //upload
    private void parseJSONWithJSONObject(String jsonData,boolean isUpdate) {
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            Message msg = new Message();
            if(isUpdate){
                String status = jsonObject.getString("status");
                if(status .equals("0")){
                    msg.what = 2;
                }else {
                    msg.what = 4;
                }
            }else {
                headImage = jsonObject.getString("headImage");
                msg.what = 3;
            }
            msg.obj = jsonData;
            handler.sendMessage(msg);
        }catch (Exception e){
            Message msg = new Message();
            msg.what = 5;
            msg.obj = e.getMessage();
            handler.sendMessage(msg);
        }
    } //parseJSONWithJSONObject
}
