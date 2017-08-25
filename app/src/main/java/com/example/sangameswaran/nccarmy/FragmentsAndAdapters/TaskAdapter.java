package com.example.sangameswaran.nccarmy.FragmentsAndAdapters;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sangameswaran.nccarmy.Activities.MainActivity;
import com.example.sangameswaran.nccarmy.Entities.AssignTaskEntity;
import com.example.sangameswaran.nccarmy.R;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Sangameswaran on 22-07-2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    ArrayList<AssignTaskEntity> tasks = new ArrayList<>();
    Context context;
    ArrayList<String> keyList;
    boolean showUpdate;
    AlertDialog.Builder updateDialog;
    public TaskAdapter(ArrayList<AssignTaskEntity> entities, Context context,boolean showUpdate,ArrayList<String> keyList) {
        this.tasks = entities;
        this.context = context;
        this.showUpdate=showUpdate;
        updateDialog=new AlertDialog.Builder(context);
        this.keyList=keyList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_card_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final AssignTaskEntity assignTaskEntity = tasks.get(position);
        if (showUpdate) {
            holder.updateBtn.setVisibility(View.VISIBLE);
            holder.callLL.setVisibility(View.GONE);

        }
        else {
            holder.updateBtn.setVisibility(View.GONE);
        }
        holder.tvAssigningUser.setText("Assigning user : " + assignTaskEntity.getAssigning_user());
        holder.tvAssignedUser.setText("Assigned User : " + assignTaskEntity.getAssigned_user());
        holder.tvAssignedAt.setText("Assigned at : " + assignTaskEntity.getAssigned_timestamp());
        holder.tvDeadline.setText("Deadline : " + assignTaskEntity.getDeadline());
        holder.taskRequirement.setText("Tast : "+assignTaskEntity.getTask_requirement());
        try {
            int progress = Integer.parseInt(assignTaskEntity.getStatus());
            holder.cpStatusProgress.setProgress(progress);
            if(progress==100){
                holder.overallStatTv.setText("COMPLETED");
                holder.overallStatTv.setTextColor(Color.parseColor("#64a844"));
            }
            else {
                holder.overallStatTv.setText("PENDING");
                holder.overallStatTv.setTextColor(Color.parseColor("#dcdc39"));
            }
            holder.cpStatusProgress.setProgress(progress);
        } catch (Exception e) {

        }
        holder.callLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + assignTaskEntity.getContact_number()));
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        context.startActivity(intent);
                    }else {
                        Toast.makeText(context,"Permission denied",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(context,"Error in making phoneCall",Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             inflateDialogAndUpdateInfo(position,assignTaskEntity);
            }
        });
    }

    private void inflateDialogAndUpdateInfo(final int position, final AssignTaskEntity entity) {
        LayoutInflater inflator= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView=inflator.inflate(R.layout.update_percent_alert,null,false);
        final EditText update;
        update=(EditText)dialogView.findViewById(R.id.etUpdatePercent);
        updateDialog.setView(dialogView);
        updateDialog.setCancelable(false).setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String per=update.getText().toString();
                if(per.equals("")){}
                else {
                    try{
                        int percen=Integer.parseInt(per);
                        if(MainActivity.percentValidation(percen)) {
                            DatabaseReference updateDatabaseInfo = FirebaseDatabase.getInstance().getReference("AssignedTasks");
                            entity.setStatus("" + percen);
                            updateDatabaseInfo.child(keyList.get(position)).setValue(entity);
                            tasks.get(position).setStatus("" + percen);
                            notifyDataSetChanged();
                            dialog.cancel();
                        }
                        else {
                            Toast.makeText(context,"Updation Failed, Enter Valid percentage",Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){}
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAssigningUser,tvAssignedUser,tvAssignedAt,taskRequirement,tvDeadline,overallStatTv;
        CircleProgress cpStatusProgress;
        LinearLayout callLL;
        Button updateBtn;
        public ViewHolder(View itemView) {
            super(itemView);
            tvAssigningUser=(TextView)itemView.findViewById(R.id.tvAssigningUser);
            tvAssignedUser=(TextView)itemView.findViewById(R.id.tvAssignedUser);
            tvAssignedAt=(TextView)itemView.findViewById(R.id.tvAssignedAt);
            taskRequirement=(TextView)itemView.findViewById(R.id.taskRequirement);
            tvDeadline=(TextView)itemView.findViewById(R.id.tvDeadline);
            overallStatTv=(TextView)itemView.findViewById(R.id.overallStatTv);
            cpStatusProgress=(CircleProgress)itemView.findViewById(R.id.cpStatusProgress);
            callLL=(LinearLayout)itemView.findViewById(R.id.callLL);
            updateBtn=(Button)itemView.findViewById(R.id.updatePercentBtn);
        }
    }
}
