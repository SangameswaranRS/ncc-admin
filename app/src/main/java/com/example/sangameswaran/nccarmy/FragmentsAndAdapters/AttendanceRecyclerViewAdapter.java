package com.example.sangameswaran.nccarmy.FragmentsAndAdapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sangameswaran.nccarmy.Entities.AttendanceEntity;
import com.example.sangameswaran.nccarmy.Entities.ParadeEntity;
import com.example.sangameswaran.nccarmy.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Sangameswaran on 12-05-2017.
 */

public class AttendanceRecyclerViewAdapter extends RecyclerView.Adapter<AttendanceRecyclerViewAdapter.AttendanceRecyclerViewHolder> {
    ArrayList<AttendanceEntity> attendanceEntities=new ArrayList<>();
    String date,yoj;
    public int EMEpresent=0,EMEabsent=0;
    public int ENGpresent=0,ENGabsent=0;
    public int SIGpresent=0,SIGabsent=0;
    public int present=0,absent=0;
    AttendanceRecyclerViewAdapter(ArrayList<AttendanceEntity> attendanceEntities,String date,String yoj)
    {
        this.date=date;
        this.yoj=yoj;
        this.attendanceEntities=attendanceEntities;
    }
    @Override
    public AttendanceRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_card_view_layout,parent,false);
        AttendanceRecyclerViewHolder holder=new AttendanceRecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AttendanceRecyclerViewHolder holder, final int position) {
        AttendanceEntity entity=new AttendanceEntity();
        entity=attendanceEntities.get(position);
        holder.t1.setText("NAME : "+entity.getName());
        holder.t2.setText("PLATOON : "+entity.getPlatoon());
        holder.t3.setText("REGIMENTAL NUMBER : "+entity.getRegimental_number());
        DatabaseReference maintainEMECount=FirebaseDatabase.getInstance().getReference("PARADE/"+date+yoj+"/CountEMEPresent");
        maintainEMECount.setValue(""+EMEpresent);
        DatabaseReference maintainENGcount=FirebaseDatabase.getInstance().getReference("PARADE/"+date+yoj+"/CountENGPresent");
        maintainENGcount.setValue(""+ENGpresent);
        DatabaseReference maintainSIGcount=FirebaseDatabase.getInstance().getReference("PARADE/"+date+yoj+"/CountSIGPresent");
        maintainSIGcount.setValue(""+SIGpresent);
        DatabaseReference maintainCount=FirebaseDatabase.getInstance().getReference("PARADE/"+date+yoj+"/CountParadePresent");
        maintainCount.setValue(""+present);
        DatabaseReference maintainEMECount1=FirebaseDatabase.getInstance().getReference("PARADE/"+date+yoj+"/CountEMEAbsent");
        maintainEMECount1.setValue(""+EMEabsent);
        DatabaseReference maintainENGcount2=FirebaseDatabase.getInstance().getReference("PARADE/"+date+yoj+"/CountENGAbsent");
        maintainENGcount2.setValue(""+ENGabsent);
        DatabaseReference maintainSIGcount3=FirebaseDatabase.getInstance().getReference("PARADE/"+date+yoj+"/CountSIGAbsent");
        maintainSIGcount3.setValue(""+SIGabsent);
        DatabaseReference maintainCount4=FirebaseDatabase.getInstance().getReference("PARADE/"+date+yoj+"/CountParadeAbsent");
        maintainCount4.setValue(""+absent);


        holder.present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.llColor.setBackgroundColor(Color.parseColor("#309229"));
                holder.absent.setVisibility(View.GONE);
                holder.present.setClickable(false);
                ParadeEntity entity1=new ParadeEntity();
                AttendanceEntity entity2=new AttendanceEntity();
                entity2=attendanceEntities.get(position);
                entity1.setCadet(entity2);
                present++;
                if(entity2.getPlatoon().toLowerCase().equals("eme")) {
                    EMEpresent++;
                }
                else if(entity2.getPlatoon().toLowerCase().equals("engineers")||entity2.getPlatoon().toLowerCase().equals("eng")) {
                    ENGpresent++;
                }
                else {
                    SIGpresent++;
                }
                DatabaseReference maintainEMECount=FirebaseDatabase.getInstance().getReference("PARADE/"+date+yoj+"/CountEMEPresent");
                maintainEMECount.setValue(""+EMEpresent);
                DatabaseReference maintainENGcount=FirebaseDatabase.getInstance().getReference("PARADE/"+date+yoj+"/CountENGPresent");
                maintainENGcount.setValue(""+ENGpresent);
                DatabaseReference maintainSIGcount=FirebaseDatabase.getInstance().getReference("PARADE/"+date+yoj+"/CountSIGPresent");
                maintainSIGcount.setValue(""+SIGpresent);
                DatabaseReference maintainCount=FirebaseDatabase.getInstance().getReference("PARADE/"+date+yoj+"/CountParadePresent");
                maintainCount.setValue(""+present);
                entity1.setAttendance("Present");
                DatabaseReference myreference= FirebaseDatabase.getInstance().getReference("PARADE/"+date+yoj);
                String id=myreference.push().getKey();
                myreference.child(id).setValue(entity1);
                Log.d("AttendanceAdapter","Value Inserted into db");
            }
        });
        holder.absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.present.setVisibility(View.GONE);
                holder.absent.setClickable(false);
                ParadeEntity entity1=new ParadeEntity();
                AttendanceEntity entity2=new AttendanceEntity();
                entity2=attendanceEntities.get(position);
                absent++;
                if(entity2.getPlatoon().toLowerCase().equals("eme")) {
                    EMEabsent++;
                }
                else if(entity2.getPlatoon().toLowerCase().equals("engineers")||entity2.getPlatoon().toLowerCase().equals("eng")) {
                    ENGabsent++;
                }
                else {
                    SIGabsent++;
                }
                DatabaseReference maintainEMECount=FirebaseDatabase.getInstance().getReference("PARADE/"+date+yoj+"/CountEMEAbsent");
                maintainEMECount.setValue(""+EMEabsent);
                DatabaseReference maintainENGcount=FirebaseDatabase.getInstance().getReference("PARADE/"+date+yoj+"/CountENGAbsent");
                maintainENGcount.setValue(""+ENGabsent);
                DatabaseReference maintainSIGcount=FirebaseDatabase.getInstance().getReference("PARADE/"+date+yoj+"/CountSIGAbsent");
                maintainSIGcount.setValue(""+SIGabsent);
                DatabaseReference maintainCount=FirebaseDatabase.getInstance().getReference("PARADE/"+date+yoj+"/CountParadeAbsent");
                maintainCount.setValue(""+absent);

                entity1.setCadet(entity2);
                entity1.setAttendance("Absent");
                DatabaseReference myreference= FirebaseDatabase.getInstance().getReference("PARADE/"+date+yoj);
                String id=myreference.push().getKey();
                myreference.child(id).setValue(entity1);
                Log.d("AttendanceAdapter","Value Inserted into db");
            }
        });

    }
    @Override
    public int getItemCount() {
        return attendanceEntities.size();
    }

    public class AttendanceRecyclerViewHolder  extends RecyclerView.ViewHolder {
        TextView t1,t2,t3;
        Button present,absent;
        LinearLayout llColor;
        public AttendanceRecyclerViewHolder(View itemView) {
            super(itemView);
            llColor=(LinearLayout)itemView.findViewById(R.id.llColor);
            t1=(TextView)itemView.findViewById(R.id.tvName);
            t2=(TextView)itemView.findViewById(R.id.tvRegimentalNumber);
            t3=(TextView)itemView.findViewById(R.id.tvPlatoon);
            present=(Button)itemView.findViewById(R.id.presentbtn);
            absent=(Button)itemView.findViewById(R.id.absentbtn);
        }
    }
}
