package com.example.lenovo.baiduditu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.baiduditu.R;
import com.example.lenovo.baiduditu.myClass.News;

/**
 * Created by lenovo on 2018/1/23.
 */

public class frag_one_newInfo extends Fragment {
    TextView title,author,time,content;
    String new_title,new_content,new_pubtime,new_endtime,new_author;
    public static frag_one_newInfo newInstance(News one){
        frag_one_newInfo a =new frag_one_newInfo();
        Bundle bundle = new Bundle();
        bundle.putString("new_title",one.getTitle());
        bundle.putString("new_content",one.getContent());
        bundle.putString("new_pubtime",one.getPubtime());
        bundle.putString("new_endtime",one.getEndtime());
        bundle.putString("new_author",one.getAuthor());
        a.setArguments(bundle);
        return a;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_one_newinfo,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments()!=null){
            //new_title,new_content,new_pubtime,new_endtime,new_author
            new_title = getArguments().getString("new_title");
            new_content = getArguments().getString("new_content");
            new_pubtime = getArguments().getString("new_pubtime");
            new_endtime = getArguments().getString("new_endtime");
            new_author = getArguments().getString("new_author");
        }
        btn(view);
    }

    private void btn(View view) {
        //title,author,time,content
        title = view.findViewById(R.id.new_title);
        title.setText(new_title);
        author = view.findViewById(R.id.new_author);
        author.setText(new_author);
        time = view.findViewById(R.id.new_time);
        time.setText("截止日期："+new_endtime);
        content = view.findViewById(R.id.new_content);
        content.setText(new_content);
    }
}
