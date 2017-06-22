package com.example.sangameswaran.nccarmy.FragmentsAndAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sangameswaran.nccarmy.Entities.ParadeTaskReportEntity;
import com.example.sangameswaran.nccarmy.R;

import java.util.ArrayList;

/**
 * Created by Sangameswaran on 22-06-2017.
 */

public class ParadeTaskReportAdapter extends RecyclerView.Adapter<ParadeTaskReportAdapter.ViewHolder> {
    ArrayList<ParadeTaskReportEntity> tasks=new ArrayList<>();
    public ParadeTaskReportAdapter(ArrayList<ParadeTaskReportEntity> entities) {
        this.tasks=entities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.parade_task_report_card_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.t1.setText("PARADE : "+tasks.get(position).getParade());
        holder.t2.setText("DRILL : "+tasks.get(position).getDrillTask());
        holder.t3.setText("THEORY : "+tasks.get(position).getTheoryTask());
        holder.t4.setText("CREW EFF : "+tasks.get(position).getCrewEff());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView t1,t2,t3,t4;
        public ViewHolder(View itemView) {
            super(itemView);
            t1=(TextView)itemView.findViewById(R.id.text1);
            t2=(TextView)itemView.findViewById(R.id.text2);
            t3=(TextView)itemView.findViewById(R.id.text3);
            t4=(TextView)itemView.findViewById(R.id.text4);
        }
    }
}
