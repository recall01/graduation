package com.example.lenovo.baiduditu;

import android.Manifest;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lenovo.baiduditu.myClass.common;
import com.example.lenovo.baiduditu.myClass.MPermissionsActivity;
import com.liji.takephoto.TakePhoto;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Chuzu extends MPermissionsActivity implements TakePhoto.onPictureSelected{
    Button back1,btn_chuzu;
    ImageView cz_image;
    EditText wp_name,wp_shuoming,wp_jiage,wp_shuliang;
    private TakePhoto takePhoto;
    private String [] request ={Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private final int requestCode = 1000;//请求码
    String image_name,my_path;
    private OkHttpClient okHttpClient;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1){
                String path = (String) msg.obj;
                //显示更改后的图像
                //http://873717549-tupisn.stor.sinaapp.com/magazine-unlock-01-2.3.848-_2b947b572126491aa4df568c9685eedd.jpg
                //Glide.with(Chuzu.this).load("http://873717549-tupisn.stor.sinaapp.com/"+image_name).into(iamge);
                cz_image.setImageBitmap(BitmapFactory.decodeFile(path));
                my_path = path;
            }else{
                Toast.makeText(Chuzu.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuzu);
        common.setHeadBackground(getWindow());

        btn();
    }
    public void btn(){
        takePhoto = new TakePhoto(this);//实例化对象
        takePhoto.setOnPictureSelected(this);//设置回调
        okHttpClient = new OkHttpClient.Builder().build();
        cz_image = findViewById(R.id.sc_image);
        cz_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission(request,requestCode);
            }
        });
        back1 = findViewById(R.id.back3);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_chuzu = findViewById(R.id.btn_chuzu);
        wp_name = findViewById(R.id.wp_name);
        wp_shuoming = findViewById(R.id.wp_shuoming);
        wp_jiage = findViewById(R.id.wp_jiage);
        wp_shuliang = findViewById(R.id.wp_shuliang);




        btn_chuzu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(wp_name.getText().toString().equals("")||wp_shuoming.getText().toString().equals("")||wp_jiage.getText().toString().equals("")||wp_shuliang.getText().toString().equals("")){
                    Toast.makeText(Chuzu.this,"你还没输入信息！",Toast.LENGTH_SHORT).show();
                }else {
                    if(my_path!=null){
                        upload(my_path);
                    }else {
                        Toast.makeText(Chuzu.this,"文件路径错误！",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }  //btn


    @Override
    public void select(final String path, final String compresspath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    handler.obtainMessage(1,compresspath).sendToTarget();
                } catch (InterruptedException e) {
                    Toast.makeText(Chuzu.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        }).start();
    //    my_path =compresspath;
        //    upload(path);  //-->只差后台数据接口
    //    upload(compresspath);
    }  //select

    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);
        if (requestCode == this.requestCode){
            takePhoto.show();//显示
        }
    }   //permissionSuccess

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
                .addFormDataPart("wp_name",wp_name.getText().toString())
                .addFormDataPart("wp_shuliang",wp_shuliang.getText().toString())
                .addFormDataPart("wp_shuoming",wp_shuoming.getText().toString())
                .addFormDataPart("wp_jiage",wp_jiage.getText().toString())
                .addFormDataPart("image_name",image_name)
                .build();//构建
        //RequestBody requestBody =new FormBody.Builder().add("good_name").build();
        Request request = new Request.Builder()//创建请求
                .url("http://1.873717549.applinzi.com/upLoads/Android_tupian.php")//添加请求链接
                .post(requestBody)//添加请求体
                .build();//构建请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(Chuzu.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //上传成功后将发送消息让界面中图片刷新
                //                handler.obtainMessage(1,path).sendToTarget();

                String responseData = response.body().string();
                parseJSONWithJSONObject(responseData,my_path);
            }
        });
    }  //upload
    private void parseJSONWithJSONObject(String jsonData,String path) {
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            String status = jsonObject.getString("status");
            Message msg = new Message();
            if(status .equals("0")){
                msg.what = 1;
            }else {
                msg.what = 4;
            }
            msg.obj = jsonData;
            handler.sendMessage(msg);
        }catch (Exception e){
            Message msg = new Message();
            msg.what = 2;
            msg.obj = e.getMessage();
            handler.sendMessage(msg);
        }
    } //parseJSONWithJSONObject

}
