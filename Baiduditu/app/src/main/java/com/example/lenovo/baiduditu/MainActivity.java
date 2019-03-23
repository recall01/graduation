package com.example.lenovo.baiduditu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
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
import com.example.lenovo.baiduditu.myClass.activityCollector;
import com.example.lenovo.baiduditu.myClass.common;
import com.example.lenovo.baiduditu.view.ChangeIcon;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements  ViewPager.OnPageChangeListener {
    Button button_determine,button_cancel;
    AlertDialog dlg;
    Student student = new Student();
    private List<ChangeIcon> lTabIndicators = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_xuanzejiemian);
        activityCollector.addActivity(this);
        common.setHeadBackground(getWindow());

        student = (Student) getIntent().getSerializableExtra("student");
        if(student!=null){
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragment_title,frag_a.newInstance(student)).commitAllowingStateLoss();
        }

    }  //onCreat
    //0为跳转签到界面
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        MyMessage message = (MyMessage) data.getSerializableExtra("message");
        if (message.what == 200){
            common.myToast(MainActivity.this,"签到成功!");
        }else {
            common.myDailog(""+message.obj,MainActivity.this);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_title,frag_a.newInstance(student)).commitAllowingStateLoss();
    }//onActivityResult

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (event.getKeyCode()== KeyEvent.KEYCODE_BACK){
            dlg = new AlertDialog.Builder(this).create();
            dlg.show();
            final Window window = dlg.getWindow();
            window.setGravity(Gravity.CENTER);
            window.setWindowAnimations(R.style.myStyle);
            window.setContentView(R.layout.dialog_layout);
            button_determine = window.findViewById(R.id.btn_determine);
            button_cancel = window.findViewById(R.id.btn_cancel);
            initEvent();
        }
        return super.onKeyDown(keyCode,event);
    }

    private void initEvent() {
        button_determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();                //对话框移动到底部消失
                activityCollector.finishAll();//关闭当前应用
            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"真是个明智的选择！",Toast.LENGTH_SHORT).show();
                dlg.dismiss();                //对话框移动到底部消失
            }
        });
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
}
