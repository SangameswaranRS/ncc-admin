package com.example.sangameswaran.nccarmy.FragmentsAndAdapters;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.sangameswaran.nccarmy.Entities.AdminEntity;
import com.example.sangameswaran.nccarmy.Entities.AssignTaskEntity;
import com.example.sangameswaran.nccarmy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sangameswaran on 23-07-2017.
 */

public class ViewMyTaskFragment extends Fragment{
    RecyclerView myTaskRecyclerView;
    TaskAdapter taskAdapter;
    RecyclerView.LayoutManager manager;
    ArrayList<AssignTaskEntity> taskList;
    RelativeLayout loaderLayout;
    ArrayList<String> keyList;
    AdminEntity adminEntity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.view_my_tasks_fragment,container,false);
        getActivity().setTitle("View My Tasks");
        myTaskRecyclerView=(RecyclerView)v.findViewById(R.id.viewMyTaskRecyclerView);
        loaderLayout=(RelativeLayout)v.findViewById(R.id.viewMyTaskLoader);
        manager=new LinearLayoutManager(getActivity());
        taskList=new ArrayList<>();
        keyList=new ArrayList<>();
        loaderLayout.setVisibility(View.VISIBLE);
        Gson gs=new Gson();
        try {
            SharedPreferences sp=getActivity().getSharedPreferences("userDetails",MODE_PRIVATE);
            String jsonString=sp.getString("user","throwRuntimeException");
            adminEntity = gs.fromJson(jsonString, AdminEntity.class);
        }catch (Exception e){

        }
        DatabaseReference getAllTaskApi= FirebaseDatabase.getInstance().getReference("AssignedTasks");
        getAllTaskApi.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        try {
                            AssignTaskEntity e = ds.getValue(AssignTaskEntity.class);
                            if(e.getAssigned_user().equals(adminEntity.getUser_name())){
                            taskList.add(e);
                            keyList.add(ds.getKey());
                            taskAdapter=new TaskAdapter(taskList,getContext(),true,keyList);
                            myTaskRecyclerView.setLayoutManager(manager);
                            myTaskRecyclerView.setAdapter(taskAdapter);
                            taskAdapter.notifyDataSetChanged();
                        }}catch (Exception e){
                            Toast.makeText(getContext(),"Parse Error",Toast.LENGTH_LONG).show();
                        }
                    }
                    loaderLayout.setVisibility(View.GONE);
                    Collections.reverse(taskList);
                    Collections.reverse(keyList);
                    taskAdapter=new TaskAdapter(taskList,getContext(),true,keyList);
                    myTaskRecyclerView.setLayoutManager(manager);
                    myTaskRecyclerView.setAdapter(taskAdapter);
                    taskAdapter.notifyDataSetChanged();
                }else {
                    loaderLayout.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"No tasks found for you",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;
    }
    public void RefreshFragment(){
        ViewMyTaskFragment fragment=new ViewMyTaskFragment();
        getFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
    }
}
