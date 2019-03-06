package com.example.authandregist;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class User {


    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email) {
        this.email = email;
    }

}
