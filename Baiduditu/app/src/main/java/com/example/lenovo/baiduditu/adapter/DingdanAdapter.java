package com.example.lenovo.baiduditu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.baiduditu.R;
import com.example.lenovo.baiduditu.myClass.Utils;
import com.example.lenovo.baiduditu.myClass.dingdan;

import java.util.List;

/**
 * Created by lenovo on 2017/12/14.
 */

public class DingdanAdapter extends RecyclerView.Adapter<DingdanAdapter.LinearViewHolder> {
    private Context mcontext;
    private List<dingdan> mDingdans;
    private OnItemClickListener mlistener;
    public DingdanAdapter(Context context, List<dingdan>dingdans, OnItemClickListener listener){
        this.mcontext = context;
        this.mDingdans=dingdans;
        this.mlistener = listener;
    }
    @Override
    public DingdanAdapter.LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.item_dingdan,parent,false));
    }

    @Override
    public void onBindViewHolder(final DingdanAdapter.LinearViewHolder holder, final int position) {
        holder.layout_content.getLayoutParams().width = Utils.getScreenWidth(mcontext);
        holder.order_time.setText(mDingdans.get(position).gettime());
        holder.order_xinxi.setText("租借"+mDingdans.get(position).getgname()+"成功！消耗"+mDingdans.get(position).getprice()+"积分。");
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.onClick(position);
                notifyItemRemoved(position);
                mDingdans.remove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDingdans.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{

        private TextView order_time,order_xinxi,btn_delete;
        public ViewGroup layout_content;
        public LinearViewHolder(View itemView) {
            super(itemView);
            order_time = itemView.findViewById(R.id.order_time);
            order_xinxi =itemView.findViewById(R.id.order_xinxi);
            layout_content = itemView.findViewById(R.id.layout_content);
            btn_delete = itemView.findViewById(R.id.dd_delete);
        }
    }  //LinearViewHolder
    public interface OnItemClickListener{
        void onClick(int pos);
    }
}
