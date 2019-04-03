package com.example.lenovo.baiduditu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lenovo.baiduditu.R;
import com.example.lenovo.baiduditu.AddStudent;
import com.example.lenovo.baiduditu.Zhujie_xinxiActivity;
import com.example.lenovo.baiduditu.adapter.LinearAdapter;
import com.example.lenovo.baiduditu.myClass.HttpUtil;
import com.example.lenovo.baiduditu.myClass.good;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by lenovo on 2018/1/17.
 */

public class frag_two extends Fragment {
    RecyclerView mRvMain;
    private List<good> goods;
    LoadingDialog ld;
    String mUrl,user_id,name;
    View myView;
    private SwipeRefreshLayout swipeRefresh;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:ld.close();changeView(myView);
                    break;
                case 2:ld.close();
                    Toast.makeText(getActivity(),msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;
                case 3:ld.close();Toast.makeText(getActivity(),"网络状况不好，请稍后再试！",Toast.LENGTH_SHORT).show();
                    break;
                default:break;
            }
        }
    };
    public static frag_two newInstance(String name,String id){
        frag_two a =new frag_two();
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        bundle.putString("user_id",id);
        a.setArguments(bundle);
        return a;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_two,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myView = view;
        if(getArguments()!=null){
            name = getArguments().getString("name");
            user_id = getArguments().getString("user_id");
        }
        LinearLayout layout = view.findViewById(R.id.shousuo);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddStudent.class);
                startActivity(intent);
            }
        });

        goods = new ArrayList<good>();
        mUrl ="http://1.873717549.applinzi.com/Android_wuping.php";
        swipeRefresh =view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorhuise);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                goods.clear();
                refreshGood(mUrl);
            }
        });
        HttpUtil.getOkHttpRequest(mUrl, new Callback() {
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
        ld = new LoadingDialog(getActivity());
        ld.setLoadingText("加载中...").show();
    }//onViewCreated
    private void refreshGood(String mUrl){
        HttpUtil.getOkHttpRequest(mUrl, new Callback() {
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
        swipeRefresh.setRefreshing(false);
    }  //refreshGood
    public void changeView(View view){
        mRvMain =view.findViewById(R.id.rv_main);
        mRvMain.setLayoutManager(new LinearLayoutManager(getActivity()));
        //       mRvMain.addItemDecoration(new MyDecoration());
        mRvMain.setAdapter(new LinearAdapter(getActivity(), goods, new LinearAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos,int good_id) {

                Intent mIntent= new Intent();
                mIntent.putExtra("good_id",good_id);
                mIntent.putExtra("user_id",user_id);
                mIntent.setClass(getActivity(), Zhujie_xinxiActivity.class);
                startActivityForResult(mIntent,0);
            }
        }));
    }//changeView
    private void parseJSONWithJSONObject(String jsonData) {
        //good_name.clear();
        try{
            JSONArray allGoods = new JSONArray(jsonData);
            for (int i=0;i<allGoods.length();i++){
                JSONObject jsgood = allGoods.getJSONObject(i);
                int id =Integer.parseInt(jsgood.getString("good_id"));
                String name =jsgood.getString("good_name");
                String url =jsgood.getString("tupian_url");
                String num =jsgood.getString("good_num");
                String time =jsgood.getString("up_time");
                good oneGood =new good(id,name,url,num);
                oneGood.setTime(time);
                goods.add(oneGood);
            }
            Message message = new Message();
            message.what = 1;
            message.obj =goods.size();
            handler.sendMessage(message);
        }catch (Exception e){
            Message message = new Message();
            message.what = 2;
            message.obj =e.getMessage();
            handler.sendMessage(message);
        }
    }//parseJSONWithJSONObject



}
