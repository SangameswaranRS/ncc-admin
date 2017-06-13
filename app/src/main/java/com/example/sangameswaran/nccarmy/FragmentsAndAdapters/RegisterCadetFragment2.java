package com.example.sangameswaran.nccarmy.FragmentsAndAdapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sangameswaran.nccarmy.Activities.MainActivity;
import com.example.sangameswaran.nccarmy.Entities.AttendanceEntity;
import com.example.sangameswaran.nccarmy.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Sangameswaran on 12-05-2017.
 */

public class RegisterCadetFragment2 extends Fragment
{
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11,e12,e13;
    Button bFinish;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.register_cadet_layout_2,container,false);

        e1=(EditText)v.findViewById(R.id.etRegisterCollegeRollNumber);
        e2=(EditText)v.findViewById(R.id.etRegisterFathersName);
        e3=(EditText)v.findViewById(R.id.etRegisterMotherName);
        e4=(EditText)v.findViewById(R.id.etRegisterAddress);
        e5=(EditText)v.findViewById(R.id.etRegisterMessPreference);
        e6=(EditText)v.findViewById(R.id.etBankAccountNumber);
        e7=(EditText)v.findViewById(R.id.etIFSCCode);
        e8=(EditText)v.findViewById(R.id.etRegisterDepartment);
        e9=(EditText)v.findViewById(R.id.etBloodGroup);
        e10=(EditText)v.findViewById(R.id.etWillingnessToDonate);
        e11=(EditText)v.findViewById(R.id.etFatherOccupation);
        e12=(EditText)v.findViewById(R.id.etMothersOccupation);
        e13=(EditText)v.findViewById(R.id.etAnnualIncome);
        bFinish=(Button)v.findViewById(R.id.btnFinish);
        bFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13;
                s1=e1.getText().toString();
                s2=e2.getText().toString();
                s3=e3.getText().toString();
                s4=e4.getText().toString();
                s5=e5.getText().toString();
                s6=e6.getText().toString();
                s7=e7.getText().toString();
                s8=e8.getText().toString();
                s9=e9.getText().toString();
                s10=e10.getText().toString();
                s11=e11.getText().toString();
                s12=e12.getText().toString();
                s13=e13.getText().toString();
                if(s1.equals(""))
                    e1.setError("College roll number required");
                if (s2.equals(""))
                    e2.setError("Father name required");
                if (s3.equals(""))
                    e3.setError("Mother name required");

                if (s4.equals(""))
                    e4.setError("Address required");

                if (s5.equals(""))
                    e5.setError("Mess preference required");

                if (s6.equals(""))
                    e6.setError("Bank account number required");

                if (s7.equals(""))
                    e7.setError("IFSC Code required");

                if (s8.equals(""))
                    e8.setError("Department required");

                if (s9.equals(""))
                    e9.setError("Blood group required");
                if (s10.equals(""))
                    e10.setError("preference is mandatory");

                if (s11.equals(""))
                    e11.setError("Fathers occupation is mandatory");

                if (s12.equals(""))
                    e12.setError("Mothers occupation required");
                if (s13.equals(""))
                    e13.setError("Optional,otherwise specify not interested");

                if(s1.equals("")||s2.equals("")||s3.equals("")||s4.equals("")||s5.equals("")||s6.equals("")||s7.equals("")||s8.equals("")||s9.equals("")||s10.equals("")||s11.equals("")||s12.equals("")||s13.equals(""))
                {
                    Toast.makeText(getActivity(),"Enter all the details to register",Toast.LENGTH_LONG).show();
                }
                else
                {
                    final MainActivity activity= (MainActivity) getActivity();
                    activity.register.setCollege_roll_number(s1);
                    activity.register.setFather_name(s2);
                    activity.register.setMother_name(s3);
                    activity.register.setAddress(s4);
                    activity.register.setMess_preference(s5);
                    activity.register.setBank_account_number(s6);
                    activity.register.setIfsc_code(s7);
                    activity.register.setDepartment(s8);
                    activity.register.setBlood_group(s9);
                    activity.register.setWillingness_to_donate(s10);
                    activity.register.setFathers_occupation(s11);
                    activity.register.setMothers_occupation(s12);
                    activity.register.setYearly_income_of_parents(s13);
                    DatabaseReference dataRegister= FirebaseDatabase.getInstance().getReference("Cadets Data");
                    String id=dataRegister.push().getKey();
                    dataRegister.child(id).setValue(activity.register);
                    AttendanceEntity entityClass=new AttendanceEntity();
                    entityClass.setName(activity.register.getName());
                    entityClass.setPlatoon(activity.register.getPlatoon());
                    entityClass.setRegimental_number(activity.register.getRegimental_number());
                    int number=Integer.parseInt(activity.register.getPass_out_year());
                    number-=3;
                    DatabaseReference myref2=FirebaseDatabase.getInstance().getReference("Attendance Data/"+number);
                    String id2=myref2.push().getKey();
                    myref2.child(id2).setValue(entityClass);
                    Toast.makeText(getActivity(),"Cadet registered",Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }
}
