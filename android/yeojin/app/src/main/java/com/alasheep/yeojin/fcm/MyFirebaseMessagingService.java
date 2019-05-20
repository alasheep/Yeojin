package com.alasheep.yeojin.fcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.alasheep.yeojin.firestore.CurLocation;
import com.alasheep.yeojin.firestore.Token;
import com.alasheep.yeojin.gps.GpsTracker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "soongil";
    private GpsTracker mGpsTracker;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 11:
                    mGpsTracker = new GpsTracker(getApplicationContext());

                    double latitude = mGpsTracker.getLatitude();
                    double longtitude = mGpsTracker.getLongitude();

                    Log.e(TAG, "latitude : " + latitude + ", longtitude : " + longtitude);

                    callFireStore(latitude, longtitude);

                    break;
            }
        }
    };

    @Override
    public void onNewToken(String refreshedToken) {
//        super.onNewToken(s);
        Log.e("NEW_TOKEN",refreshedToken);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", refreshedToken);
        editor.commit();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if(remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload : " + remoteMessage.getData());
//            Toast.makeText(this, "message : "+remoteMessage.getData().toString() , Toast.LENGTH_LONG).show();

            mHandler.sendEmptyMessage(11);
        }

    }

    private void callFireStore(double latitude, double longtitude) {

        FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();

        DocumentReference doc = mFireStore.collection("cur_location").document("lLxQJ1cOQv5nU3HNNSSm");

        CurLocation mLocation = new CurLocation(String.valueOf(latitude), String.valueOf(longtitude));

        doc.set(mLocation).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e(TAG, "success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "fail");
            }
        });

    }
}