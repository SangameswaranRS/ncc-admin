package com.example.sangameswaran.nccarmy.FragmentsAndAdapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.sangameswaran.nccarmy.Entities.AttendanceReportEntity;
import com.example.sangameswaran.nccarmy.Entities.ViewParadeOverallReportEntity;
import com.example.sangameswaran.nccarmy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Sangameswaran on 15-05-2017.
 */

public class ViewParadeOverallReportFragment extends Fragment {

    RecyclerView viewParadeOverallReportRecyclerView;
    RecyclerView.LayoutManager manager;
    ViewParadeOverallReportAdapter adapter;
    ArrayList<ViewParadeOverallReportEntity> allReports;
    RelativeLayout loader4;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.view_parade_overall_report,container,false);
        allReports=new ArrayList<>();
        viewParadeOverallReportRecyclerView=(RecyclerView) v.findViewById(R.id.viewparadeoverallreportrecyclerview);
        manager=new LinearLayoutManager(getActivity());
        getActivity().setTitle("Parade Reports");
        loader4=(RelativeLayout)v.findViewById(R.id.loader4);
        final DatabaseReference mdatabase= FirebaseDatabase.getInstance().getReference("PARADE_REPORT");
        mdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren())
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        ViewParadeOverallReportEntity entity=new ViewParadeOverallReportEntity();
                        AttendanceReportEntity entity1=new AttendanceReportEntity();
                        entity1=ds.getValue(AttendanceReportEntity.class);
                        entity.setReport(entity1);
                        entity.setDate(ds.getKey());
                        allReports.add(entity);
                        adapter=new ViewParadeOverallReportAdapter(allReports,getActivity());
                        viewParadeOverallReportRecyclerView.setAdapter(adapter);
                        viewParadeOverallReportRecyclerView.setLayoutManager(manager);
                        adapter.notifyDataSetChanged();
                    }
                    loader4.setVisibility(View.GONE);
                Collections.reverse(allReports);
                adapter=new ViewParadeOverallReportAdapter(allReports,getActivity());
                viewParadeOverallReportRecyclerView.setAdapter(adapter);
                viewParadeOverallReportRecyclerView.setLayoutManager(manager);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }
}
