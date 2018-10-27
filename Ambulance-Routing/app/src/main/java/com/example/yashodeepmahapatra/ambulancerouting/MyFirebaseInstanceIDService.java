package com.example.yashodeepmahapatra.ambulancerouting;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    //String token = FirebaseInstanceId.getInstance().getInstanceId().toString();
    public void onNewToken(String token) {
        Log.v("HAHA", token);
    }
}
