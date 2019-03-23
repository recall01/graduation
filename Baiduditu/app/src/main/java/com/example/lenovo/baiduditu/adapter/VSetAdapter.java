package com.example.lenovo.baiduditu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.baiduditu.R;
import com.example.lenovo.baiduditu.model.VSet;

import java.util.List;

/**
 * Created by lenovo on 2017/12/14.
 */

public class VSetAdapter extends RecyclerView.Adapter<VSetAdapter.VSetViewHolder> {
    private Context mcontext;
    private OnItemClickListener mListener;
    private List<VSet> vSets;
    public VSetAdapter(Context context, List<VSet>vSets, OnItemClickListener listener){
        this.mcontext = context;
        this.vSets=vSets;
        this.mListener=listener;
    }

    @Override
    public VSetAdapter.VSetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VSetViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.item_vset,parent,false));
    }

    @Override
    public void onBindViewHolder(final VSetAdapter.VSetViewHolder holder, final int position) {
        holder.setNameTV.setText("["+vSets.get(position).getSetName()+"]");
        holder.createrNameTV.setText("发布者:"+vSets.get(position).getCreaterName());
        String sigTime = "签到时间:"+vSets.get(position).getStartSigTime()+ " - "+vSets.get(position).getEndSigTime();
        holder.sigTimeTV.setText(sigTime);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(position,vSets.get(position).getSetName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return vSets.size();
    }

    class VSetViewHolder extends RecyclerView.ViewHolder{

        private TextView setNameTV,sigTimeTV,createrNameTV;
        public VSetViewHolder(View itemView) {
            super(itemView);
            setNameTV = itemView.findViewById(R.id.setName);
            createrNameTV = itemView.findViewById(R.id.createrName);
            sigTimeTV =itemView.findViewById(R.id.sigTime);
        }
    }
    public interface OnItemClickListener{
        void onClick(int pos, String setName);
    }
}
