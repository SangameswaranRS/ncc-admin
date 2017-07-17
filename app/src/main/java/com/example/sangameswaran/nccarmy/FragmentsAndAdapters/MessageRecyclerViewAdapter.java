package com.example.sangameswaran.nccarmy.FragmentsAndAdapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sangameswaran.nccarmy.Entities.MessageEntity;
import com.example.sangameswaran.nccarmy.R;

import java.util.ArrayList;

/**
 * Created by Sangameswaran on 03-07-2017.
 */

public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter.MessageRecyclerViewHolder> {
    ArrayList<MessageEntity> messages=new ArrayList<>();
    Context ctx;

    MessageRecyclerViewAdapter(ArrayList<MessageEntity> messages,Context context){
        this.messages=messages;
        this.ctx=context;
    }

    @Override
    public MessageRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_card_static,parent,false);
        return new MessageRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MessageRecyclerViewHolder holder, int position) {
        SharedPreferences sp=ctx.getSharedPreferences("LoginCredentials",Context.MODE_PRIVATE);
        String user=sp.getString("MyloginID","throwNewRuntimeException");
        holder.message.setText(messages.get(position).getMessage());
        holder.sentTime.setText(messages.get(position).getTime());
        holder.sender.setText(messages.get(position).getSender());
        if (messages.get(position).getSender().equals(user)){
            holder.cview.setCardBackgroundColor(Color.parseColor("#ffffff"));
            holder.sender.setTextColor(Color.parseColor("#64a844"));
            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) holder.masterRl.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.masterRl.setLayoutParams(lp);
        }else {
            holder.cview.setCardBackgroundColor(Color.parseColor("#FFF2948F"));
            holder.sender.setTextColor(Color.parseColor("#ff0000"));
            holder.masterRl.setGravity(Gravity.START);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
    public class MessageRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView sender,message,sentTime;
        CardView cview;
        RelativeLayout masterRl;
        public MessageRecyclerViewHolder(View itemView) {
            super(itemView);
            sender=(TextView)itemView.findViewById(R.id.sender);
            message=(TextView)itemView.findViewById(R.id.tvChatMessage);
            sentTime=(TextView)itemView.findViewById(R.id.tvChatMessageTime);
            cview=(CardView)itemView.findViewById(R.id.cardContainer);
            masterRl=(RelativeLayout)itemView.findViewById(R.id.masterRl);
        }
    }
}
