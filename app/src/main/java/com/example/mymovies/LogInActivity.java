package com.example.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity {
    boolean foundEmail=false;
    boolean foundPassword=false;

    Button btnLogIn,btnSignUp;
    TextInputLayout textInputLayoutEmail,textInputLayoutPassword;

    private FirebaseDatabase database;

    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("MyMovies");
        myRef.keepSynced(true);

        btnLogIn=findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateEmail(myRef) & validatePassword(myRef)){
                    textInputLayoutEmail=findViewById(R.id.textInputLayoutEmail);
                    final String emailText =textInputLayoutEmail.getEditText().getText().toString().trim();

                    database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("MyMovies");
                    myRef.keepSynced(true);

                    myRef.child("Users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot dn : snapshot.getChildren()) {
                                    User user = dn.getValue(User.class);
                                    if (user.getEmail().equals(emailText)) {
                                        uid = user.getUid();
                                    }
                                }
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    final MovieDB movieDB = MovieDB.getInstanta(getApplicationContext());

                    myRef.child("UsersWithMovies").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for(DataSnapshot dn : snapshot.getChildren()){
                                    if(dn.getKey().equals(uid)){
                                        ExtractMovie extractMovie = new ExtractMovie((Long) dn.getValue(), movieDB,true);

                                        extractMovie.execute();
                                    }
                                }
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    SharedPreferences settingsFile = getSharedPreferences("prefs", 0);
                    SharedPreferences.Editor myEditor = settingsFile.edit();

                    myEditor.putString("email", emailText);
                    myEditor.apply();
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnSignUp=findViewById(R.id.btnSignUpL);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateEmail(DatabaseReference myRef){
        textInputLayoutEmail=findViewById(R.id.textInputLayoutEmail);
        final String email=textInputLayoutEmail.getEditText().getText().toString().trim();

        if (email.isEmpty())
        {textInputLayoutEmail.setError("Field can't be empty!");
            return false;
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {textInputLayoutEmail.setError("Please enter a valid email address!");
            return false;
        }
        else {
            myRef.child("Users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot dn : snapshot.getChildren()) {
                            User user = dn.getValue(User.class);
                            if (user.getEmail().equals(email)) {
                                foundEmail = true;
                            } else {
                                foundEmail = false;
                            }
                        }
                    }

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            if (foundEmail) {
                textInputLayoutEmail.setError(null);
                return true;
            } else {
                textInputLayoutEmail.setError("This email doesn't exist!");
                return false;
            }

        }
    }

    private boolean validatePassword(DatabaseReference myRef){
        textInputLayoutPassword=findViewById(R.id.textInputLayoutPassword);
        final String password = textInputLayoutPassword.getEditText().getText().toString().trim();
        if (password.isEmpty()){
            textInputLayoutPassword.setError("Field can't be empty!");
            return false;}
        else {
            myRef.child("Users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot dn : snapshot.getChildren()) {
                            User user = dn.getValue(User.class);
                            if (user.getPassword().equals(password)) {
                                foundPassword = true;
                            } else {
                                foundPassword = false;
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            if (foundPassword) {
                textInputLayoutPassword.setError(null);
                return true;
            } else {
                textInputLayoutPassword.setError("Password incorrect!");
                return false;
            }

        }
    }
}