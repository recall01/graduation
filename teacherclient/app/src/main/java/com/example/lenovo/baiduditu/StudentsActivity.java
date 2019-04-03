package com.example.lenovo.baiduditu;

import android.annotation.SuppressLint;
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
import android.widget.Toast;
import com.example.lenovo.baiduditu.adapter.StudentsAdapter;
import com.example.lenovo.baiduditu.model.Student;
import com.example.lenovo.baiduditu.model.VO.TeacherVO;
import com.example.lenovo.baiduditu.myClass.HttpUtil;
import com.example.lenovo.baiduditu.myClass.common;
import com.example.lenovo.baiduditu.utils.Constants;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class StudentsActivity extends AppCompatActivity {
    private TeacherVO teacher = new TeacherVO();
    private Button backBT,addBT;
    RecyclerView mRvMain;
    private SwipeRefreshLayout swipeRefresh;
    LoadingDialog ld;
    private List<Student> students = new ArrayList<>();
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:ld.close();changeView();
                    break;
                case 2:ld.close();
                    Toast.makeText(StudentsActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(StudentsActivity.this,"系统异常",Toast.LENGTH_SHORT).show();
                    break;
                default:break;
            }
        }
    };
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        common.setHeadBackground(getWindow());

        teacher = (TeacherVO) getIntent().getSerializableExtra("teacher");
        if(teacher == null){
            return;
        }
        backBT = findViewById(R.id.bt_back);
        backBT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addBT = findViewById(R.id.bt_add);
        addBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(StudentsActivity.this,AddStudentActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("teacher",teacher);
                mainIntent.putExtras(mBundle);
                startActivity(mainIntent);
            }
        });
        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeColors(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                students.clear();
                initView();
                swipeRefresh.setRefreshing(false);
            }
        });
        if(teacher.getAClass()!=null){
            initView();
            ld = new LoadingDialog(StudentsActivity.this);
            ld.setLoadingText("加载中...").show();
        }

    }
    //请求查看签到记录
    private void initView(){
        HttpUtil.getOkHttpRequest(Constants.QUERYSTUDENTS_URL+"?claID="+teacher.getAClass().getClaId(), new Callback() {
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
    }


    public void changeView(){
        mRvMain =findViewById(R.id.dd_main);
        mRvMain.setLayoutManager(new LinearLayoutManager(StudentsActivity.this));
        mRvMain.setAdapter(new StudentsAdapter(StudentsActivity.this, students, new StudentsAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                System.out.println("onClick执行啦");
/*                dingdan oneDingdan =new dingdan();
                oneDingdan.setid(signLists.get(position).getid());
                oneDingdan.setgname(signLists.get(position).getgname());
                oneDingdan.settime(signLists.get(position).gettime());
                oneDingdan.setprice(signLists.get(position).getprice());
                String mUrl ="http://1.873717549.applinzi.com/Android_guihuan.php";
                RequestHTTP(mUrl,oneDingdan);*/
            }
        }));
    }//changeView()
    private void parseJSONWithJSONObject(String jsonData) {
        Message message = new Message();
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            int status = jsonObject.getInt("status");
            if(status == 200){
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for(int i=0;i<jsonArray.length();i++){
                    Student student = new Student();
                    JSONObject jo = jsonArray.getJSONObject(i);
                    if(jo!=null){
                        student.setStuId(jo.getString("stuId"));
                        student.setStuName(jo.getString("stuName"));
                        student.setStuSex(jo.getInt("stuSex"));
                        student.setStuNumber(jo.getString("stuNumber"));
                        student.setStuPassword(jo.getString("stuPassword"));
                        student.setStuPhone(jo.getString("stuPhone"));
                        student.setStuMail(jo.getString("stuMail"));
                        student.setClaID(jo.getString("claID"));
                        student.setRegisterTime(jo.getString("registerTime"));
                        student.setPermissions(jo.getString("permissions"));
                        student.setClassName(jo.getString("claName"));
                        students.add(student);
                    }
                }
            }
            message.what = 1;
            message.obj =  status;
        }catch (Exception e){
            e.printStackTrace();
            message.what = 3;
            message.obj = e.getMessage();
        }finally {
            handler.sendMessage(message);
        }

    } //parseJSONWithJSONObject

}
