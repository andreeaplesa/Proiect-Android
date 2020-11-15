package com.example.mymovies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class EditProfileActivity extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN=Pattern.compile("^"+"(?=.*[0-9])"+"(?=.*[a-z])"+"(?=.*[A-Z])"+".{4,}"+"$");
   public static final String EDIT_USER_ACT= "editUserAct";
    TextInputLayout textInputLayoutFirstnameEdit, textInputLayoutLastnameEdit,textInputLayoutPasswordEdit,textInputLayoutConfirmPasswordEdit;
    RadioButton genderEdit;
    RadioGroup radioGroup;
    Spinner originEdit;
    Button btnEditProfileAct;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        final Intent intent=getIntent();

        if (intent.hasExtra(ProfileFragment.EDIT_USER)){
            user=(User)intent.getSerializableExtra(ProfileFragment.EDIT_USER);
        textInputLayoutFirstnameEdit=findViewById(R.id.textInputLayoutFirstnameEdit);
        textInputLayoutFirstnameEdit.getEditText().setText(user.getFirstname());

        textInputLayoutLastnameEdit=findViewById(R.id.textInputLayoutLastnameEdit);
        textInputLayoutLastnameEdit.getEditText().setText(user.getLastname());

        if (user.getGender().equals("Male")){
            genderEdit=findViewById(R.id.radioBtnMaleEdit);
            genderEdit.setChecked(true);
        }else{
            genderEdit=findViewById(R.id.radioBtnFemaleEdit);
            genderEdit.setChecked(true);
        }

        originEdit=findViewById(R.id.spinnerOriginEdit);

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.countries_array, android.R.layout.simple_spinner_dropdown_item);
        originEdit.setAdapter(adapter);
        adapter = (ArrayAdapter<CharSequence>) originEdit.getAdapter();
        int position = adapter.getPosition(user.getOrigin());
        originEdit.setSelection(position);


        textInputLayoutPasswordEdit=findViewById(R.id.textInputLayoutPasswordEdit);
        textInputLayoutPasswordEdit.getEditText().setText(user.getPassword());

        textInputLayoutConfirmPasswordEdit=findViewById(R.id.textInputLayoutConfirmPassEdit);
        textInputLayoutConfirmPasswordEdit.getEditText().setText(user.getPassword());

        btnEditProfileAct=findViewById(R.id.btnEditProfileAct);
        btnEditProfileAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFirstname() & validateLastname() &validatePassword() &validateConfirmPassword()){
                    radioGroup=findViewById(R.id.radioBtnGroupEdit);
                    RadioButton radioButtonSelected = findViewById(radioGroup.getCheckedRadioButtonId());
                    User user2=new User(user.getEmail(),textInputLayoutPasswordEdit.getEditText().getText().toString(),textInputLayoutFirstnameEdit.getEditText().getText().toString(),textInputLayoutLastnameEdit.getEditText().getText().toString(),radioButtonSelected.getText().toString(),originEdit.getSelectedItem().toString());
                    intent.putExtra(EDIT_USER_ACT,user2);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        }


    }

    private boolean validateFirstname() {
        textInputLayoutFirstnameEdit=findViewById(R.id.textInputLayoutFirstnameEdit);
        String firstname=textInputLayoutFirstnameEdit.getEditText().getText().toString().trim();

        if (firstname.isEmpty()){
            textInputLayoutFirstnameEdit.setError("Field can't be empty!");
            return false;
        }else
        {textInputLayoutFirstnameEdit.setError(null);
            return true;
        }
    }

    private boolean validateLastname() {
        textInputLayoutLastnameEdit=findViewById(R.id.textInputLayoutLastnameEdit);
        String lastname=textInputLayoutLastnameEdit.getEditText().getText().toString().trim();

        if (lastname.isEmpty()){
            textInputLayoutLastnameEdit.setError("Field can't be empty!");
            return false;
        }else
        {textInputLayoutLastnameEdit.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        textInputLayoutPasswordEdit=findViewById(R.id.textInputLayoutPasswordEdit);
        String password = textInputLayoutPasswordEdit.getEditText().getText().toString().trim();
        if (password.isEmpty()){
            textInputLayoutPasswordEdit.setError("Field can't be empty!");
            return false;}
        else if(!PASSWORD_PATTERN.matcher(password).matches()){
            textInputLayoutPasswordEdit.setError("Password too week!");
            return false;
        }else{
            textInputLayoutPasswordEdit.setError(null);
            return true;
        }
    }
    private boolean validateConfirmPassword() {
        textInputLayoutConfirmPasswordEdit = findViewById(R.id.textInputLayoutConfirmPassEdit);
        String confirmPass = textInputLayoutConfirmPasswordEdit.getEditText().getText().toString().trim();

        textInputLayoutPasswordEdit = findViewById(R.id.textInputLayoutPasswordEdit);
        String password = textInputLayoutPasswordEdit.getEditText().getText().toString().trim();
        if (confirmPass.isEmpty()) {
            textInputLayoutConfirmPasswordEdit.setError("Field can't be empty!");
            return false;
        } else if (!confirmPass.equals(password)) {
            textInputLayoutConfirmPasswordEdit.setError("Password doesn't match!");
            return false;
        } else {
            textInputLayoutConfirmPasswordEdit.setError(null);
            return true;
        }
    }

}