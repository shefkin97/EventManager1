package com.example.authandregist;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class User {


    public String email;
    public String username;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }

}
