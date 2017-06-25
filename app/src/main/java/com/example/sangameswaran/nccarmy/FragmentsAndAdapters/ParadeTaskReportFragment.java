package com.example.sangameswaran.nccarmy.FragmentsAndAdapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.opengl.EGLDisplay;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sangameswaran.nccarmy.Entities.ParadeTaskReportEntity;
import com.example.sangameswaran.nccarmy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Sangameswaran on 22-06-2017.
 */

public class ParadeTaskReportFragment extends Fragment{
    RecyclerView paradeTasks;
    FloatingActionButton fab;
    AlertDialog.Builder builder;
    RecyclerView.LayoutManager manager;
    ParadeTaskReportAdapter adapter;
    ArrayList<String> parades=new ArrayList<>();
    RelativeLayout rlLoader;
    ArrayList<ParadeTaskReportEntity> taskReportEntities=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.parade_task_report_fragment,container,false);
        paradeTasks=(RecyclerView)v.findViewById(R.id.paradeTaskReportRecyclerView);
        fab=(FloatingActionButton)v.findViewById(R.id.addFAB);
        rlLoader=(RelativeLayout)v.findViewById(R.id.rlLoader);
        builder=new AlertDialog.Builder(getActivity());
        rlLoader.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlLoader.setVisibility(View.VISIBLE);
                DatabaseReference myReference=FirebaseDatabase.getInstance().getReference("PARADE_REPORT");
                myReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                        try {
                            for(DataSnapshot d:dataSnapshot.getChildren()){
                                parades.add(d.getKey());
                            }
                            inflateDialog(parades);
                        }catch (Exception e){
                            Toast.makeText(getContext(),"Fetch error",Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        manager=new LinearLayoutManager(getActivity());
        final DatabaseReference getTasksApi= FirebaseDatabase.getInstance().getReference("TasksApi");
        getTasksApi.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    for(DataSnapshot iterator:dataSnapshot.getChildren()){
                        try {
                            rlLoader.setVisibility(View.GONE);
                            ParadeTaskReportEntity entity=iterator.getValue(ParadeTaskReportEntity.class);
                            taskReportEntities.add(entity);
                            adapter=new ParadeTaskReportAdapter(taskReportEntities);
                            paradeTasks.setAdapter(adapter);
                            paradeTasks.setLayoutManager(manager);
                        }catch (Exception e){
                            Toast.makeText(getActivity(),"Cast Error",Toast.LENGTH_LONG).show();
                        }
                    }
                    Collections.reverse(taskReportEntities);
                    adapter=new ParadeTaskReportAdapter(taskReportEntities);
                    paradeTasks.setAdapter(adapter);
                    paradeTasks.setLayoutManager(manager);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;
    }

    private void inflateDialog(ArrayList<String> parades) {
        LayoutInflater inflater1=getActivity().getLayoutInflater();
        View dialogView=inflater1.inflate(R.layout.add_parade_task_report_layout,null,false);
        final Spinner selectParade=(Spinner)dialogView.findViewById(R.id.selectParadeSpinner);
        final EditText drill=(EditText)dialogView.findViewById(R.id.etParadeTask);
        final EditText theory=(EditText)dialogView.findViewById(R.id.etTheoryTask);
        final EditText crewEff=(EditText)dialogView.findViewById(R.id.etCrewEff);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, parades);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectParade.setAdapter(dataAdapter);
        rlLoader.setVisibility(View.GONE);
        builder.setView(dialogView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference postRequest=FirebaseDatabase.getInstance().getReference("TasksApi");
                String id=postRequest.push().getKey();
                ParadeTaskReportEntity e=new ParadeTaskReportEntity();
                if(drill.getText().toString().equals("")||theory.getText().toString().equals("")||crewEff.getText().toString().equals("")){
                 if(drill.getText().toString().equals(""))
                     drill.setError("Required");
                    if (theory.getText().toString().equals(""))
                        theory.setError("Required");
                    if(crewEff.getText().toString().equals(""))
                        crewEff.setError("Required");
                }
                else {
                    e.setParade((String) selectParade.getSelectedItem());
                    e.setCrewEff(crewEff.getText().toString());
                    e.setDrillTask(drill.getText().toString());
                    e.setTheoryTask(theory.getText().toString());
                    postRequest.child(id).setValue(e);
                    Toast.makeText(getActivity(), "Added successfully", Toast.LENGTH_LONG).show();
                    ParadeTaskReportFragment fragment = new ParadeTaskReportFragment();
                    getFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setCancelable(false).show();
    }
}
