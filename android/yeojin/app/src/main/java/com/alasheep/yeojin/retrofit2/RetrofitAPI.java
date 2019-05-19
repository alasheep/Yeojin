package com.alasheep.yeojin.retrofit2;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitAPI {

    String URL = "https://us-central1-api-project-1028360976451.cloudfunctions.net/";

    @GET("/reqInfo")
    Call<String> reqInfo();

//    @POST("/saveToken")
//    Call<Token> saveToken(@Body Token token);

//    @FormUrlEncoded
//    @POST("/saveToken")
//    Call<String> saveToken(@FieldMap HashMap<String, Object> parameters);

    @FormUrlEncoded
    @POST("/saveToken")
    Call<String> saveToken(@Field("token") String token);
}