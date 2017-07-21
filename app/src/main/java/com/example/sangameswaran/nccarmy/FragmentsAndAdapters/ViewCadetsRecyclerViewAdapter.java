package com.example.sangameswaran.nccarmy.FragmentsAndAdapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sangameswaran.nccarmy.Entities.CadetEntity;
import com.example.sangameswaran.nccarmy.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Sangameswaran on 12-05-2017.
 */

public class ViewCadetsRecyclerViewAdapter extends RecyclerView.Adapter<ViewCadetsRecyclerViewAdapter.ViewCadetsRecyclerViewHolder> {
    ArrayList<CadetEntity> cadetEntities = new ArrayList<>();
    Context ctx;

    ViewCadetsRecyclerViewAdapter(ArrayList<CadetEntity> cadetEntities, Context c) {
        this.cadetEntities = cadetEntities;
        this.ctx = c;
    }

    @Override
    public ViewCadetsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cadet_card_layout, parent, false);
        ViewCadetsRecyclerViewHolder holder = new ViewCadetsRecyclerViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewCadetsRecyclerViewHolder holder, int position) {

        CadetEntity entity = new CadetEntity();
        entity = cadetEntities.get(position);
        holder.t1.setText("" + entity.getName());
        holder.t2.setText("" + entity.getRegimental_number());
        holder.t3.setText("Rank:" + entity.getRank());
        holder.t4.setText("Platoon:" + entity.getPlatoon());
        holder.t5.setText("Pass out year:" + entity.getPass_out_year());
        holder.t6.setText("Date Of Enrollment:" + entity.getDate_of_enrollment());
        holder.t7.setText("Date of Birth:" + entity.getDate_of_birth());
        holder.t8.setText("Contact number : " + entity.getContact_number());
        holder.t9.setText("Email Id: " + entity.getEmailid());
        holder.t10.setText("College Id : " + entity.getCollege_roll_number());
        holder.t11.setText("Fathers name : " + entity.getFather_name());
        holder.t12.setText("Mothers name : " + entity.getMother_name());
        holder.t13.setText("Mess Preference : " + entity.getMess_preference());
        holder.t14.setText("Bank Account Number : " + entity.getBank_account_number());
        holder.t15.setText("IFSC : " + entity.getIfsc_code());
        holder.t16.setText("Department :" + entity.getDepartment());
        holder.t17.setText("Blood Group : " + entity.getBlood_group());
        holder.t18.setText("Willingness to donate : " + entity.getWillingness_to_donate());
        holder.t19.setText("Fathers Occupation : " + entity.getFathers_occupation());
        holder.t20.setText("Mothers Occupation : " + entity.getMothers_occupation());
        holder.t21.setText("Residential Address : " + entity.getAddress());
        holder.t22.setText("Yearly Income : " + entity.getYearly_income_of_parents());
        final CadetEntity finalEntity = entity;
        holder.callCadetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx,"Calling cadet.. please wait",Toast.LENGTH_LONG).show();
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + finalEntity.getContact_number()));
                if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                ctx.startActivity(callIntent);
            }
        });
        holder.messageCadetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                Intent messageIntent=new Intent(Intent.ACTION_VIEW);
                messageIntent.setData(Uri.parse("sms:"+finalEntity.getContact_number()));
                ctx.startActivity(messageIntent);
            }catch (Exception e){
                    Toast.makeText(ctx,"Error in Sending message",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cadetEntities.size();
    }
    public class ViewCadetsRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20,t21,t22;
        TextView callCadetTv,messageCadetTv;
        public ViewCadetsRecyclerViewHolder(View itemView) {
            super(itemView);
            callCadetTv=(TextView)itemView.findViewById(R.id.callCadetTv);
            messageCadetTv=(TextView)itemView.findViewById(R.id.messageCadetTv);
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
