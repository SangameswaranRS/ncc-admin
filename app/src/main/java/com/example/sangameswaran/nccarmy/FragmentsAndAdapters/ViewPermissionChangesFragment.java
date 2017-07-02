package com.example.sangameswaran.nccarmy.FragmentsAndAdapters;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.sangameswaran.nccarmy.Entities.PermissionChangeEntity;
import com.example.sangameswaran.nccarmy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Sangameswaran on 11-05-2017.
 */

public class ViewPermissionChangesFragment extends Fragment
{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    ArrayList<PermissionChangeEntity> arrayList=new ArrayList<>();
    PermissionRecyclerViewAdapter adapter;
    RelativeLayout loader3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.view_permission_changes_fragment,container,false);
        getActivity().setTitle("View Permission changes");
        recyclerView=(RecyclerView)v.findViewById(R.id.permissionRecyclerView);
        manager=new LinearLayoutManager(getContext());
        loader3=(RelativeLayout)v.findViewById(R.id.loader3);
        loader3.setVisibility(View.VISIBLE);
       DatabaseReference permissionReference= FirebaseDatabase.getInstance().getReference().child("PermissionChanges");
        permissionReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren())
                {
                    for(DataSnapshot dsp :dataSnapshot.getChildren())
                    {
                        PermissionChangeEntity entity=new PermissionChangeEntity();
                        entity=dsp.getValue(PermissionChangeEntity.class);
                        arrayList.add(entity);
                        adapter=new PermissionRecyclerViewAdapter(arrayList);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(manager);
                        adapter.notifyDataSetChanged();
                    }
                    Collections.reverse(arrayList);
                    adapter.notifyDataSetChanged();
                   //recyclerView.scrollToPosition(arrayList.size()-1);
                    loader3.setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(),"Connectivity Error",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity().getApplicationContext(),"DB Error",Toast.LENGTH_LONG).show();
            }

        });
        adapter=new PermissionRecyclerViewAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        adapter.notifyDataSetChanged();

        return  v;
    }
}
