package com.example.authandregist;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmailPassword extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;



    private TextInputLayout ETemail;
    private TextInputLayout ETpassword;
    private TextInputLayout ETusername;
    private Button Sign_in;
    private Button Regist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_password);

        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {


                } else {
                    // User is signed out

                }

            }
        };

        ETemail = findViewById(R.id.et_email);
        ETpassword = findViewById(R.id.et_password);
        ETusername = findViewById(R.id.et_username);
        Sign_in = findViewById(R.id.btn_sign_in);
        Regist = findViewById(R.id.btn_registration);

        ImageView image_View = findViewById(R.id.imageView);
        image_View.setImageResource(R.drawable.icon);

        findViewById(R.id.btn_sign_in).setOnClickListener(this);
        findViewById(R.id.btn_registration).setOnClickListener(this);

        ETemail.getEditText().addTextChangedListener(loginTextWatcher);
        ETpassword.getEditText().addTextChangedListener(loginTextWatcher);
        ETusername.getEditText().addTextChangedListener(loginTextWatcher);
    }


    private TextWatcher loginTextWatcher = new TextWatcher(){
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after){}


        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String email = ETemail.getEditText().getText().toString().trim();
            String password = ETpassword.getEditText().getText().toString().trim();
            String username = ETusername.getEditText().getText().toString().trim();

            Sign_in.setEnabled(!email.isEmpty() && !password.isEmpty() && !username.isEmpty());
            Regist.setEnabled(!email.isEmpty() && !password.isEmpty() && !username.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_sign_in)
        {
            signin(ETemail.getEditText().getText().toString(),ETpassword.getEditText().getText().toString());

        }else if (view.getId() == R.id.btn_registration)
        {
            registration(ETemail.getEditText().getText().toString(),ETpassword.getEditText().getText().toString());
        }

    }

    public void signin(String email , String password)
    {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser());
                    Toast.makeText(EmailPassword.this, "Aвторизация успешна", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(EmailPassword.this, "Aвторизация провалена", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void registration (String email , String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    onAuthSuccess(task.getResult().getUser());
                    Toast.makeText(EmailPassword.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(EmailPassword.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onAuthSuccess(FirebaseUser user) {

        String username = ETusername.getEditText().getText().toString();
        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());
        startActivity(new Intent(EmailPassword.this, MainActivity.class));
    }

    private void writeNewUser(String userId, String name,String email) {
       User user = new User(email, name);

        mDatabase.child("users").child(userId).setValue(user);
    }

}

