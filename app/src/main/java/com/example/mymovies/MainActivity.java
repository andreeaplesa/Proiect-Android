package com.example.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ProfileFragment profile;
    private MoviesFragment movies;
    private DiscoverFragment discover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavView = findViewById(R.id.bottomNavView);
        bottomNavView.setSelectedItemId(R.id.discover);

        //initializare fragmente

        movies=new MoviesFragment();
        discover=new DiscoverFragment();
        profile=new ProfileFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, discover).commit();

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.movies:
                        fragment = movies;
                        break;
                    case R.id.discover:
                        fragment = discover;
                        break;
                    case R.id.profile:
                        fragment =profile;
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