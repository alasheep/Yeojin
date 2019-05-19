package com.alasheep.yeojin.firestore;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class CurLocation {

    public String cur_latitude;
    public String cur_longtitude;


    public CurLocation(String la, String lo) {
        this.cur_latitude = la;
        this.cur_longtitude = lo;
    }
}
