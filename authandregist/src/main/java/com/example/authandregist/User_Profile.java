package com.example.authandregist;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;


import com.example.authandregist.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_Profile extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "User_Profile";


    private DatabaseReference profileUserRef;
    private FirebaseAuth mAuth;
    private String currentUserId;

    TextView email;
    TextView name;
    TextView phone;
    TextView gender;
    TextView city;

    CircleImageView profileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        profileUserRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);

        email = findViewById(R.id.email);
        name = findViewById(R.id.username);
        phone = findViewById(R.id.phoneNumber);
        gender = findViewById(R.id.gender);
        city = findViewById(R.id.city);

        profileImage = findViewById(R.id.user_profile_image);

        profileUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String myProfileImage = dataSnapshot.child("profileimage").getValue().toString();
                    String myUserName = dataSnapshot.child("username").getValue().toString();
                    String myEmail = dataSnapshot.child("email").getValue().toString();
                    String myPhone = dataSnapshot.child("phone").getValue().toString();
                    String myGender = dataSnapshot.child("gender").getValue().toString();
                    String myCity = dataSnapshot.child("city").getValue().toString();

                    Picasso.with(User_Profile.this).load(myProfileImage).placeholder(R.drawable.user).into(profileImage);

                    name.setText("Name    " + myUserName);
                    email.setText("Email    " + myEmail);
                    phone.setText("Phone    " + myPhone);
                    gender.setText("Gender    " + myGender);
                    city.setText("City    " + myCity);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClick(View v) {

    }


}