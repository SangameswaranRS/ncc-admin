package com.example.sangameswaran.nccarmy.FragmentsAndAdapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.sangameswaran.nccarmy.Entities.MessageEntity;
import com.example.sangameswaran.nccarmy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sangameswaran on 03-07-2017.
 */

public class ChatFragment extends Fragment{
    RecyclerView messageRecyclerView;
    RecyclerView.Adapter messageAdapter;
    RecyclerView.LayoutManager manager;
    EditText messageTyper;
    de.hdodenhof.circleimageview.CircleImageView circleImageView;
    ArrayList<MessageEntity> messages=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.chat_fragment_layout,container,false);
        messageRecyclerView=(RecyclerView) view.findViewById(R.id.rvMessage);
        manager=new LinearLayoutManager(getActivity());
        messageTyper=(EditText)view.findViewById(R.id.etMessage);
        circleImageView= (CircleImageView) view.findViewById(R.id.drawSend);
        DatabaseReference getMessages=FirebaseDatabase.getInstance().getReference("MESSAGES");
        getMessages.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        MessageEntity entity=ds.getValue(MessageEntity.class);
                        messages.add(entity);
                        messageAdapter=new MessageRecyclerViewAdapter(messages,getActivity());
                        messageAdapter.notifyDataSetChanged();
                        messageRecyclerView.setAdapter(messageAdapter);
                        messageRecyclerView.setLayoutManager(manager);
                    }
                    messageAdapter=new MessageRecyclerViewAdapter(messages,getActivity());
                    messageAdapter.notifyDataSetChanged();
                    messageRecyclerView.setAdapter(messageAdapter);
                    messageRecyclerView.setLayoutManager(manager);
                    messageRecyclerView.scrollToPosition(messages.size()-1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(messageTyper.getText().toString().equals("")){

                }else {
                    SharedPreferences sp=getActivity().getSharedPreferences("LoginCredentials", Context.MODE_PRIVATE);
                    String user=sp.getString("MyloginID","throwNewRuntimeException");
                    String message=messageTyper.getText().toString();
                    DatabaseReference sendmessage= FirebaseDatabase.getInstance().getReference("MESSAGES");
                    String hashKey=sendmessage.push().getKey();
                    Calendar calendar=Calendar.getInstance();
                    String time=calendar.get(Calendar.DATE)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR)+" "+calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE);
                    sendmessage.child(hashKey).setValue(new MessageEntity(message,user,time));
                }
            }
        });
        return view;
    }
}
