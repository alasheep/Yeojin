package com.alasheep.yeojin.firestore;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Token {

    public String token;


    public Token(String value) {
        this.token = value;
    }
}
