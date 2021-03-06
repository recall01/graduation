package com.example.lenovo.baiduditu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.baiduditu.R;
import com.example.lenovo.baiduditu.model.VSign;
import com.example.lenovo.baiduditu.myClass.Utils;

import java.util.List;

/**
 * Created by lenovo on 2017/12/14.
 */

public class DingdanAdapter extends RecyclerView.Adapter<DingdanAdapter.LinearViewHolder> {
    private Context mcontext;
    private List<VSign> signLists;
    private OnItemClickListener mlistener;
    public DingdanAdapter(Context context, List<VSign>signLists, OnItemClickListener listener){
        this.mcontext = context;
        this.signLists = signLists;
        this.mlistener = listener;
    }
    @Override
    public DingdanAdapter.LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.item_dingdan,parent,false));
    }

    @Override
    public void onBindViewHolder(final DingdanAdapter.LinearViewHolder holder, final int position) {
        holder.layout_content.getLayoutParams().width = Utils.getScreenWidth(mcontext);
        holder.order_time.setText(signLists.get(position).getSigTime());
        holder.order_xinxi.setText("["+signLists.get(position).getSetName()+"]签到成功！");
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.onClick(position);
                notifyItemRemoved(position);
                signLists.remove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return signLists.size();
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
