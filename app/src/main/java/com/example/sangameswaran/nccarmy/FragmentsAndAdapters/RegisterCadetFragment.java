package com.example.sangameswaran.nccarmy.FragmentsAndAdapters;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sangameswaran.nccarmy.Activities.MainActivity;
import com.example.sangameswaran.nccarmy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sangameswaran on 11-05-2017.
 */

public class RegisterCadetFragment extends Fragment {

    EditText e1,e2,e5,e6,e7,e8,e9;
    Spinner e3;
    Spinner e4;
    Button btnNext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.register_cadet_layout_1,container,false);
        e1=(EditText)v.findViewById(R.id.etRegisterName);
        e2=(EditText)v.findViewById(R.id.etRegisterRegimentalNumber);
        e3=(Spinner) v.findViewById(R.id.etRank);
        getActivity().setTitle("Register new cadets");
        e4=(Spinner) v.findViewById(R.id.etPlatoon);
        e5=(EditText)v.findViewById(R.id.etPassoutYear);
        e6=(EditText)v.findViewById(R.id.etDateOfEnrollment);
        e7=(EditText)v.findViewById(R.id.etDateOfBirth);
        e8=(EditText)v.findViewById(R.id.etContactNumber);
        e9=(EditText)v.findViewById(R.id.etContactMail) ;
        List<String> platoons=new ArrayList<>();
        platoons.add("EME");
        platoons.add("Engineers");
        platoons.add("Signals");
        List<String> rank=new ArrayList<>();
        rank.add("CSUO");
        rank.add("CUO");
        rank.add("CQMS");
        rank.add("CSM");
        rank.add("Sgt");
        rank.add("Cpl");
        rank.add("LCpl");
        rank.add("Cdt");
        ArrayAdapter<String> rankAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,rank);
        rankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        e3.setAdapter(rankAdapter);
        ArrayAdapter<String> platoonAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,platoons);
        platoonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        e4.setAdapter(platoonAdapter);
        btnNext=(Button)v.findViewById(R.id.btnNext);

        final MainActivity activity= (MainActivity) getActivity();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e1.getText().toString().equals(""))
                    e1.setError("Name Required");
                if(e2.getText().toString().equals(""))
                    e2.setError("Regimental number Required");
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
                s3=String.valueOf(e3.getSelectedItem());
                s4=String.valueOf(e4.getSelectedItem());
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
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_main,fragment).commit();
                }
            }
        });
        return v;
    }
}
