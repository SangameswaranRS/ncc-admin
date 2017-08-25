package com.example.sangameswaran.nccarmy.FragmentsAndAdapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sangameswaran.nccarmy.Activities.MainActivity;
import com.example.sangameswaran.nccarmy.Entities.AdminEntity;
import com.example.sangameswaran.nccarmy.Entities.AssignTaskEntity;
import com.example.sangameswaran.nccarmy.Entities.CadetEntity;
import com.example.sangameswaran.nccarmy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sangameswaran on 19-07-2017.
 */

public class AssignTaskFragment extends Fragment {
    Spinner assigningUser,assignedUser;
    EditText requirement,percentageOfCompletion,deadline;
    TextView assignedTimestampDetail;
    Button btnAssignTask;
    ArrayList<AdminEntity> admins;
    RelativeLayout progressPanel;
    AdminEntity adminEntity;
    AssignTaskEntity taskEntity=new AssignTaskEntity();
    Gson gs=new Gson();
    List<CadetEntity> cadetEntities=new ArrayList<>();
    boolean hasFoundNumber=false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.assign_task_fragment,container,false);
        admins=new ArrayList<>();
        getActivity().setTitle("View All Tasks");
        assigningUser=(Spinner)v.findViewById(R.id.taskOwnerSpinner);
        getActivity().setTitle("Assign Task");
        progressPanel=(RelativeLayout)v.findViewById(R.id.progressPanel);
        assignedUser=(Spinner)v.findViewById(R.id.taskAssignedToSpinner);
        requirement=(EditText)v.findViewById(R.id.etTaskRequirement);
        btnAssignTask=(Button)v.findViewById(R.id.btnAssignTask);
        percentageOfCompletion=(EditText)v.findViewById(R.id.taskPercentageOfCompletion);
        deadline=(EditText)v.findViewById(R.id.etDeadline);
        assignedTimestampDetail=(TextView)v.findViewById(R.id.taskAssignedTimeStamp);
        progressPanel.setVisibility(View.VISIBLE);
        btnAssignTask.setVisibility(View.GONE);
        final Calendar calendar=Calendar.getInstance();
        assignedTimestampDetail.setText("Assigned at : "+calendar.get(Calendar.DATE)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR)+" TIME : "+calendar.get(Calendar.HOUR_OF_DAY)+"hours and"+calendar.get(Calendar.MINUTE)+"minutes");
        btnAssignTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int perVal=101;
                try {
                     perVal = Integer.parseInt(percentageOfCompletion.getText().toString());
                }catch (Exception e){

                }
                if(requirement.getText().toString().equals("")||deadline.getText().toString().equals("")||percentageOfCompletion.getText().toString().equals("")||(!(MainActivity.percentValidation(perVal)))){
                    if(requirement.getText().toString().equals("")){
                        requirement.setError("Requirement field cannot be empty");
                    }else if(deadline.getText().toString().equals("")){
                        deadline.setError("Deadline required");
                    }else if(percentageOfCompletion.getText().toString().equals("")|| ! MainActivity.percentValidation(Integer.parseInt(percentageOfCompletion.getText().toString()))){
                        percentageOfCompletion.setError("Invalid Percentage");
                    }
                }else {
                progressPanel.setVisibility(View.VISIBLE);
                btnAssignTask.setVisibility(View.GONE);
                taskEntity.setAssigned_timestamp(calendar.get(Calendar.DATE)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR)+" TIME : "+calendar.get(Calendar.HOUR_OF_DAY)+"hours and"+calendar.get(Calendar.MINUTE)+"minutes");
                taskEntity.setAssigning_user(String.valueOf(assigningUser.getSelectedItem()));
                taskEntity.setAssigned_user(String.valueOf(assignedUser.getSelectedItem()));
                taskEntity.setTask_requirement(requirement.getText().toString());
                taskEntity.setDeadline(deadline.getText().toString());
                taskEntity.setStatus(percentageOfCompletion.getText().toString());
                try {
                    SharedPreferences sp=getActivity().getSharedPreferences("userDetails",MODE_PRIVATE);
                    String jsonString=sp.getString("user","throwRuntimeException");
                    adminEntity=gs.fromJson(jsonString,AdminEntity.class);
                    final DatabaseReference getAllDetails=FirebaseDatabase.getInstance().getReference("Cadets Data");
                    getAllDetails.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChildren()){
                                for(DataSnapshot ds:dataSnapshot.getChildren()){
                                    cadetEntities.add(ds.getValue(CadetEntity.class));
                                }
                                String user=String.valueOf(assignedUser.getSelectedItem());
                                String userRegNum=null;
                                for(AdminEntity e:admins){
                                    if (e.getUser_name().equals(user)){
                                        userRegNum=e.getRegimental_number();
                                        break;
                                    }
                                }
                                for(CadetEntity entity:cadetEntities){
                                    if(entity.getRegimental_number().equals(userRegNum)){
                                        taskEntity.setContact_number(entity.getContact_number());
                                        hasFoundNumber=true;
                                        break;
                                    }
                                }
                                if (hasFoundNumber){
                                    DatabaseReference pushRef=FirebaseDatabase.getInstance().getReference("AssignedTasks");
                                    String key=pushRef.push().getKey();
                                    pushRef.child(key).setValue(taskEntity);
                                    SmsManager sms=SmsManager.getDefault();
                                    try {
                                        sms.sendTextMessage(taskEntity.getContact_number(), null, "TASK ASSIGNED BY: " + taskEntity.getAssigning_user() + " TASK: " + taskEntity.getTask_requirement() + " DEADLINE:" + taskEntity.getDeadline(), null, null);
                                        Toast.makeText(getActivity(), "Task Assigned", Toast.LENGTH_LONG).show();
                                        AssignTaskFragment fragment = new AssignTaskFragment();
                                        getFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
                                    }catch (Exception e){
                                        Toast.makeText(getActivity(),"Encountered Exception : "+e.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }else {
                                    Toast.makeText(getActivity(),"Unable to find contact number,Try again",Toast.LENGTH_LONG).show();
                                    progressPanel.setVisibility(View.GONE);
                                    btnAssignTask.setVisibility(View.VISIBLE);
                                }
                            }else {
                                Toast.makeText(getActivity(),"No cadet Enrolled yet",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }catch (Exception e){
                    Toast.makeText(getActivity(),"Cast Exception",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        DatabaseReference getAdminUserApi= FirebaseDatabase.getInstance().getReference("Admins");
        getAdminUserApi.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    for(DataSnapshot iterator:dataSnapshot.getChildren()){
                        try {
                            AdminEntity entity = new AdminEntity();
                            entity = iterator.getValue(AdminEntity.class);
                            admins.add(entity);
                        }catch (Exception e){
                            Toast.makeText(getActivity(),"Parse Exception encountered",Toast.LENGTH_LONG).show();
                        }
                    }
                    List<String> users=new ArrayList<String>();
                    for(AdminEntity entity:admins){
                        users.add(entity.getUser_name());
                    }
                    ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,users);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    assigningUser.setAdapter(dataAdapter);
                    assignedUser.setAdapter(dataAdapter);
                    progressPanel.setVisibility(View.GONE);
                    btnAssignTask.setVisibility(View.VISIBLE);

                }else {
                    Toast.makeText(getActivity(),"No Admins found",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }
}
