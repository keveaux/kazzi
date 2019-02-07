package com.example.kelvincb.ikazi;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import com.example.kelvincb.ikazi.Main.MainActivity;
import com.example.kelvincb.ikazi.UserLoginAndRegister.LoginRegisterActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

public class FCMMessagingService extends FirebaseMessagingService{

    String type="";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getData().size()>0){
            type="json";
            sendNotification(remoteMessage.getData().toString());
        }
        if(remoteMessage.getNotification() != null){
            type="message";
            sendNotification(remoteMessage.getNotification().getBody());
        }
        super.onMessageReceived(remoteMessage);
    }

    private void sendNotification(String messagebody) {

        String id="",message="",title="";
        if(type.equals("json")){
            try{
                JSONObject jsonObject=new JSONObject(messagebody);
                id=jsonObject.getString("id");
                message=jsonObject.getString("message");
                title=jsonObject.getString("title");



            }catch (Exception e){

            }


        }else if(type.equals("message")){
            message=messagebody;
        }

        Intent intent=new Intent(this,LoginRegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationbuilder=new NotificationCompat.Builder(this);
        notificationbuilder.setContentTitle(getString(R.string.app_name));
        notificationbuilder.setContentText(message);
        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        notificationbuilder.setSound(soundUri);
        notificationbuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationbuilder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.mipmap.ic_launcher));
        notificationbuilder.setAutoCancel(true);
        Vibrator v= (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(3000);
        notificationbuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationbuilder.build());


    }


}

