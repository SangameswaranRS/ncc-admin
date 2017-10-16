package com.example.sangameswaran.nccarmy.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.sangameswaran.nccarmy.Entities.ParadeDeclarationEntity;
import com.example.sangameswaran.nccarmy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

/**
 * Created by Sangameswaran on 27-08-2017.
 */

public class ParadeTimeNotificationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final Handler checkForNotification=new Handler();
        Runnable iterateInstructions=new Runnable() {
            @Override
            public void run() {
                if(isNetworkAvailable(ParadeTimeNotificationService.this)) {
                    final NotificationManager NM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    DatabaseReference getParadeDetails= FirebaseDatabase.getInstance().getReference("ParadeDetails");
                    getParadeDetails.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChildren()){
                                try {
                                    ParadeDeclarationEntity entity = dataSnapshot.getValue(ParadeDeclarationEntity.class);
                                    String date = entity.getDate();
                                    String time = entity.getTime();
                                    time=time.trim();
                                    String dateParts[]=date.split("/");
                                    String timeParts[]=time.split(":");
                                    int year,month,dateInt;
                                    year=Integer.parseInt(dateParts[0]);
                                    month=Integer.parseInt(dateParts[1]);
                                    dateInt=Integer.parseInt(dateParts[2]);
                                    int hours,mins;
                                    hours=Integer.parseInt(timeParts[0].trim());
                                    mins=Integer.parseInt(timeParts[1].trim());
                                    Calendar calendar=Calendar.getInstance();
                                    int actualDateYear,actualDateMonth,actualDatedate;
                                    actualDateYear=calendar.get(Calendar.YEAR);
                                    actualDateMonth=calendar.get(Calendar.MONTH);
                                    //actualDateMonth+=1;
                                    actualDatedate=calendar.get(Calendar.DATE);
                                    int actualHours,actualMins;
                                    actualHours=calendar.get(Calendar.HOUR_OF_DAY);
                                    actualMins=calendar.get(Calendar.MINUTE);
                                    if(actualDateYear<=year){
                                        if(actualDatedate>dateInt&&actualDateMonth<month){
                                            //validated date
                                            if(hours>=actualHours){
                                                //validated Time
                                                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + entity.getLatitude() + "," + entity.getLongitude() + "&avoid=t");
                                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                                mapIntent.setPackage("com.google.android.apps.maps");
                                                PendingIntent piDismiss = PendingIntent.getService(ParadeTimeNotificationService.this, 0, mapIntent, 0);
                                                Notification notification = new Notification.Builder(ParadeTimeNotificationService.this)
                                                        .setContentTitle("Parade Reminder").setStyle(new Notification.BigTextStyle().bigText("you have parade at \n Latitude: "+entity.getLatitude()+"\n Longitude: "+entity.getLongitude()+"\n DressCode :"+entity.getDress_code()+"\n Date : "+entity.getDate()+ "\n Time : "+entity.getTime())).setContentText("Get ready cadet").setSmallIcon(R.drawable.date_icon).addAction(R.drawable.parade_icon,"Navigate",piDismiss).build();
                                                NM.notify(0, notification);
                                            }
                                        }else if(actualDatedate<=dateInt&&actualDateMonth<=month){
                                            //validatedDate
                                            if(hours>=actualHours){
                                                //validated Time
                                                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + entity.getLatitude() + "," + entity.getLongitude() + "&avoid=t");
                                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                                mapIntent.setPackage("com.google.android.apps.maps");
                                                PendingIntent piDismiss = PendingIntent.getService(ParadeTimeNotificationService.this, 0, mapIntent, 0);
                                                Notification notification = new Notification.Builder(ParadeTimeNotificationService.this)
                                                        .setContentTitle("Parade Reminder").setStyle(new Notification.BigTextStyle().bigText("you have parade at \n Latitude: "+entity.getLatitude()+"\n Longitude: "+entity.getLongitude()+"\n DressCode :"+entity.getDress_code()+"\n Date : "+entity.getDate()+ "\n Time : "+entity.getTime())).setContentText("Get ready cadet").setSmallIcon(R.drawable.date_icon).addAction(R.drawable.parade_icon,"Navigate",piDismiss).build();
                                                NM.notify(0, notification);
                                            }
                                        }
                                    }

                                }catch (Exception e){
                                    Log.d("Parse Error","Error in parsing DataSnapshot");
                                }
                            }else {
                                Log.d("NotService","Datasnapshot Empty");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    checkForNotification.postDelayed(this, 10000);
                }
            }
        };
        checkForNotification.postDelayed(iterateInstructions,1000);
    }
     boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
