package com.example.sangameswaran.nccarmy.FragmentsAndAdapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sangameswaran.nccarmy.Entities.AdminEntity;
import com.example.sangameswaran.nccarmy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Sangameswaran on 10-05-2017.
 */

public class CreateNewAdminFragment extends Fragment {

    EditText UserName,Regimenta_number,Password,SecurityQuestion,SecurityAnswer;
    TextView t;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.create_new_admin_fragment,container,false);
        //initialize views


        UserName = (EditText) v.findViewById(R.id.etSigninUserName);
        Regimenta_number=(EditText)v.findViewById(R.id.etSigninRegimentalNumber);
        Password=(EditText)v.findViewById(R.id.etSigninPassword);
        SecurityQuestion=(EditText)v.findViewById(R.id.etSigninSecurityQuestion);
        SecurityAnswer=(EditText)v.findViewById(R.id.etSigninSecurityAnswer);
        t=(TextView)v.findViewById(R.id.tvCreateAdminBtn);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UserName.getText().toString().equals(""))
                {
                    UserName.setError("Username Required");
                }
                if(Regimenta_number.getText().toString().equals(""))
                {
                    Regimenta_number.setError("Regimental number Required");
                }
                if(Password.getText().toString().equals(""))
                {
                    Password.setError("Password Required");
                }
                if (SecurityQuestion.getText().toString().equals(""))
                {
                    SecurityQuestion.setError("Security Question required");
                }
                if (SecurityAnswer.getText().toString().equals(""))
                {
                    SecurityAnswer.setError("Answer required");
                }
                String User,Reg,pw,sq,sa;
                User=UserName.getText().toString();
                Reg=Regimenta_number.getText().toString();
                pw=Password.getText().toString();
                sq=SecurityQuestion.getText().toString();
                sa=SecurityAnswer.getText().toString();
                if(sa.equals("")||sq.equals("")||User.equals("")||Reg.equals("")||pw.equals(""))
                {

                }
                else
                {
                   final DatabaseReference adminSignup;
                   final AdminEntity adminEntity=new AdminEntity(Reg,pw,User,sq,sa,"0","0");
                    adminEntity.setIsAdmin("0");
                    adminEntity.setIsCadet("1");
                    adminEntity.setIsSuperAdmin("0");
                    adminSignup=FirebaseDatabase.getInstance().getReference("Admins/"+User);
                    adminSignup.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.hasChildren())
                            {
                                Toast.makeText(getActivity(),"Username taken,sorry",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getActivity(),"Username Available,Creating admin..",Toast.LENGTH_LONG).show();
                                adminSignup. setValue(adminEntity);
                                Toast.makeText(getActivity(),"Admin created Successfully",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        });

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}
