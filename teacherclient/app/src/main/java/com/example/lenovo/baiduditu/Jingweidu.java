package com.example.lenovo.baiduditu;

import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.example.lenovo.baiduditu.model.MyMessage;
import com.example.lenovo.baiduditu.model.Sign;
import com.example.lenovo.baiduditu.myClass.common;
import com.example.lenovo.baiduditu.myClass.CryptoObjectHelper;
import com.example.lenovo.baiduditu.utils.Constants;
import com.google.gson.Gson;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Jingweidu extends AppCompatActivity {
    private LocationClient mLocationClient;
    private TextView Text;
    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private Sign sign;
    LoadingDialog ld;
    private boolean isFirstLoacate = true;
    private FingerprintManagerCompat manager;
    private View inflate;
    private Dialog dialog;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            Intent intent = new Intent(Jingweidu.this, MainActivity.class);
            MyMessage message = new MyMessage();
            message.what = msg.what;
            message.obj = msg.obj;
            intent.putExtra("message",message);
            setResult(0,intent);
            ld.close();
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_jingweidu);
        sign = (Sign)getIntent().getSerializableExtra("sign");

        Text = findViewById(R.id.position_text_view);
        mMapView = findViewById(R.id.mTexturemap);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        MapStatusUpdate zoomTo = MapStatusUpdateFactory.zoomTo(19f);
        mBaiduMap.animateMapStatus(zoomTo);

        requestLocation();
    }//oncreat

    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = RequestBody.create(Constants.JSONTYPE,new Gson().toJson(sign));
                    Request request = new Request.Builder().url(Constants.SIGN_URL).post(body).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData) {
        Message message = new Message();
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            int status = jsonObject.getInt("status");
            message.what = status;
            message.obj = jsonObject.getString("msg");
        }catch (Exception e){
            e.printStackTrace();
            message.what = 5;
            message.obj = e.getMessage();
        }finally {
            handler.sendMessage(message);
        }
    }

    public void showDialog(){
        dialog = new Dialog(this,R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.actionsheetdialog, null);
        //初始化控件
        TextView finger_back = inflate.findViewById(R.id.finger_back);
        finger_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent();
                // 设置结果，并进行传送
                setResult(0,mIntent);
                finish();
            }
        });
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
        lp.width = (int)(display.getWidth());  //设置Dialog宽度等于屏宽
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.setCancelable(false);
        dialog.show();//显示对话框
    }  //showdialog





    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void navigateTo(BDLocation location) throws Exception {
        if (isFirstLoacate){
            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(update);
            isFirstLoacate = false;
        //    manager = FingerprintManagerCompat.from(this);
            if(decideFinger()){
                CryptoObjectHelper cryptoObjectHelper = new CryptoObjectHelper();
                if(cryptoObjectHelper!=null){
                    showDialog();
                    manager.authenticate(cryptoObjectHelper.buildCryptoObject(), 0, null, new MyCallBack(), null);
                }else {
                    finish();
                }
            }else {
                finish();
            }

        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        mBaiduMap.setMyLocationData(locationData);
    }//navigateTo
    //验证设备条件是否具备
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private boolean decideFinger(){
        manager = FingerprintManagerCompat.from(this);
        if(!manager.isHardwareDetected()){
            common.myDailog("当前设备不支持指纹识别！",Jingweidu.this);
            return false;
        }else{
            KeyguardManager keyguardManager =(KeyguardManager)getSystemService(Jingweidu.KEYGUARD_SERVICE);
            if(!keyguardManager.isKeyguardSecure()){
                common.myDailog("当前设备未使用屏幕锁保护！",Jingweidu.this);
                return false;
            }else {
                if(manager.hasEnrolledFingerprints()){
                    return true;
                }else {
                    common.myDailog("当前设备没有录入指纹！",Jingweidu.this);
                    return false;
                }
            }
        }
    }  //decideFinger
    public class MyCallBack extends FingerprintManagerCompat.AuthenticationCallback {

        // 当出现错误的时候回调此函数，比如多次尝试都失败了的时候，errString是错误信息
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            common.myDailog("onAuthenticationError: " + errString,Jingweidu.this);
        }

        // 当指纹验证失败的时候会回调此函数，失败之后允许多次尝试，失败次数过多会停止响应一段时间然后再停止sensor的工作
        @Override
        public void onAuthenticationFailed() {
            common.myDailog("onAuthenticationFailed: " + "验证失败",Jingweidu.this);
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            common.myDailog("onAuthenticationHelp: " + helpString,Jingweidu.this);
        }
        // 当验证的指纹成功时会回调此函数，然后不再监听指纹sensor
        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            ld = new LoadingDialog(Jingweidu.this);
            ld.setLoadingText("加载中...").show();
            sendRequestWithOkHttp();
            dialog.dismiss();
        }
    }//MyCallBack

@Override
protected void onResume(){
    super.onResume();
    mMapView.onResume();
}
@Override
protected void onPause(){
        super.onPause();
        mMapView.onPause();
}
@Override
protected void onDestroy(){
    super.onDestroy();
    mLocationClient.stop();
    mMapView.onDestroy();
    mBaiduMap.setMyLocationEnabled(false);
}

    private void requestLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setScanSpan(1000);
        option.setOpenGps(true);
        option.setIsNeedAddress(true);
        option.setIsNeedLocationDescribe(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    public class MyLocationListener extends BDAbstractLocationListener implements BDLocationListener {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onReceiveLocation(BDLocation location) {
            String str ="纬度："+location.getLatitude()+"\n";
            str = str +"经线："+location.getLongitude()+"\n";
            str = str +"国家："+location.getCity()+"\n";
            sign.setLatitude(String.valueOf(location.getLatitude()));
            sign.setLongitude(String.valueOf(location.getLongitude()));
            str = str +"省："+location.getProvince()+"\n";
            str = str +"市："+location.getCity()+"\n";
            str = str +"区："+location.getDistrict()+"\n";
            str = str +"街道："+location.getStreet()+"\n";
            str = str +"描述："+location.getLocationDescribe()+"\n";
            str = str +"定位方式：";
            if(location.getLocType() ==BDLocation.TypeGpsLocation){
                str = str +"GPS";
            }else if(location.getLocType() ==BDLocation.TypeNetWorkLocation){
                str = str +"网络";
            }
            if(location.getLocType() ==BDLocation.TypeGpsLocation||location.getLocType() ==BDLocation.TypeNetWorkLocation){
                try {
                    navigateTo(location);
                }catch (Exception e){
                    common.myDailog("navigateTo() Exception:"+e.getMessage(),Jingweidu.this);
                }

            }
            Text.setText(str);
        }
    }
}
