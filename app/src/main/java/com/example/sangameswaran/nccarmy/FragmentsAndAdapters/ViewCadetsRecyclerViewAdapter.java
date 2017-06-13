package com.example.sangameswaran.nccarmy.FragmentsAndAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.sangameswaran.nccarmy.Entities.CadetEntity;
import com.example.sangameswaran.nccarmy.R;
import java.util.ArrayList;

/**
 * Created by Sangameswaran on 12-05-2017.
 */

public class ViewCadetsRecyclerViewAdapter extends RecyclerView.Adapter<ViewCadetsRecyclerViewAdapter.ViewCadetsRecyclerViewHolder> {
    ArrayList<CadetEntity> cadetEntities=new ArrayList<>();
    ViewCadetsRecyclerViewAdapter(ArrayList<CadetEntity> cadetEntities)
    {
        this.cadetEntities=cadetEntities;
    }
    @Override
    public ViewCadetsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cadet_card_layout,parent,false);
        ViewCadetsRecyclerViewHolder holder=new ViewCadetsRecyclerViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewCadetsRecyclerViewHolder holder, int position) {

        CadetEntity entity=new CadetEntity();
        entity=cadetEntities.get(position);
        holder.t1.setText("NAME OF THE CADET:"+entity.getName());
        holder.t2.setText("REGIMENTAL NUMBER:"+entity.getRegimental_number());
        holder.t3.setText("RANK:"+entity.getRank());
        holder.t4.setText("PLATOON:"+entity.getPlatoon());
        holder.t5.setText("PASS_OUT_YEAR:"+entity.getPass_out_year());
        holder.t6.setText("DOE:"+entity.getDate_of_enrollment());
        holder.t7.setText("DOB:"+entity.getDate_of_birth());
        holder.t8.setText("CONTACT:"+entity.getContact_number());
        holder.t9.setText("EMAIL:"+entity.getEmailid());
        holder.t10.setText("COLLEGE ROLL NUMBER:"+entity.getCollege_roll_number());
        holder.t11.setText("FATHERS NAME:"+entity.getFather_name());
        holder.t12.setText("MOTHERS NAME:"+entity.getMother_name());
        holder.t13.setText("MESS PREF:"+entity.getMess_preference());
        holder.t14.setText("BANK_ACC: "+entity.getBank_account_number());
        holder.t15.setText("IFSC:"+entity.getIfsc_code());
        holder.t16.setText("DEPT:"+entity.getDepartment());
        holder.t17.setText("BLOOD GROUP:"+entity.getBlood_group());
        holder.t18.setText("WILLING?:"+entity.getWillingness_to_donate());
        holder.t19.setText("FATHER OCC:"+entity.getFathers_occupation());
        holder.t20.setText("MOTHER OCC:"+entity.getMothers_occupation());
        holder.t21.setText("ADD:"+entity.getAddress());
        holder.t22.setText("YEARLY INOME:"+entity.getYearly_income_of_parents());
    }

    @Override
    public int getItemCount() {
        return cadetEntities.size();
    }
    public class ViewCadetsRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20,t21,t22;
        public ViewCadetsRecyclerViewHolder(View itemView) {
            super(itemView);
            t1=(TextView) itemView.findViewById(R.id.tv1);
            t2=(TextView) itemView.findViewById(R.id.tv2);
            t3=(TextView) itemView.findViewById(R.id.tv3);
            t4=(TextView) itemView.findViewById(R.id.tv4);
            t5=(TextView) itemView.findViewById(R.id.tv5);
            t6=(TextView) itemView.findViewById(R.id.tv6);
            t7=(TextView) itemView.findViewById(R.id.tv7);
            t8=(TextView) itemView.findViewById(R.id.tv8);
            t9=(TextView) itemView.findViewById(R.id.tv9);
            t10=(TextView) itemView.findViewById(R.id.tv10);
            t11=(TextView) itemView.findViewById(R.id.tv11);
            t12=(TextView) itemView.findViewById(R.id.tv12);
            t13=(TextView) itemView.findViewById(R.id.tv13);
            t14=(TextView) itemView.findViewById(R.id.tv14);
            t15=(TextView) itemView.findViewById(R.id.tv15);
            t16=(TextView) itemView.findViewById(R.id.tv16);
            t17=(TextView) itemView.findViewById(R.id.tv17);
            t18=(TextView) itemView.findViewById(R.id.tv18);
            t19=(TextView) itemView.findViewById(R.id.tv19);
            t20=(TextView) itemView.findViewById(R.id.tv20);
            t21=(TextView) itemView.findViewById(R.id.tv21);
            t22=(TextView) itemView.findViewById(R.id.tv22);
        }
    }
    public void setFiler(ArrayList<CadetEntity> newList)
    {
        cadetEntities=new ArrayList<>();
        cadetEntities.addAll(newList);
        notifyDataSetChanged();
    }

}
