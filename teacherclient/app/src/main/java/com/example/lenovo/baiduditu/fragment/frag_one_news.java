package com.example.lenovo.baiduditu.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.baiduditu.R;
import com.example.lenovo.baiduditu.adapter.InformationAdapter;
import com.example.lenovo.baiduditu.myClass.HttpUtil;
import com.example.lenovo.baiduditu.myClass.News;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lenovo on 2018/1/23.
 */

public class frag_one_news extends Fragment {
    String name,user_id,mUrl;
    private List<News> news;
    LoadingDialog ld;
    View mView;
    RecyclerView mRvMain;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:break;
                case 1:ld.close();changeView(mView);break;
            }
        }
    };

    public static frag_one_news newInstance(String name,String id){
        frag_one_news a =new frag_one_news();
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        bundle.putString("user_id",id);
        a.setArguments(bundle);
        return a;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_one_news,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView=view;
        if(getArguments()!=null){
            name = getArguments().getString("name");
            user_id = getArguments().getString("user_id");
        }
        news = new ArrayList<News>();
        mUrl ="http://1.873717549.applinzi.com/Android_news.php";
        RequestBody requestBody =new FormBody.Builder().add("user_id", user_id).build();
        HttpUtil.postEnqueueRequest(requestBody,mUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                parseJSONWithJSONObject(responseData,false);
            }
        });
        ld = new LoadingDialog(getActivity());
        ld.setLoadingText("加载中...").show();
    }
    private void parseJSONWithJSONObject(String jsonData,boolean isReturn) {
        try{
            if(isReturn){
                JSONObject jsonObject = new JSONObject(jsonData);
                String status = jsonObject.getString("status");
                Message msg = new Message();
                msg.what = 2;
                msg.obj = status;
                handler.sendMessage(msg);
            }else {
                JSONArray allGoods = new JSONArray(jsonData);
                for (int i=0;i<allGoods.length();i++){
                    JSONObject jsgood = allGoods.getJSONObject(i);
                    String id =jsgood.getString("new_id");
                    String type =jsgood.getString("new_type");
                    String title =jsgood.getString("new_title");
                    String content =jsgood.getString("new_content");
                    String pubtime =jsgood.getString("new_pubtime");
                    String endtime =jsgood.getString("new_endtime");
                    String author =jsgood.getString("new_author");
                    News oneNew =new News(id,type,title,content,pubtime,endtime,author);
                    news.add(oneNew);
                }
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }catch (Exception e){
            e.printStackTrace();
            Message msg = new Message();
            msg.what = 2;
            msg.obj = e.getMessage();
            handler.sendMessage(msg);
        }

    } //parseJSONWithJSONObject
    public void changeView(View view){
        mRvMain =view.findViewById(R.id.dd_main);
        mRvMain.setLayoutManager(new LinearLayoutManager(getActivity()));
        //       mRvMain.addItemDecoration(new MyDecoration());
        mRvMain.setAdapter(new InformationAdapter(getActivity(), news, new InformationAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                News onenew = new News();
                onenew.setId(news.get(position).getId());
                onenew.setType(news.get(position).getType());
                onenew.setTitle(news.get(position).getTitle());
                onenew.setContent(news.get(position).getContent());
                onenew.setPubtime(news.get(position).getPubtime());
                onenew.setEndtime(news.get(position).getEndtime());
                onenew.setAuthor(news.get(position).getAuthor());
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                //transaction.replace(R.id.fragment_news,frag_one_newInfo.newInstance(onenew)).commitAllowingStateLoss();
                //common.myDailog(onenew.getId() + onenew.getType() + onenew.getTitle() , getActivity());
                //        String mUrl ="http://1.873717549.applinzi.com/Android_guihuan.php";
                //        RequestHTTP(mUrl,oneDingdan);
            }
        }));
    }//changeView()





}
