package com.example.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ProfileFragment profile;
    private MoviesFragment movies;
    private DiscoverFragment discover;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavView = findViewById(R.id.bottomNavView);
        bottomNavView.setSelectedItemId(R.id.discover);

        movies = new MoviesFragment();
        discover = new DiscoverFragment();
        profile = new ProfileFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, discover).commit();

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragment = null;

                switch (item.getItemId()){
                    case R.id.movies:
                        fragment = movies;
                        break;
                    case R.id.discover:
                        fragment = discover;
                        break;
                    case R.id.profile:
                        fragment = profile;
                        break;
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainFrame, fragment)
                        .commit();

                return true;
            }
        });
    }
}