package com.example.lenovo.baiduditu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.baiduditu.R;
import com.example.lenovo.baiduditu.model.VRecord;
import com.example.lenovo.baiduditu.utils.Utils;

import java.util.List;

/**
 * Created by lenovo on 2017/12/14.
 */

public class StuRecordsAdapter extends RecyclerView.Adapter<StuRecordsAdapter.LinearViewHolder> {
    private Context mcontext;
    private List<VRecord> vRecords;
    public StuRecordsAdapter(Context context, List<VRecord>vRecords){
        this.mcontext = context;
        this.vRecords = vRecords;
    }
    @Override
    public StuRecordsAdapter.LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.item_vrecords_list,parent,false));
    }

    @Override
    public void onBindViewHolder(final StuRecordsAdapter.LinearViewHolder holder, final int position) {
        holder.layout_content.getLayoutParams().width = Utils.getScreenWidth(mcontext);
        holder.title.setText("["+vRecords.get(position).getSetName()+"]"+" 签到成功!");
        holder.time.setText(vRecords.get(position).getSigTime());
    }

    @Override
    public int getItemCount() {
        return vRecords.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{

        private TextView title,time;
        public ViewGroup layout_content;
        public LinearViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            time =itemView.findViewById(R.id.tv_time);
            layout_content = itemView.findViewById(R.id.layout_content);
        }
    }  //LinearViewHolder
}
