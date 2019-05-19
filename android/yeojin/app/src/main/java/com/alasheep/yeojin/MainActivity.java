package com.alasheep.yeojin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.alasheep.yeojin.firestore.Token;
import com.alasheep.yeojin.retrofit2.RetrofitAPI;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "soongil";

    private Context mContext;

    String storedToken = "";

    private Retrofit mRetrofit;
    private RetrofitAPI mRetrofitAPI;

    private FirebaseFirestore mFireStore;

//    private Call<String> mCallFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        checkFcmToken();

        setRetrofitInit();

        if(storedToken!=null && !storedToken.isEmpty()) {
//            callSaveToken();
//            callCloudFunction();
            callFireStore();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void checkFcmToken() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        storedToken = preferences.getString("token", null);
        if(storedToken!=null) {
            Log.e(TAG, "stored token : " + storedToken);
            Toast.makeText(this, "stored token : "+ storedToken, Toast.LENGTH_LONG).show();
        }
    }

    private Task<String> callCloudFunction() {
        FirebaseFunctions mFunctions = FirebaseFunctions.getInstance();
        Map<String,Object> data = new HashMap<>();
        data.put("token",storedToken);

        return mFunctions.getHttpsCallable("updateToken").call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        return (String)task.getResult().getData();
                    }
                });
    }

    private void callFireStore() {
        mFireStore = FirebaseFirestore.getInstance();

        DocumentReference doc = mFireStore.collection("push_token").document("yAmpQQ271chZWM82LtI2");
        Token mToken = new Token(storedToken);
        doc.set(mToken).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void setRetrofitInit() {

        mRetrofit = new Retrofit.Builder()
                .baseUrl(RetrofitAPI.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mRetrofitAPI = mRetrofit.create(RetrofitAPI.class);
    }

    private void callSaveToken() {

//        Token token = new Token(storedToken);
//        Call<Token> call1 = mRetrofitAPI.saveToken(token);
//        call1.enqueue(new Callback<Token>() {
//            @Override
//            public void onResponse(Call<Token> call, Response<Token> response) {
//
////                Toast.makeText(mContext,"api call success", Toast.LENGTH_LONG).show();;
//            }
//
//            @Override
//            public void onFailure(Call<Token> call, Throwable t) {
//                call.cancel();
//
////                Toast.makeText(mContext,"api call fail", Toast.LENGTH_LONG).show();;
//
//            }
//        });

//        HashMap<String,Object> tokens = new HashMap<>();
//        tokens.put("fcm_device_token",storedToken);
//
//        mRetrofitAPI.saveToken(tokens).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//
////                Toast.makeText(mContext,"api call success", Toast.LENGTH_LONG).show();;
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                call.cancel();
//
////                Toast.makeText(mContext,"api call fail", Toast.LENGTH_LONG).show();;
//
//            }
//        });

        mRetrofitAPI.saveToken(storedToken).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

//                Toast.makeText(mContext,"api call success", Toast.LENGTH_LONG).show();;
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                call.cancel();

//                Toast.makeText(mContext,"api call fail", Toast.LENGTH_LONG).show();;

            }
        });
    }



//    private Callback<String> mRetrofitCallback = new Callback<String>() {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String result = response.body();
//            Log.d(TAG, result);
//
//            Toast.makeText(mContext, "api result :" + result, Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            t.printStackTrace();
//
//        }
//    };
}
