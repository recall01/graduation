package com.example.lenovo.baiduditu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.lenovo.baiduditu.fragment.frag_a;
import com.example.lenovo.baiduditu.model.MyMessage;
import com.example.lenovo.baiduditu.model.Student;
import com.example.lenovo.baiduditu.model.Teacher;
import com.example.lenovo.baiduditu.model.VO.TeacherVO;
import com.example.lenovo.baiduditu.myClass.activityCollector;
import com.example.lenovo.baiduditu.myClass.common;
import com.example.lenovo.baiduditu.view.ChangeIcon;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements  ViewPager.OnPageChangeListener,View.OnClickListener {
    Button studentsBT,signBT,signdataBT,exitBT,setClassBT;
    TeacherVO teacher = new TeacherVO();
    private List<ChangeIcon> lTabIndicators = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mian);
//        activityCollector.addActivity(this);
        common.setHeadBackground(getWindow());

        initView();
        teacher = (TeacherVO) getIntent().getSerializableExtra("teacher");
/*        if(teacher!=null){
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragment_title,frag_a.newInstance(teacher)).commitAllowingStateLoss();
        }*/

    }  //onCreat
    private void initView(){
        studentsBT = findViewById(R.id.bt_students);
        studentsBT.setOnClickListener(this);
        signBT = findViewById(R.id.bt_sign);
        signBT.setOnClickListener(this);
        signdataBT = findViewById(R.id.bt_signdata);
        signdataBT.setOnClickListener(this);
        exitBT = findViewById(R.id.bt_exit);
        exitBT.setOnClickListener(this);
        setClassBT = findViewById(R.id.bt_setclass);
        setClassBT.setOnClickListener(this);
    }



    //0为跳转签到界面
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == 0){
            MyMessage message = (MyMessage) data.getSerializableExtra("message");
            if (message.what == 200){
                common.myToast(MainActivity.this,"签到成功!");
            }else {
                common.myDailog(""+message.obj,MainActivity.this);
            }
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_title,frag_a.newInstance(student)).commitAllowingStateLoss();
        }else {
            System.out.println("resultCode的值:"+resultCode);
        }

    }//onActivityResult


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

            case R.id.bt_sign:
                System.out.println("按钮bt_sign");
                common.myToast(MainActivity.this,"bt_sign");break;
            case R.id.bt_signdata:
                System.out.println("按钮bt_signdata");
                common.myToast(MainActivity.this,"bt_signdata");break;
            case R.id.bt_exit:
                System.out.println("按钮bt_exit");
                common.myToast(MainActivity.this,"bt_exit");break;
        }
    }
}
