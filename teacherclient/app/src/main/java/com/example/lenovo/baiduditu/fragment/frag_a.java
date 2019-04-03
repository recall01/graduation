package com.example.lenovo.baiduditu.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.baiduditu.Jingweidu;
import com.example.lenovo.baiduditu.LoginActivity;
import com.example.lenovo.baiduditu.AddStudentActivity;
import com.example.lenovo.baiduditu.R;
import com.example.lenovo.baiduditu.adapter.VSetAdapter;
import com.example.lenovo.baiduditu.StudentsActivity;
import com.example.lenovo.baiduditu.model.Sign;
import com.example.lenovo.baiduditu.model.Student;
import com.example.lenovo.baiduditu.model.VSet;
import com.example.lenovo.baiduditu.myClass.HttpUtil;
import com.example.lenovo.baiduditu.myClass.common;
import com.example.lenovo.baiduditu.utils.Constants;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by lenovo on 2018/1/17.
 */

public class frag_a extends Fragment {
    Student student = new Student();
    private SwipeRefreshLayout swipeRefresh;
    TextView nameTV,emailTV;
    CircleImageView image;
    Button nav;
    LoadingDialog ld;
    View mView;
    RecyclerView vSetRV;
    List<VSet>vSets = new ArrayList<>();
    private DrawerLayout mDrawerLayout;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:loadView(mView);break;
                case 3:ld.close();Toast.makeText(getActivity(),"连接失败，请稍后再试！",Toast.LENGTH_SHORT).show();break;
                default:ld.close();common.myDailog("系统异常!"+msg.obj,getActivity());break;
            }
        }
    };

    public static frag_a newInstance(Student student){
        frag_a a =new frag_a();
        Bundle bundle = new Bundle();
        bundle.putSerializable("student",student);
        a.setArguments(bundle);
        return a;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_qiandao,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mView=view;
        if(getArguments()==null){
            return;
        }
        student = (Student) getArguments().getSerializable("student");

        if(student==null){
            return;
        }
        NavigationView navView = view.findViewById(R.id.nav_view);
        View headerLayout = navView.inflateHeaderView(R.layout.nav_header);
        nameTV =headerLayout.findViewById(R.id.nav_nickname);
        emailTV = headerLayout.findViewById(R.id.nav_email);
        mDrawerLayout = view.findViewById(R.id.drawer_layout);
        nav =view.findViewById(R.id.nav);
        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_location:
                        Intent intent = new Intent(getActivity(), StudentsActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("student",student);
                        intent.putExtras(mBundle);
                        startActivity(intent);break;
                    case R.id.nav_infor://完善信息按钮
                        intent = new Intent(getActivity(), AddStudentActivity.class);
                        mBundle = new Bundle();
                        mBundle.putSerializable("student",student);
                        intent.putExtras(mBundle);
                        startActivity(intent);break;
                    case R.id.nav_quit://切换账号按钮
                        intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();break;
                    default:mDrawerLayout.closeDrawers();break;
                }
                return true;
            }
        });
        image = headerLayout.findViewById(R.id.icon_image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                common.myDailog("作者太懒,该功能还未实现",getActivity());
            }
        });
        loadData();
        initView(student);
    }//onViewCreated
    private void loadData(){
        HttpUtil.getOkHttpRequest(Constants.QUERYVSET_URL+"?claID="+student.getClaID()+"&stuID="+student.getStuId(), new Callback() {
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


    @SuppressLint("ResourceAsColor")
    public void loadView(View view ){
        vSetRV = view.findViewById(R.id.dd_main);
        vSetRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        vSetRV.setAdapter(new VSetAdapter(getActivity(), vSets, new VSetAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos,String setName) {
                Intent mIntent= new Intent();
                Bundle mBundle = new Bundle();
                Sign sign = new Sign();
                sign.setStuId(student.getStuId());
                sign.setSetId(vSets.get(pos).getSetId());
                mBundle.putSerializable("sign",sign);
                mIntent.putExtras(mBundle);
                mIntent.setClass(getActivity(), Jingweidu.class);
                startActivityForResult(mIntent,0);
            }
        }));
        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeColors(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                vSets.clear();
                loadData();
                swipeRefresh.setRefreshing(false);
            }
        });

/*
        navView.setCheckedItem(R.id.nav_location);
*/

    }  //btn
    private void initView(Student student){
        nameTV.setText(student.getStuName());
        emailTV.setText(student.getStuMail());
    }

    private void parseJSONWithJSONObject(String jsonData) {
        Message message = new Message();
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            int status = jsonObject.getInt("status");
            if(status == 200){
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for(int i=0;i<jsonArray.length();i++){
                    VSet set = new VSet();
                    JSONObject jo = jsonArray.getJSONObject(i);
                    if(jo!=null){
                        set.setClaId(jo.getString("claID"));
                        set.setClaName(jo.getString("claName"));
                        set.setSetId(jo.getString("setID"));
                        set.setSetName(jo.getString("setName"));
                        set.setLongitude(jo.getString("longitude"));
                        set.setLatitude(jo.getString("longitude"));
                        set.setScope(jo.getString("scope"));
                        set.setStartSigTime(jo.getString("startSigTime"));
                        set.setEndSigTime(jo.getString("endSigTime"));
                        set.setCreaterName(jo.getString("createrName"));
                        vSets.add(set);
                    }
                }
            }
            message.what = 0;
            message.obj =  status;
        }catch (Exception e){
            e.printStackTrace();
            message.what = 5;
            message.obj = e.getMessage();
        }finally {
            handler.sendMessage(message);
        }
    }  //parseJSONWithJSONObject

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_BROADCAST");
        BroadcastReceiver mItemviewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                student = (Student) intent.getSerializableExtra("student");
                initView(student);
            }
        };
        broadcastManager.registerReceiver(mItemviewListClickReceiver, intentFilter);
    }

}
