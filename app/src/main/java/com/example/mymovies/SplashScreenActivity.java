package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences settingsFile = getSharedPreferences("prefs", 0);
        final String email=settingsFile.getString("email",null);

        if (email!=null){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }else {
            Intent intent=new Intent(getApplicationContext(),LogInActivity.class);
            startActivity(intent);
        }

        finish();
    }
}