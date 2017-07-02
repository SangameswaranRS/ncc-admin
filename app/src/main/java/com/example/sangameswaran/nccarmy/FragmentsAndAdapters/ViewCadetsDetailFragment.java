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
import android.widget.SearchView;
import android.widget.Toast;

import com.example.sangameswaran.nccarmy.Entities.CadetEntity;
import com.example.sangameswaran.nccarmy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Sangameswaran on 12-05-2017.
 */

public class ViewCadetsDetailFragment extends Fragment {
     SearchView cadetSearch;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    ArrayList<CadetEntity> arrayList=new ArrayList<>();
    ViewCadetsRecyclerViewAdapter adapter;
    RelativeLayout loader1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.view_cadets_details_fragment,container,false);
        getActivity().setTitle("Cadet details");
        recyclerView =(RecyclerView) v.findViewById(R.id.recyclerViewCadets);
        manager=new LinearLayoutManager(getActivity());
        loader1=(RelativeLayout) v.findViewById(R.id.loader1) ;
        cadetSearch=(SearchView)v.findViewById(R.id.cadetSearch);
        DatabaseReference myreference= FirebaseDatabase.getInstance().getReference("Cadets Data");
        myreference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren())
                {
                    CadetEntity entity=new CadetEntity();
                    for(DataSnapshot dsp : dataSnapshot.getChildren())
                    {
                        entity=dsp.getValue(CadetEntity.class);
                        arrayList.add(entity);
                        adapter=new ViewCadetsRecyclerViewAdapter(arrayList);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(manager);
                        adapter.notifyDataSetChanged();
                        loader1.setVisibility(View.GONE);
                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"Error in connectivity",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter=new ViewCadetsRecyclerViewAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        adapter.notifyDataSetChanged();
        cadetSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<CadetEntity> newList=new ArrayList<CadetEntity>();
                for(CadetEntity entity:arrayList)
                {
                    if(entity.getRegimental_number().contains(newText))
                    {
                        newList.add(entity);
                    }
                }
                adapter.setFiler(newList);

                return true;


            }
        });

        return v;
    }


}
