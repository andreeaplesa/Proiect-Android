package com.example.mymovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavView;
    private ProfileFragment profile;
    private MoviesFragment movies;
    private DiscoverFragment discover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavView = findViewById(R.id.bottomNavView);
        bottomNavView.setSelectedItemId(R.id.discover);

        //initializare fragmente

        movies=new MoviesFragment();
        discover=new DiscoverFragment();
        profile=new ProfileFragment();

        //transfer SignUp->FragmentProfile
        if(getIntent().hasExtra(SignUpActivity.ADD_USER)){
            User user= (User)getIntent().getSerializableExtra(SignUpActivity.ADD_USER);
            Toast.makeText(getApplicationContext(),user.getFirstname()+" "+user.getLastname()+" "
                    +user.getGender()+" "+user.getEmail()+" "+user.getOrigin()+" "+user.getPassword(),Toast.LENGTH_LONG).show();
            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();

            Bundle bundle=new Bundle();
            bundle.putSerializable("user_key",user);
            profile.setArguments(bundle);
            // !!!
            //ft.replace(R.id.mainFrame, profile);
            // !!!
            ft.commit();
        }

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