package com.alasheep.yeojin.retrofit2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token {

//    @SerializedName("token")
    @Expose
    public String token;

    public Token(String value) {
        this.token = value;
    }
}

