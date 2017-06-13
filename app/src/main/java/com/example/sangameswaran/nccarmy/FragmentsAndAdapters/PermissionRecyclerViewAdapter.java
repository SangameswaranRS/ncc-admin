package com.example.sangameswaran.nccarmy.FragmentsAndAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sangameswaran.nccarmy.Entities.PermissionChangeEntity;
import com.example.sangameswaran.nccarmy.R;

import java.util.ArrayList;

/**
 * Created by Sangameswaran on 11-05-2017.
 */

public class PermissionRecyclerViewAdapter extends RecyclerView.Adapter<PermissionRecyclerViewAdapter.PermissionRecyclerViewHolder>{

    ArrayList<PermissionChangeEntity> permissionChangeEntities=new ArrayList<>();
    PermissionRecyclerViewAdapter(ArrayList<PermissionChangeEntity> entities)
    {
        this.permissionChangeEntities=entities;
    }
    @Override
    public PermissionRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.permission_recycler_view_card_layout,parent,false);
        PermissionRecyclerViewHolder holder=new PermissionRecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PermissionRecyclerViewHolder holder, int position) {

        PermissionChangeEntity entity=permissionChangeEntities.get(position);
        holder.t1.setText("PCU:    "+entity.getUser_id());
        holder.t2.setText("CU:     "+entity.getRevoked_by());
        holder.t3.setText("REASON: "+entity.getReasonsForPermissionChange());
        holder.t4.setText("TIME:   "+entity.getRevoked_during());
    }

    @Override
    public int getItemCount() {
        return permissionChangeEntities.size();
    }
    public class PermissionRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3,t4;

        public PermissionRecyclerViewHolder(View itemView) {
            super(itemView);
            t1=(TextView) itemView.findViewById(R.id.tv1);
            t2=(TextView) itemView.findViewById(R.id.tv2);
            t3=(TextView) itemView.findViewById(R.id.tv3);
            t4=(TextView) itemView.findViewById(R.id.tv4);
        }
    }
}
