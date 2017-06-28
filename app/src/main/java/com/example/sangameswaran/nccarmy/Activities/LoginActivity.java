package com.example.sangameswaran.nccarmy.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sangameswaran.nccarmy.Entities.AdminEntity;
import com.example.sangameswaran.nccarmy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

/**
 * Created by Sangameswaran on 10-05-2017.
 */

public class LoginActivity extends AppCompatActivity{
    EditText etLoginUserName,etLoginPassword;
    TextView tvLoginButton;
    LinearLayout progressLL,ContainerLL;
    TextView loaderMessage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        progressLL=(LinearLayout)findViewById(R.id.progressLL);
        ContainerLL=(LinearLayout)findViewById(R.id.containerll);
        etLoginUserName=(EditText)findViewById(R.id.etLoginUserName);
        loaderMessage=(TextView)findViewById(R.id.tvLoginLoaderMessage);
        etLoginPassword=(EditText)findViewById(R.id.etLoginPassword);
        tvLoginButton=(TextView)findViewById(R.id.tvLoginButton);
        tvLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etLoginUserName.getText().toString().equals(""))
                    etLoginUserName.setError("Enter User name");
                if (etLoginPassword.getText().toString().equals(""))
                    etLoginPassword.setError("Enter Password");
                if(etLoginUserName.getText().toString().equals("")||etLoginPassword.getText().toString().equals(""))
                {}
                else
                {
                    ContainerLL.setVisibility(View.GONE);
                    progressLL.setVisibility(View.VISIBLE);
                    tvLoginButton.setVisibility(View.GONE);
                    loaderMessage.setText("Checking your credentials..");
                    final String username=etLoginUserName.getText().toString();
                    final String password=etLoginPassword.getText().toString();
                    DatabaseReference mdatabase= FirebaseDatabase.getInstance().getReference("Admins/"+username);
                    mdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChildren())
                            {

                                Log.d("tag","Admins/"+username);
                                Log.d("Tag",""+dataSnapshot.toString());
                                AdminEntity adminLogin=new AdminEntity();
                                adminLogin=dataSnapshot.getValue(AdminEntity.class);
                                if(password.equals(adminLogin.getPassword()))
                                {
                                    if(adminLogin.getLogin_flag().equals("0"))
                                    {
                                        if(adminLogin.getBlocked_status().equals("0")) {
                                            Toast.makeText(getApplicationContext(), "Authorization Success", Toast.LENGTH_LONG).show();
                                            SharedPreferences sp=getSharedPreferences("LoginCredentials",MODE_PRIVATE);
                                            SharedPreferences.Editor editor=sp.edit();
                                            editor.putString("MyloginID",adminLogin.getUser_name());
                                            editor.commit();
                                            SharedPreferences w=getSharedPreferences("userDetails",MODE_PRIVATE);
                                            SharedPreferences.Editor editor1=w.edit();
                                            try {
                                                Gson gson=new Gson();
                                                String json=gson.toJson(adminLogin,AdminEntity.class);
                                                editor1.putString("user",json);
                                                editor1.commit();
                                            }catch (Exception e){
                                                Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();
                                                finish();
                                            }
                                            createSession(adminLogin.getUser_name());
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                        else
                                        {
                                            progressLL.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(),"You are blocked,Sorry",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    else
                                    {
                                        if(adminLogin.getBlocked_status().equals("0")) {
                                            loaderMessage.setText("Retreiving your last settings...");
                                            checkSession(adminLogin.getUser_name());
                                        }else {
                                            progressLL.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(),"You are blocked,Sorry",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                                else
                                {
                                    ContainerLL.setVisibility(View.VISIBLE);
                                    progressLL.setVisibility(View.GONE);
                                    tvLoginButton.setVisibility(View.VISIBLE);
                                    Toast.makeText(getApplicationContext(),"Wrong password for "+username,Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                            {
                                ContainerLL.setVisibility(View.VISIBLE);
                                progressLL.setVisibility(View.GONE);
                                tvLoginButton.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(),"User name doesnot exist",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        });


    }

    private void checkSession(String user_name) {
        DatabaseReference get=FirebaseDatabase.getInstance().getReference("ACTIVE_SESSION/"+user_name);
        get.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                String imei=dataSnapshot.getValue(String.class);
                TelephonyManager tm= (TelephonyManager) LoginActivity.this.getSystemService(TELEPHONY_SERVICE);
                String actualImei=tm.getDeviceId();
                if(imei.equals(actualImei)){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    ContainerLL.setVisibility(View.VISIBLE);
                    progressLL.setVisibility(View.GONE);
                    tvLoginButton.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"Login From Multiple Devices is blocked",Toast.LENGTH_LONG).show();
                }}catch (Exception e){}

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void createSession(String userName) {
        DatabaseReference sessionApi=FirebaseDatabase.getInstance().getReference("ACTIVE_SESSION");
        TelephonyManager telephonyManager= (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        sessionApi.child(userName).setValue(telephonyManager.getDeviceId());
    }
}
