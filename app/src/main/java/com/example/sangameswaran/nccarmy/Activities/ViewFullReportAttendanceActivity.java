package com.example.sangameswaran.nccarmy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sangameswaran.nccarmy.Entities.ParadeEntity;
import com.example.sangameswaran.nccarmy.FragmentsAndAdapters.ViewFullAttendanceReportAdapter;
import com.example.sangameswaran.nccarmy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Sangameswaran on 13-06-2017.
 */

public class ViewFullReportAttendanceActivity extends AppCompatActivity {
    String HIT_DIR;
    ArrayList<ParadeEntity> entities=new ArrayList<>();
    RecyclerView CompleteAttendanceRecyclerView;
    RecyclerView.LayoutManager manager;
    ViewFullAttendanceReportAdapter adapter;
    RelativeLayout loader5;
    TextView batchDate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_full_report_attendance_activity);
        batchDate=(TextView)findViewById(R.id.tvBatchDate);
        CompleteAttendanceRecyclerView=(RecyclerView)findViewById(R.id.viewFullReportRv);
        loader5=(RelativeLayout)findViewById(R.id.loader5);
        getValuesViaIntent();
        manager=new LinearLayoutManager(this);
        DatabaseReference AttendApi= FirebaseDatabase.getInstance().getReference("PARADE/"+HIT_DIR);
        batchDate.setText(HIT_DIR);
        AttendApi.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        try{
                            ParadeEntity e=ds.getValue(ParadeEntity.class);
                            entities.add(e);
                            loader5.setVisibility(View.GONE);
                            adapter=new ViewFullAttendanceReportAdapter(entities);
                            CompleteAttendanceRecyclerView.setAdapter(adapter);
                            CompleteAttendanceRecyclerView.setLayoutManager(manager);
                            adapter.notifyDataSetChanged();
                        }catch (Exception e){

                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Connectivity Error",Toast.LENGTH_LONG).show();
            }
        });
        adapter=new ViewFullAttendanceReportAdapter(entities);
        CompleteAttendanceRecyclerView.setAdapter(adapter);
        CompleteAttendanceRecyclerView.setLayoutManager(manager);
        adapter.notifyDataSetChanged();
    }

    private void getValuesViaIntent() {
        Intent intent=getIntent();
        HIT_DIR=intent.getStringExtra("hit_directory");
    }

    public void backPressed(View view) {
        super.onBackPressed();
    }
}
