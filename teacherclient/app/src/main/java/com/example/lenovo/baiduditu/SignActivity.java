package com.example.lenovo.baiduditu;

import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.lenovo.baiduditu.model.Set;
import com.example.lenovo.baiduditu.model.Sign;
import com.example.lenovo.baiduditu.model.VO.TeacherVO;
import com.example.lenovo.baiduditu.model.VSet;
import com.example.lenovo.baiduditu.myClass.HttpUtil;
import com.example.lenovo.baiduditu.myClass.common;
import com.example.lenovo.baiduditu.myClass.CryptoObjectHelper;
import com.example.lenovo.baiduditu.utils.Constants;
import com.example.lenovo.baiduditu.utils.TimeUtils;
import com.google.gson.Gson;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignActivity extends AppCompatActivity implements View.OnClickListener {
    private LocationClient mLocationClient;
    //private TextureMapView mMapView;
    private TextView determineTV;
    private ImageView startTimeIV,endTimeIV;
    private Button backBT,locateBT;
    private EditText sigNameET,longitudeET,latitudeET,startTimeET,endTimeET,scopeET;
    private TeacherVO teacher;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:common.myToast(SignActivity.this,msg.obj.toString());break;
                case 2:common.myToast(SignActivity.this,msg.obj.toString());finish();break;
                default:break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_sign);
        common.setHeadBackground(getWindow());

        teacher = (TeacherVO) getIntent().getSerializableExtra("teacher");
        if(teacher == null){
            return;
        }
        initView();
    }//oncreat


    private void initView(){
        sigNameET = findViewById(R.id.et_sigName);
        longitudeET = findViewById(R.id.et_longitude);
        latitudeET = findViewById(R.id.et_latitude);
        backBT = findViewById(R.id.bt_back);
        backBT.setOnClickListener(this);
        locateBT = findViewById(R.id.bt_locate);
        locateBT.setOnClickListener(this);
        //获取当前时间
        String nowTime = TimeUtils.getTime();
        startTimeET = findViewById(R.id.et_startTime);
        startTimeET.setText(nowTime);
        startTimeIV = findViewById(R.id.iv_startTime);
        startTimeIV.setOnClickListener(this);
        endTimeET = findViewById(R.id.et_endTime);
        endTimeET.setText(nowTime);
        endTimeIV = findViewById(R.id.iv_endTime);
        endTimeIV.setOnClickListener(this);
        determineTV = findViewById(R.id.tv_determine);
        determineTV.setOnClickListener(this);
        scopeET = findViewById(R.id.et_scope);
    }

    private void parseJSONWithJSONObject(String jsonData) {
        Message message = new Message();
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            int status = jsonObject.getInt("status");
            if(status == 200){
                message.what = 2;
            }else {
                message.what = 1;
            }
            message.obj = jsonObject.getString("msg");
        }catch (Exception e){
            e.printStackTrace();
            message.what = 1;
            message.obj = e.getMessage();
        }finally {
            handler.sendMessage(message);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_back:finish();break;
            case R.id.bt_locate:requestLocation();break;
            case R.id.iv_startTime:getDate(startTimeET);break;
            case R.id.iv_endTime:getDate(endTimeET);break;
            case R.id.tv_determine:saveSign();break;
            default:break;
        }
    }


    private void saveSign(){
        Set set = new Set();
        set.setSetName(sigNameET.getText().toString());
        set.setClaID(teacher.getAClass().getClaID());
        set.setLongitude(longitudeET.getText().toString());
        set.setLatitude(latitudeET.getText().toString());
        set.setScope(scopeET.getText().toString());
        set.setStartSigTime(startTimeET.getText().toString());
        set.setEndSigTime(endTimeET.getText().toString());
        set.setCreaterID(teacher.getTeaNumber());
        RequestBody body = RequestBody.create(Constants.JSONTYPE,new Gson().toJson(set));
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
        HttpUtil.postEnqueueRequest(body, Constants.CREATSET_URL, callback);
    }

    private void getDate(EditText et) {
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);       //获取年月日时分秒
        int month=cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        int day=cal.get(Calendar.DAY_OF_MONTH);
        showDatePickerDialog(et,year,month,day);
    }//getDate
    private void showDatePickerDialog(final EditText et, int year, int month, int day){
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                et.setText(i+"-"+(i1+1)+"-"+i2+" 00:00:00");
            }
        };
        DatePickerDialog dialog=new DatePickerDialog(SignActivity.this, 0,listener,year,month,day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
        dialog.show();
    }

@Override
protected void onResume(){
    super.onResume();
//    mMapView.onResume();
}
@Override
protected void onPause(){
        super.onPause();
//        mMapView.onPause();
}
@Override
protected void onDestroy(){
    super.onDestroy();
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
            latitudeET.setText(String.valueOf(location.getLatitude()));
            longitudeET.setText(String.valueOf(location.getLongitude()));
        }
    }
}
