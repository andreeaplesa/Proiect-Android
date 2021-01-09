package com.example.mymovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class EditProfileActivity extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN=Pattern.compile("^"+"(?=.*[0-9])"+"(?=.*[a-z])"+"(?=.*[A-Z])"+".{4,}"+"$");
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
        final Intent intent = getIntent();

        if (intent.hasExtra("user")) {
            user = (User) intent.getSerializableExtra("user");
            textInputLayoutFirstnameEdit = findViewById(R.id.textInputLayoutFirstnameEdit);
            textInputLayoutFirstnameEdit.getEditText().setText(user.getFirstname());

            textInputLayoutLastnameEdit = findViewById(R.id.textInputLayoutLastnameEdit);
            textInputLayoutLastnameEdit.getEditText().setText(user.getLastname());

            if (user.getGender().equals("Male")) {
                genderEdit = findViewById(R.id.radioBtnMaleEdit);
                genderEdit.setChecked(true);
            } else {
                genderEdit = findViewById(R.id.radioBtnFemaleEdit);
                genderEdit.setChecked(true);
            }

            originEdit = findViewById(R.id.spinnerOriginEdit);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.countries_array, android.R.layout.simple_spinner_dropdown_item);
            originEdit.setAdapter(adapter);
            adapter = (ArrayAdapter<CharSequence>) originEdit.getAdapter();
            int position = adapter.getPosition(user.getOrigin());
            originEdit.setSelection(position);


            textInputLayoutPasswordEdit = findViewById(R.id.textInputLayoutPasswordEdit);
            textInputLayoutPasswordEdit.getEditText().setText(user.getPassword());

            textInputLayoutConfirmPasswordEdit = findViewById(R.id.textInputLayoutConfirmPassEdit);
            textInputLayoutConfirmPasswordEdit.getEditText().setText(user.getPassword());

            btnEditProfileAct = findViewById(R.id.btnEditProfileAct);
            btnEditProfileAct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validateFirstname() & validateLastname() & validatePassword() & validateConfirmPassword()) {
                        radioGroup = findViewById(R.id.radioBtnGroupEdit);
                        final RadioButton radioButtonSelected = findViewById(radioGroup.getCheckedRadioButtonId());

                        SharedPreferences mySettings = getSharedPreferences("prefs", 0);
                        final String email = mySettings.getString("email", null);

                        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("MyMovies");
                        myRef.keepSynced(true);
                        myRef.child("Users").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dn : snapshot.getChildren()) {
                                    final User user = dn.getValue(User.class);
                                    if (user.getEmail().equals(email)) {
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
                                        Map<String, Object> updates = new HashMap<String, Object>();
                                        updates.put("password", textInputLayoutPasswordEdit.getEditText().getText().toString());
                                        updates.put("firstname", textInputLayoutFirstnameEdit.getEditText().getText().toString());
                                        updates.put("lastname", textInputLayoutLastnameEdit.getEditText().getText().toString());
                                        updates.put("gender", radioButtonSelected.getText().toString());
                                        updates.put("origin", originEdit.getSelectedItem().toString());
                                        ref.updateChildren(updates);
                                    }
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }


                        });
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