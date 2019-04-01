package com.example.lenovo.baiduditu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.baiduditu.R;
import com.example.lenovo.baiduditu.myClass.News;

import java.util.List;

/**
 * Created by lenovo on 2017/12/14.
 */

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.LinearViewHolder> {
    //0:系统消息，1：班级消息，2：个人消息，3：年级消息，4：学校消息
    private String[] a= {"系统消息","班级消息","个人消息","年级消息","学校消息"};
    private Context mcontext;
    private List<News> mnews;
    private OnItemClickListener mlistener;
    public InformationAdapter(Context context, List<News>news, OnItemClickListener listener){
        this.mcontext = context;
        this.mnews=news;
        this.mlistener = listener;
    }
    @Override
    public InformationAdapter.LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.item_information,parent,false));
    }

    @Override
    public void onBindViewHolder(final InformationAdapter.LinearViewHolder holder, final int position) {
    //    holder.info_topic.setText(mDingdans.get(position).gettime());
    //    holder.info_time.setText("租借"+mDingdans.get(position).getgname()+"成功！消耗"+mDingdans.get(position).getprice()+"积分。");
        String str1 = "";
        switch (mnews.get(position).getType()){
            case "0":str1 = "<font color='#FF0000'>[系统消息]<br/></font>";break;
            case "1":str1 = "<font color='#AAAAAA'>[班级消息]<br/></font>";break;
            case "2":str1 = "<font color='#0000FF'>[个人消息]<br/></font>";break;
            case "3":str1 = "<font color='#AAAAAA'>[年级消息]<br/></font>";break;
            case "4":str1 = "<font color='#AAAAAA'>[学校消息]<br/></font>";break;
            default:str1="系统故障！";
        }

        String str = str1+mnews.get(position).getTitle();
        holder.info_topic.setText(Html.fromHtml(str));
        holder.info_time.setText(mnews.get(position).getPubtime());
        holder.info_autor.setText(mnews.get(position).getAuthor());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
             //   common.myDailog("长按",mcontext);
                return false;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mnews.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{

        private TextView info_topic,info_time,info_autor;
        public LinearViewHolder(View itemView) {
            super(itemView);
            info_topic = itemView.findViewById(R.id.info_topic);
            info_time =itemView.findViewById(R.id.info_time);
            info_autor =itemView.findViewById(R.id.info_autor);
        }
    }  //LinearViewHolder
    public interface OnItemClickListener{
        void onClick(int pos);
    }
}
