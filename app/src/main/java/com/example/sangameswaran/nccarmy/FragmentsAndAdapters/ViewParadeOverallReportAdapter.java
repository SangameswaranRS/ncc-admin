package com.example.sangameswaran.nccarmy.FragmentsAndAdapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sangameswaran.nccarmy.Activities.ViewFullReportAttendanceActivity;
import com.example.sangameswaran.nccarmy.Entities.ViewParadeOverallReportEntity;
import com.example.sangameswaran.nccarmy.R;

import java.util.ArrayList;

/**
 * Created by Sangameswaran on 18-05-2017.
 */

public class ViewParadeOverallReportAdapter extends RecyclerView.Adapter<ViewParadeOverallReportAdapter.ViewParadeOverallReportViewHolder> {
    ArrayList<ViewParadeOverallReportEntity> paradeList=new ArrayList<>();
    Context context;
    public ViewParadeOverallReportAdapter(ArrayList<ViewParadeOverallReportEntity> entities, Context context)
    {
        this.paradeList=entities;
        this.context=context;
    }
    @Override
    public ViewParadeOverallReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_parade_overall_report_card_layout,parent,false);
        ViewParadeOverallReportViewHolder holder=new ViewParadeOverallReportViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewParadeOverallReportViewHolder holder, int position) {
        final ViewParadeOverallReportEntity entity=paradeList.get(position);
        String colorDecider=entity.getReport().getPercentage_of_present();
        colorDecider=colorDecider.replace("%","");
        try{
            Double a=Double.parseDouble(colorDecider);
            if(a<=40.0){
             holder.color.setBackgroundColor(Color.RED);
                holder.t4.setTextColor(Color.RED);
                holder.t1.setTextColor(Color.RED);
            }
            else if(a>=41.0&&a<=70.0){
                holder.color.setBackgroundColor(Color.parseColor("#dcdc39"));
                holder.t4.setTextColor(Color.parseColor("#dcdc39"));
                holder.t1.setTextColor(Color.parseColor("#dcdc39"));
            }
            else {
                holder.color.setBackgroundColor(Color.parseColor("#309229"));
                holder.t4.setTextColor(Color.parseColor("#309229"));
                holder.t1.setTextColor(Color.parseColor("#309229"));
            }
        }catch (Exception e){

        }
        holder.t1.setText("BATCH/DATE : "+entity.getDate());
        holder.t2.setText("OVERALL PRESENT COUNT : "+entity.getReport().getOverall_present_count());
        holder.t3.setText("OVERALL ABSENT COUNT : "+entity.getReport().getOverall_absent_count());
        holder.t4.setText("OVERALL ATTENDANCE : "+entity.getReport().getPercentage_of_present());
        holder.t5.setText("BATCH :" +entity.getReport().getBatch());
        holder.t6.setText("EME PRESENT COUNT :" + entity.getReport().getEMEpresentCount());
        holder.t7.setText("EME ABSENT COUNT : "+entity.getReport().getEMEAbsentCount());
        holder.t8.setText("ENGINEERS PRESENT COUNT : "+entity.getReport().getENGpresentCount());
        holder.t9.setText("ENGINEERS ABSENT COUNT : "+entity.getReport().getENGabsentCount());
        holder.t10.setText("SIGNALS PRESENT COUNT : "+entity.getReport().getSIGpresentCount());
        holder.t11.setText("SIGNALS ABSENT COUNT : "+entity.getReport().getSIGabsentCount());

        holder.CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Cliked",""+holder.t1.getText().toString());
                Intent intent=new Intent(context, ViewFullReportAttendanceActivity.class);
                intent.putExtra("hit_directory",entity.getDate());
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return paradeList.size();
    }
    public class ViewParadeOverallReportViewHolder extends RecyclerView.ViewHolder
    {
        View CardView;
        TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11;
        LinearLayout color;

        public ViewParadeOverallReportViewHolder(View itemView) {
            super(itemView);
            CardView=itemView;
            color=(LinearLayout)itemView.findViewById(R.id.colorLL);
            t1=(TextView)itemView.findViewById(R.id.te1);
            t2=(TextView)itemView.findViewById(R.id.te2);
            t3=(TextView)itemView.findViewById(R.id.te3);
            t4=(TextView)itemView.findViewById(R.id.te4);
            t5=(TextView)itemView.findViewById(R.id.te5);
            t6=(TextView)itemView.findViewById(R.id.te6);
            t7=(TextView)itemView.findViewById(R.id.te7);
            t8=(TextView)itemView.findViewById(R.id.te8);
            t9=(TextView)itemView.findViewById(R.id.te9);
            t10=(TextView)itemView.findViewById(R.id.te10);
            t11=(TextView)itemView.findViewById(R.id.te11);
        }
    }
}
