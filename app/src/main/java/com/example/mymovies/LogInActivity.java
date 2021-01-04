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

    Button btnLogIn,btnSignUp;
    TextInputLayout textInputLayoutEmail,textInputLayoutPassword;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // !! Asa raman logat
//        SharedPreferences mySettings = getSharedPreferences("prefs", 0);
//        final String email = mySettings.getString("email", null);
//        if(email != null){
//            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
//            startActivity(intent);
//        }

        database = FirebaseDatabase.getInstance();

        btnLogIn=findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEmail() & validatePassword()){
                    textInputLayoutEmail=findViewById(R.id.textInputLayoutEmail);
                    final String emailText =textInputLayoutEmail.getEditText().getText().toString().trim();

                    final DatabaseReference myRef = database.getReference("users");
                    myRef.keepSynced(true);

                    myRef.child("users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()) {
                                for (DataSnapshot dn : snapshot.getChildren()) {
                                    User user = dn.getValue(User.class);
                                    if(user.getEmail().equals(emailText)){
                                        SharedPreferences settingsFile = getSharedPreferences("prefs", 0);
                                        SharedPreferences.Editor myEditor = settingsFile.edit();

                                        myEditor.putString("email", emailText);
                                        myEditor.apply();

                                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
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

    private boolean validateEmail(){
        textInputLayoutEmail=findViewById(R.id.textInputLayoutEmail);
        String email=textInputLayoutEmail.getEditText().getText().toString().trim();

        if (email.isEmpty())
        {textInputLayoutEmail.setError("Field can't be empty!");
            return false;
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {textInputLayoutEmail.setError("Please enter a valid email address!");
            return false;
        }
        else {textInputLayoutEmail.setError(null);
            return true;}

    }

    private boolean validatePassword(){
        textInputLayoutPassword=findViewById(R.id.textInputLayoutPassword);
        String password = textInputLayoutPassword.getEditText().getText().toString().trim();
        if (password.isEmpty()){
            textInputLayoutPassword.setError("Field can't be empty!");
            return false;}
        else {
            textInputLayoutPassword.setError(null);
            return true;
        }
    }
}