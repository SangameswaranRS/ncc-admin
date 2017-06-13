package com.example.sangameswaran.nccarmy.FragmentsAndAdapters;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sangameswaran.nccarmy.Entities.AdminEntity;
import com.example.sangameswaran.nccarmy.Entities.PermissionChangeEntity;
import com.example.sangameswaran.nccarmy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sangameswaran on 10-05-2017.
 */

public class GrantRevokePermissionFragment extends Fragment {
    EditText etRevokeUserId;
    EditText etReason;
    TextView RevokeBtn;
    RadioGroup rdgp;
    String selector="Revoke";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.grant_revoke_permission_fragment,container,false);
        //initialize views
        etRevokeUserId=(EditText)v.findViewById(R.id.etRevokeUserID);
        etReason=(EditText)v.findViewById(R.id.etReason);
        RevokeBtn=(TextView)v.findViewById(R.id.tvRevokeButton);
        rdgp=(RadioGroup)v.findViewById(R.id.rgSelectorRadio);
        rdgp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if(i==R.id.rbGrant)
                    selector="Grant";
                else if(i==R.id.rbRevoke)
                    selector="Revoke";
            }
        });
        RevokeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etReason.getText().toString().equals(""))
                    etReason.setError("Reason Required");
                if(etRevokeUserId.getText().toString().equals(""))
                    etRevokeUserId.setError("UserId Required");
                if(etReason.getText().toString().equals("")||etRevokeUserId.getText().toString().equals(""))
                {}
                else {
                    String UserId, reason, time;
                    SharedPreferences sp = getActivity().getSharedPreferences("LoginCredentials", MODE_PRIVATE);
                    UserId = sp.getString("MyloginID", "Error");
                    final String Revokedid = etRevokeUserId.getText().toString();
                    reason = etReason.getText().toString()+"ACTION PERFORMED :"+selector;
                    java.util.Calendar calendar = java.util.Calendar.getInstance();
                    time = "" + calendar.getTime();
                    DatabaseReference myreference = FirebaseDatabase.getInstance().getReference();
                    PermissionChangeEntity entity = new PermissionChangeEntity(UserId, Revokedid, time, reason);
                    String id = myreference.push().getKey();
                    myreference.child("PermissionChanges").child(id).setValue(entity);

                    if (selector.equals("Revoke")) {
                        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Admins/" + Revokedid);
                        myref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (dataSnapshot.hasChildren()) {
                                    AdminEntity entity = new AdminEntity();
                                    entity = dataSnapshot.getValue(AdminEntity.class);
                                    entity.setBlocked_status("1");
                                    DatabaseReference myref3 = FirebaseDatabase.getInstance().getReference("Admins/" + Revokedid);
                                    myref3.setValue(entity);
                                    Toast.makeText(getContext(), "User Permission Changed", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getContext(), "UserId doesnot Exist", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    } else {
                        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Admins/" + Revokedid);
                        myref.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override

                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (dataSnapshot.hasChildren()) {
                                    AdminEntity entity = new AdminEntity();
                                    entity = dataSnapshot.getValue(AdminEntity.class);
                                    entity.setBlocked_status("0");
                                    entity.setLogin_flag("0");
                                    DatabaseReference myref3 = FirebaseDatabase.getInstance().getReference("Admins/" + Revokedid);
                                    myref3.setValue(entity);
                                    Toast.makeText(getContext(), "User Permission Changed", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getContext(), "UserId doesnot Exist", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }

                }



            }
        });
        return v;

    }
}
