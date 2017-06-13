package com.example.sangameswaran.nccarmy.FragmentsAndAdapters;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sangameswaran.nccarmy.Activities.MainActivity;
import com.example.sangameswaran.nccarmy.R;

/**
 * Created by Sangameswaran on 11-05-2017.
 */

public class RegisterCadetFragment extends Fragment {

    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9;
    Button btnNext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.register_cadet_layout_1,container,false);
        e1=(EditText)v.findViewById(R.id.etRegisterName);
        e2=(EditText)v.findViewById(R.id.etRegisterRegimentalNumber);
        e3=(EditText)v.findViewById(R.id.etRank);
        e4=(EditText)v.findViewById(R.id.etPlatoon);
        e5=(EditText)v.findViewById(R.id.etPassoutYear);
        e6=(EditText)v.findViewById(R.id.etDateOfEnrollment);
        e7=(EditText)v.findViewById(R.id.etDateOfBirth);
        e8=(EditText)v.findViewById(R.id.etContactNumber);
        e9=(EditText)v.findViewById(R.id.etContactMail) ;
        btnNext=(Button)v.findViewById(R.id.btnNext);

        final MainActivity activity= (MainActivity) getActivity();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e1.getText().toString().equals(""))
                    e1.setError("Name Required");
                if(e2.getText().toString().equals(""))
                    e2.setError("Regimental number Required");
                if(e3.getText().toString().equals(""))
                    e3.setError("Rank Required");
                if(e4.getText().toString().equals(""))
                    e4.setError("Platoon Required");
                if(e5.getText().toString().equals(""))
                    e5.setError("Passout year Required");
                if(e6.getText().toString().equals(""))
                    e6.setError("DOE Required");
                if(e7.getText().toString().equals(""))
                    e7.setError("DOB Required");
                if(e8.getText().toString().equals(""))
                    e8.setError("Contact Number Required");
                if(e9.getText().toString().equals(""))
                    e9.setError("Contact mail Required");
                String s1,s2,s3,s4,s5,s6,s7,s8,s9;
                s1=e1.getText().toString();
                s2=e2.getText().toString();
                s3=e3.getText().toString();
                s4=e4.getText().toString();
                s5=e5.getText().toString();
                s6=e6.getText().toString();
                s7=e7.getText().toString();
                s8=e8.getText().toString();
                s9=e9.getText().toString();
                if(s1.equals("")||s2.equals("")||s3.equals("")||s4.equals("")||s5.equals("")||s6.equals("")||s7.equals("")||s8.equals("")||s9.equals(""))
                {
                    Toast.makeText(getActivity(),"Enter all the fields",Toast.LENGTH_LONG).show();
                }
                else
                {
                    activity.register.setName(s1);
                    activity.register.setRegimental_number(s2);
                    activity.register.setRank(s3);
                    activity.register.setPlatoon(s4);
                    activity.register.setPass_out_year(s5);
                    activity.register.setDate_of_enrollment(s6);
                    activity.register.setDate_of_birth(s7);
                    activity.register.setContact_number(s8);
                    activity.register.setEmailid(s9);
                    Toast.makeText(getActivity(),"Complete Personal details to finish",Toast.LENGTH_LONG).show();
                    RegisterCadetFragment2 fragment=new RegisterCadetFragment2();
                    //RegisterCadetFragment fragment1=new RegisterCadetFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_main,fragment).commit();
                }





            }
        });
        return v;
    }
}
