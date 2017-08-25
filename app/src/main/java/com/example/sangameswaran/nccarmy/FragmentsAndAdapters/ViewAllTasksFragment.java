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
import android.widget.Toast;

import com.example.sangameswaran.nccarmy.Entities.AssignTaskEntity;
import com.example.sangameswaran.nccarmy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Sangameswaran on 22-07-2017.
 */

public class ViewAllTasksFragment extends Fragment {
    RecyclerView viewAllTaskRv;
    ArrayList<AssignTaskEntity> taskList;
    RecyclerView.LayoutManager manager;
    TaskAdapter taskAdapter;
    ArrayList<String> keyList=new ArrayList<>();
    RelativeLayout taskLoaderRelativeLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.view_all_tasks_fragment,container,false);
        taskList=new ArrayList<>();
        getActivity().setTitle("View All Tasks");
        taskLoaderRelativeLayout=(RelativeLayout)v.findViewById(R.id.taskLoaderRelativeLayout);
        taskLoaderRelativeLayout.setVisibility(View.VISIBLE);
        viewAllTaskRv=(RecyclerView)v.findViewById(R.id.viewAllTaskRv);
        manager=new LinearLayoutManager(getActivity());
        DatabaseReference getAllTaskApi= FirebaseDatabase.getInstance().getReference("AssignedTasks");
        getAllTaskApi.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        try {
                            AssignTaskEntity e = ds.getValue(AssignTaskEntity.class);
                            taskList.add(e);
                            keyList.add(ds.getKey());
                            taskAdapter=new TaskAdapter(taskList,getContext(),false,keyList);
                            viewAllTaskRv.setLayoutManager(manager);
                            viewAllTaskRv.setAdapter(taskAdapter);
                            taskAdapter.notifyDataSetChanged();
                        }catch (Exception e){
                            Toast.makeText(getContext(),"Parse Error",Toast.LENGTH_LONG).show();
                        }
                    }
                    taskLoaderRelativeLayout.setVisibility(View.GONE);
                    Collections.reverse(taskList);
                    Collections.reverse(keyList);
                    taskAdapter=new TaskAdapter(taskList,getContext(),false,keyList);
                    viewAllTaskRv.setLayoutManager(manager);
                    viewAllTaskRv.setAdapter(taskAdapter);
                    taskAdapter.notifyDataSetChanged();
                }else {
                    taskLoaderRelativeLayout.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"No tasks found",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;
    }
}
