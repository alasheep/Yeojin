package com.alasheep.yeojin.game.flappybird;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alasheep.yeojin.R;

public class FlappyBirdMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.flappybird_activity_main);
    }

    public void startGame(View view) {

    }
}