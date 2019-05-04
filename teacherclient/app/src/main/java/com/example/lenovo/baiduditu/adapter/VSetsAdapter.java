package com.example.lenovo.baiduditu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.baiduditu.R;
import com.example.lenovo.baiduditu.model.Student;
import com.example.lenovo.baiduditu.model.VSet;
import com.example.lenovo.baiduditu.myClass.Utils;
import com.example.lenovo.baiduditu.utils.TimeUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2017/12/14.
 */

public class VSetsAdapter extends RecyclerView.Adapter<VSetsAdapter.LinearViewHolder> {
    private Context mcontext;
    private List<VSet> vSets;
    private OnItemClickListener mlistener;
    public VSetsAdapter(Context context, List<VSet>vSets, OnItemClickListener listener){
        this.mcontext = context;
        this.vSets = vSets;
        this.mlistener = listener;
    }
    @Override
    public VSetsAdapter.LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.item_vsets_list,parent,false));
    }

    @Override
    public void onBindViewHolder(final VSetsAdapter.LinearViewHolder holder, final int position) {
        holder.layout_content.getLayoutParams().width = Utils.getScreenWidth(mcontext);
        holder.name.setText(vSets.get(position).getSetName());
        try {
            Date startTime = TimeUtils.getTimeByString(vSets.get(position).getStartSigTime());
            Date endTime = TimeUtils.getTimeByString(vSets.get(position).getEndSigTime());
            Date nowTime = TimeUtils.getTimeByString(TimeUtils.getTime());
            if(nowTime.getTime()<startTime.getTime()){
                holder.status.setText("还未开始");
            }else if(nowTime.getTime()>=startTime.getTime()&&nowTime.getTime()<=endTime.getTime()){
                holder.status.setText("进行中");
            }else {
                holder.status.setText("已经结束");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemRemoved(position);
                vSets.remove(position);
            }
        });
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.onClick(position,vSets.get(position).getSetId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return vSets.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{

        private TextView name,status,btn_delete;
        public ViewGroup layout_content;
        public LinearViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.vset_name);
            status =itemView.findViewById(R.id.vset_status);
            layout_content = itemView.findViewById(R.id.layout_content);
            btn_delete = itemView.findViewById(R.id.dd_delete);
        }
    }  //LinearViewHolder
    public interface OnItemClickListener{
        void onClick(int pos,String setId);
    }
}
