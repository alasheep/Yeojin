package com.alasheep.yeojin.fcm;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "soongil";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if(remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload : " + remoteMessage.getData());
            Toast.makeText(this, "message : "+remoteMessage.getData().toString() , Toast.LENGTH_LONG).show();
        }

    }
}