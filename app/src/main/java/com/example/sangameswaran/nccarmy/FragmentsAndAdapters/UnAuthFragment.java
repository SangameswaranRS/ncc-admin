package com.example.sangameswaran.nccarmy.FragmentsAndAdapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.sangameswaran.nccarmy.R;

/**
 * Created by Sangameswaran on 21-06-2017.
 */

public class UnAuthFragment extends Fragment {
    Button callBtn;
    AlertDialog.Builder builder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.unauth_fragment,container,false);
        callBtn=(Button)v.findViewById(R.id.callBtn);
        builder=new AlertDialog.Builder(getActivity());
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Confirmation").setMessage("Are you sure to call DBA?").setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Intent callIntent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:9677947957"));
                            startActivity(callIntent);
                        }catch (Exception e){
                            Toast.makeText(getActivity(),"Error in making phone call",Toast.LENGTH_LONG).show();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
            }
        });
        return v;
    }
}
