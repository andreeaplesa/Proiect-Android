package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class LogInActivity extends AppCompatActivity {

    Button btnLogIn,btnSignUp;
    TextInputLayout textInputLayoutEmail,textInputLayoutPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        btnLogIn=findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEmail() & validatePassword()){
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
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