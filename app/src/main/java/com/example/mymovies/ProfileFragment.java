package com.example.mymovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {


    TextView tvFirstname, tvLastname,tvEmail,tvOrigin;
    ImageView ivProfile;
    Button btnEditProfile;
    User user;

    private FirebaseDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        tvFirstname = view.findViewById(R.id.tvFirstname);
        tvLastname = view.findViewById(R.id.tvLastname);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvOrigin = view.findViewById(R.id.tvOriginProfile);
        ivProfile = view.findViewById(R.id.ivProfile);

        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        final RatingBar rateApplication=view.findViewById(R.id.rateApplication);
        final TextView tvApplicationRating=view.findViewById(R.id.tvApplicationRating);
        rateApplication.setNumStars(5);

        final SharedPreferences settingsFile = this.getActivity().getSharedPreferences("prefs", 0);
        String rating=settingsFile.getString("rating",null);
        if (rating==null){
            rateApplication.setRating(0);
            tvApplicationRating.setText("Rate this app");

        }else{
            tvApplicationRating.setText("Your rating on this app: "+rating+"/5");
            rateApplication.setRating(Float.parseFloat(rating));
        }

        rateApplication.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                SharedPreferences.Editor myEditor = settingsFile.edit();
                myEditor.putString("rating", String.valueOf(rateApplication.getRating()));
                myEditor.apply();
                rateApplication.setRating(rating);
                tvApplicationRating.setText("Your rating on this app: "+rating+"/5");

            }
        });
        String firstAuthentication=settingsFile.getString("authentication","null");
        TextView tvfirstAuthentication=view.findViewById(R.id.tvFirstAuthentication);
        tvfirstAuthentication.setText("First authentication on: "+firstAuthentication);

        database = FirebaseDatabase.getInstance();

        SharedPreferences mySettings = this.getActivity().getSharedPreferences("prefs", 0);
        final String email = mySettings.getString("email", null);

        final DatabaseReference myRef = database.getReference("MyMovies");
        myRef.keepSynced(true);

        myRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dn : snapshot.getChildren()){
                        final User user = dn.getValue(User.class);
                        if(user.getEmail().equals(email)){
                            tvFirstname.setText(user.getFirstname());
                            tvLastname.setText(user.getLastname());
                            tvEmail.setText(user.getEmail());
                            tvOrigin.setText(user.getOrigin());
                            if (user.getGender().equals("Male")) {
                                ivProfile.setImageResource(R.drawable.boy);
                            } else {
                                ivProfile.setImageResource(R.drawable.girl);
                            }
                            btnEditProfile.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity().getApplicationContext(), EditProfileActivity.class);
                                    intent.putExtra("user",user);
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;

    }


}