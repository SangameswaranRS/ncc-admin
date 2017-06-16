package com.example.sangameswaran.nccarmy.FragmentsAndAdapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sangameswaran.nccarmy.Entities.ParadeEntity;
import com.example.sangameswaran.nccarmy.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Sangameswaran on 13-06-2017.
 */

public class ViewFullAttendanceReportAdapter extends RecyclerView.Adapter<ViewFullAttendanceReportAdapter.ViewFullAttendancereportRecyclerViewHolder> {
    ArrayList<ParadeEntity> ParadeEntities=new ArrayList<>();

    public ViewFullAttendanceReportAdapter(ArrayList<ParadeEntity> ParadeEntities){
        this.ParadeEntities=ParadeEntities;
    }

    @Override
    public ViewFullAttendancereportRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_full_report_attendance_card_layout,parent,false);
        ViewFullAttendancereportRecyclerViewHolder holder=new ViewFullAttendancereportRecyclerViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewFullAttendancereportRecyclerViewHolder holder, int position) {
        ParadeEntity bindEntity=ParadeEntities.get(position);
        holder.t1.setText("NAME : "+bindEntity.getCadet().getName());
        holder.t1.setTextColor(Color.BLACK);
        holder.t2.setText("REGIMENTAL NUMBER : "+bindEntity.getCadet().getRegimental_number());
        holder.t3.setText("PLATOON : "+bindEntity.getCadet().getPlatoon());
        if(bindEntity.getAttendance().equals("present")||bindEntity.getAttendance().equals("Present")){
            holder.cardColor.setBackgroundColor(Color.parseColor("#309229"));
        }
    }

    @Override
    public int getItemCount() {
        return ParadeEntities.size();
    }

    public class ViewFullAttendancereportRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3;
        LinearLayout cardColor;
        public ViewFullAttendancereportRecyclerViewHolder(View itemView) {
            super(itemView);
            t1=(TextView)itemView.findViewById(R.id.tex1);
            t2=(TextView)itemView.findViewById(R.id.tex2);
            t3=(TextView)itemView.findViewById(R.id.tex3);
            cardColor=(LinearLayout)itemView.findViewById(R.id.cardColor);
        }
    }
}
