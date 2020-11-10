package com.example.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavView;
    private boolean pressedTwice = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavView = findViewById(R.id.bottomNavView);
        bottomNavView.setSelectedItemId(R.id.discover);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new DiscoverFragment()).commit();

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.movies:
                        fragment = new MoviesFragment();
                        break;
                    case R.id.discover:
                        fragment = new DiscoverFragment();
                        break;
                    case R.id.profile:
                        fragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).commit();

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (pressedTwice) {
            super.onBackPressed();
        }

        this.pressedTwice = true;

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                pressedTwice=false;
            }
        }, 2000);
    }
}