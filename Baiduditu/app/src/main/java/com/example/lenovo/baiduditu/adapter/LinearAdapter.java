package com.example.lenovo.baiduditu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.baiduditu.R;
import com.example.lenovo.baiduditu.myClass.good;

import java.util.List;

/**
 * Created by lenovo on 2017/12/14.
 */

public class LinearAdapter extends RecyclerView.Adapter<LinearAdapter.LinearViewHolder> {
    private Context mcontext;
    private OnItemClickListener mListener;
    private List<good> mGoods;
    public LinearAdapter(Context context,List<good>goods,OnItemClickListener listener){
        this.mcontext = context;
        this.mGoods=goods;
        this.mListener=listener;
    }
    @Override
    public LinearAdapter.LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.item_tushu,parent,false));
    }

    @Override
    public void onBindViewHolder(final LinearAdapter.LinearViewHolder holder, final int position) {
    //    holder.timu.setText("测试一下");
    //    holder.timu.setText(tim.get(position));
        holder.timu.setText(mGoods.get(position).good_name);
        holder.shuliang.setText("当前所剩余数量："+mGoods.get(position).good_num);
        holder.shijian.setText(mGoods.get(position).getTime());
        Glide.with(mcontext).load("http://873717549-tupisn.stor.sinaapp.com/"+mGoods.get(position).tupian_url).into(holder.tupian);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(position,mGoods.get(position).good_id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGoods.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{

        private TextView timu,shijian,shuliang;
        ImageView tupian;
        public LinearViewHolder(View itemView) {
            super(itemView);
            timu = itemView.findViewById(R.id.timu);
            shijian =itemView.findViewById(R.id.shijian);
            tupian =itemView.findViewById(R.id.tupian);
            shuliang =itemView.findViewById(R.id.shuliang);
        }
    }
    public interface OnItemClickListener{
        void onClick(int pos,int good_id);
    }
}
