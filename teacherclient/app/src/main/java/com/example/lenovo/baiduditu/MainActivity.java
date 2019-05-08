package com.example.lenovo.baiduditu;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import com.example.lenovo.baiduditu.model.Classes;
import com.example.lenovo.baiduditu.model.VO.TeacherVO;
import com.example.lenovo.baiduditu.myClass.HttpUtil;
import com.example.lenovo.baiduditu.utils.common;
import com.example.lenovo.baiduditu.utils.Constants;
import com.example.lenovo.baiduditu.view.ChangeIcon;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends FragmentActivity implements  ViewPager.OnPageChangeListener,View.OnClickListener {
    Button studentsBT,classInfoBT,signBT,signdataBT,exitBT,setClassBT;
    TeacherVO teacher = new TeacherVO();
    private List<ChangeIcon> lTabIndicators = new ArrayList<>();
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    common.myDailog(""+msg.obj,MainActivity.this);
                    break;
                case 1:break;
                default:break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mian);
//        activityCollector.addActivity(this);
        common.setHeadBackground(getWindow());

        initView();
        teacher = (TeacherVO) getIntent().getSerializableExtra("teacher");

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(MainActivity.this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_BROADCAST");
        BroadcastReceiver mItemviewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                teacher = (TeacherVO) intent.getSerializableExtra("teacher");
                //获取teacher最新数据
                loadTeacherData();
            }
        };
        broadcastManager.registerReceiver(mItemviewListClickReceiver, intentFilter);

    }  //onCreat
    private void initView(){
        studentsBT = findViewById(R.id.bt_students);
        studentsBT.setOnClickListener(this);
        classInfoBT = findViewById(R.id.bt_classInfo);
        classInfoBT.setOnClickListener(this);
        signBT = findViewById(R.id.bt_sign);
        signBT.setOnClickListener(this);
        signdataBT = findViewById(R.id.bt_signdata);
        signdataBT.setOnClickListener(this);
        exitBT = findViewById(R.id.bt_exit);
        exitBT.setOnClickListener(this);
        setClassBT = findViewById(R.id.bt_setclass);
        setClassBT.setOnClickListener(this);
    }
    private void loadTeacherData(){
        RequestBody requestBody =new FormBody.Builder().add("teaId",teacher.getTeaId()).build();
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 0;
                message.obj = e.getMessage();
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                parseJSONWithJSONObject(responseData);
            }
        };
        HttpUtil.postEnqueueRequest(requestBody, Constants.GETTEACHERBYID_URL, callback);
    }
    private void parseJSONWithJSONObject(String jsonData) {
        Message message = new Message();
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            int status = jsonObject.getInt("status");
            if(status==200){
                JSONObject js = jsonObject.getJSONObject("data");
                if(js!=null){
                    teacher.setTeaId(js.getString("teaID"));
                    teacher.setTeaNumber(js.getString("teaNumber"));
                    teacher.setTeaName(js.getString("teaName"));
                    teacher.setTeaPhone(js.getString("teaPhone"));
                    teacher.setRegisterTime(js.getString("registerTime"));
                    teacher.setPermissions(js.getString("permissions"));
                    if (!js.isNull("aClass")){
                        JSONObject aclass = js.getJSONObject("aClass");
                        if(aclass != null){
                            Classes classes = new Classes();
                            classes.setClaID(aclass.getString("claID"));
                            classes.setClaName(aclass.getString("claName"));
                            classes.setCreateTime(aclass.getString("createTime"));
                            classes.setCreaterID(aclass.getString("createrID"));
                            classes.setDynamic(aclass.getString("dynamic"));
                            teacher.setAClass(classes);
                        }
                    }
                }
                message.what = 1;
            }else {
                message.what = 0;
            }
            message.obj = jsonObject.getString("msg");
        }catch (Exception e){
            e.printStackTrace();
            message.obj = e.getMessage();
            message.what = 0;
        }finally {
            handler.sendMessage(message);
        }
    }

    protected void onRestart(){
        super.onRestart();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0)     //如果偏移值大于0
        {
            ChangeIcon left = lTabIndicators.get(position);     //获取向左滑下标
            ChangeIcon right = lTabIndicators.get(position+1);  //获取向右滑下标
            left.setIconAlpha(1 - positionOffset);      //设置左侧图标透明，右侧变色
            right.setIconAlpha(positionOffset);       //设置右侧图标透明，左侧变色
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_students:
                if(teacher.getAClass() != null){
                    Intent intent = new Intent(MainActivity.this,StudentsActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("teacher",teacher);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }else {
                    common.myDailog("你还没有班级,请先创建班级",MainActivity.this);
                }
                break;
            case R.id.bt_setclass:
                if(teacher.getAClass() == null){
                    Intent intent = new Intent(MainActivity.this,AddClassActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("teacher",teacher);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }else {
                    common.myDailog("你已经创建过班级了",MainActivity.this);
                }
                break;
            case R.id.bt_classInfo:
                if(teacher.getAClass() != null){
                    Intent intent = new Intent(MainActivity.this,ClassInfoActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("teacher",teacher);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }else {
                    common.myDailog("你还没有班级,请先创建班级",MainActivity.this);
                }
                break;
            case R.id.bt_sign:
                if(teacher.getAClass() != null){
                    Intent intent = new Intent(MainActivity.this,SignActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("teacher",teacher);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }else {
                    common.myDailog("你还没有班级,请先创建班级",MainActivity.this);
                }
                break;
            case R.id.bt_signdata:
                if(teacher.getAClass() != null){
                    Intent intent = new Intent(MainActivity.this,VSetsDataActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("teacher",teacher);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }else {
                    common.myDailog("你还没有班级,请先创建班级",MainActivity.this);
                }
                break;
            case R.id.bt_exit:
                exitAPP();break;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void exitAPP() {
        ActivityManager activityManager = (ActivityManager) MainActivity.this.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.AppTask> appTaskList = activityManager.getAppTasks();
        for (ActivityManager.AppTask appTask : appTaskList) {
            appTask.finishAndRemoveTask();
        }
    }
}
