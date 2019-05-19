package com.alasheep.yeojin.fcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.alasheep.yeojin.firestore.CurLocation;
import com.alasheep.yeojin.firestore.Token;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "soongil";

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

//            callFireStore(11.111, 22.222);
            try {
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();

                callFireStore(latitude, longitude);
            }
            catch (Exception e) {
                Log.d(TAG, "error");
            }
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