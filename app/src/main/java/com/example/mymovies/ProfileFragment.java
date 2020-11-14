package com.example.mymovies;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.app.Activity.RESULT_OK;




public class ProfileFragment extends Fragment {
    public static final int REQUEST_CODE = 200;

    private static final String USER_KEY="user_key";
    TextView tvFirstname, tvLastname,tvEmail,tvOrigin;
    ImageView ivProfile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


      View view= inflater.inflate(R.layout.fragment_profile, container, false);
      if(!getArguments().isEmpty()) {

          User user = (User) getArguments().getSerializable(USER_KEY);
          tvFirstname = view.findViewById(R.id.tvFirstname);
          tvFirstname.setText(user.getFirstname());

          tvLastname = view.findViewById(R.id.tvLastname);
          tvLastname.setText(user.getLastname());

          tvEmail = view.findViewById(R.id.tvEmail);
          tvEmail.setText(user.getEmail());

          tvOrigin = view.findViewById(R.id.tvOriginProfile);
          tvOrigin.setText(user.getOrigin());

          if (user.getGender().equals("Male")) {
              ivProfile = view.findViewById(R.id.ivProfile);
              ivProfile.setImageResource(R.drawable.boy);
          } else {
              ivProfile = view.findViewById(R.id.ivProfile);
              ivProfile.setImageResource(R.drawable.girl);
          }

      }
        return view;

    }

}