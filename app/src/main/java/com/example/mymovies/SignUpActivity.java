package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN=Pattern.compile("^"+"(?=.*[0-9])"+"(?=.*[a-z])"+"(?=.*[A-Z])"+"(?=.*[@#$%^&+=])"+".{4,}"+"$");
    public enum Origin
    {
        ASIA, AFRICA, EUROPE, AMERICA, AUSTRALIA;
    };


    private Button btnSignUp;
    private CheckBox chkboxTermsConditions;
    TextInputLayout  textInputLayoutFirstname, textInputLayoutLastname,textInputLayoutEmailSignUp,textInputLayoutPasswordSignUp,textInputLayoutConfirmPassSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        final Spinner spinnerOrigin = findViewById(R.id.spinnerOrigin);

        String[] continents = new String[Origin.values().length];
        int i = 0;
        for(Origin origin: Origin.values()){
            continents[i++] = origin.toString();
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,continents);
        spinnerOrigin.setAdapter(adapter);
        spinnerOrigin.setSelection(0);

        final RadioButton radioBtnFemale=findViewById(R.id.radioBtnFemale);
        radioBtnFemale.setChecked(true);

        btnSignUp = findViewById(R.id.btnSignUpS);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFirstname() | !validateLastname() | !validateEmail() | !validatePassword() | !validateConfirmPassword() | !validateTermsConditions()){
                    return;
                }else{
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);}
            }
        });

        final TextView termsConditions =findViewById(R.id.tvTermsConditions);
        termsConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                builder.setTitle("Terms and Conditions");
                final View view = getLayoutInflater().inflate(R.layout.terms_conditions_layout, null);
                builder.setView(view);
                builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       chkboxTermsConditions=findViewById(R.id.chkboxTermsConditions);
                               chkboxTermsConditions.setChecked(true);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });
    }



    private boolean validateFirstname() {
        textInputLayoutFirstname=findViewById(R.id.textInputLayoutFirstname);
        String firstname=textInputLayoutFirstname.getEditText().getText().toString().trim();

        if (firstname.isEmpty()){
            textInputLayoutFirstname.setError("Field can't be empty!");
            return false;
        }else
            {textInputLayoutFirstname.setError(null);
            return true;
        }
    }

    private boolean validateLastname() {
        textInputLayoutLastname=findViewById(R.id.textInputLayoutLastname);
        String lastname=textInputLayoutLastname.getEditText().getText().toString().trim();

        if (lastname.isEmpty()){
            textInputLayoutLastname.setError("Field can't be empty!");
            return false;
        }else
        {textInputLayoutLastname.setError(null);
            return true;
        }
    }
    private boolean validateEmail(){
        textInputLayoutEmailSignUp=findViewById(R.id.textInputLayoutEmailSignUp);
        String email=textInputLayoutEmailSignUp.getEditText().getText().toString().trim();

        if (email.isEmpty())
        {textInputLayoutEmailSignUp.setError("Field can't be empty!");
            return false;
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {textInputLayoutEmailSignUp.setError("Please enter a valid email address!");
            return false;
        }
        else {textInputLayoutEmailSignUp.setError(null);
            return true;}

    }

    private boolean validatePassword(){
        textInputLayoutPasswordSignUp=findViewById(R.id.textInputLayoutPasswordSignUp);
        String password = textInputLayoutPasswordSignUp.getEditText().getText().toString().trim();
        if (password.isEmpty()){
            textInputLayoutPasswordSignUp.setError("Field can't be empty!");
            return false;}
        else if(!PASSWORD_PATTERN.matcher(password).matches()){
            textInputLayoutPasswordSignUp.setError("Password too week!");
            return false;
        }else{
            textInputLayoutPasswordSignUp.setError(null);
            return true;
        }
    }
    private boolean validateConfirmPassword() {
        textInputLayoutConfirmPassSignUp = findViewById(R.id.textInputLayoutConfirmPassSignUp);
        String confirmPass = textInputLayoutConfirmPassSignUp.getEditText().getText().toString().trim();

        textInputLayoutPasswordSignUp = findViewById(R.id.textInputLayoutPasswordSignUp);
        String password = textInputLayoutPasswordSignUp.getEditText().getText().toString().trim();
        if (confirmPass.isEmpty()) {
            textInputLayoutConfirmPassSignUp.setError("Field can't be empty!");
            return false;
        } else if (confirmPass != password) {
            textInputLayoutConfirmPassSignUp.setError("Password doesn't match!");
            return false;
        } else {
            textInputLayoutConfirmPassSignUp.setError(null);
            return true;
        }
    }

    private boolean validateTermsConditions() {
        chkboxTermsConditions = findViewById(R.id.chkboxTermsConditions);
        final TextView termsConditions=findViewById(R.id.tvTermsConditions);
        if (!chkboxTermsConditions.isChecked()) {

            termsConditions.setError("You must accept our Terms and Conditions!");
            return false;
        } else {

            termsConditions.setError(null);
            return true;
        }


    }

}