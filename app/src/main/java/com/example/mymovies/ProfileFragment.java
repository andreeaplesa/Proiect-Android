package com.example.mymovies;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    public static final int REQUEST_CODE = 200;
    public static final String EDIT_USER = "editUser";

    private static final String USER_KEY="user_key";
    TextView tvFirstname, tvLastname,tvEmail,tvOrigin;
    ImageView ivProfile;
    Button btnEditProfile;
    User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


      View view= inflater.inflate(R.layout.fragment_profile, container, false);

        if(getArguments()!=null) {
            tvFirstname = view.findViewById(R.id.tvFirstname);
            tvLastname = view.findViewById(R.id.tvLastname);
            tvEmail = view.findViewById(R.id.tvEmail);
            tvOrigin = view.findViewById(R.id.tvOriginProfile);
            ivProfile = view.findViewById(R.id.ivProfile);

            //populare fragment
            user = (User)getArguments().getSerializable(USER_KEY);

            tvFirstname.setText(user.getFirstname());
            tvLastname.setText(user.getLastname());
            tvEmail.setText(user.getEmail());
            tvOrigin.setText(user.getOrigin());
            if (user.getGender().equals("Male")) {
                ivProfile.setImageResource(R.drawable.boy);
            } else {
                ivProfile.setImageResource(R.drawable.girl);
            }
            btnEditProfile = view.findViewById(R.id.btnEditProfile);
            btnEditProfile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity().getApplicationContext(), EditProfileActivity.class);
                            intent.putExtra(EDIT_USER,user);
                            startActivityForResult(intent, REQUEST_CODE);

                        }
                    });

      }
        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==REQUEST_CODE && resultCode==RESULT_OK && data!=null)
        {
            user = (User)data.getSerializableExtra(EditProfileActivity.EDIT_USER_ACT);
            if(user!=null)
            {tvFirstname.setText(user.getFirstname());
            tvLastname.setText(user.getLastname());
            tvEmail.setText(user.getEmail());
            tvOrigin.setText(user.getOrigin());
                if (user.getGender().equals("Male")) {
                    ivProfile.setImageResource(R.drawable.boy);
                } else {
                    ivProfile.setImageResource(R.drawable.girl);
                }

            }
        }

    }
}