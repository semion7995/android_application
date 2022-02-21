package com.example.homefragmentexample.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.homefragmentexample.R;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener{

    //    public static final FavoritesEmpty EMPTY_FAVORITES = new FavoritesEmpty();
    private FirebaseAuth mAuth;
    //    private Button authorization;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText ETemail;
    private EditText ETpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    mAuth = FirebaseAuth.getInstance();




    mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                startActivity(intent);

                //user is signed in
            } else {
                // User is signed out

            }

        }
    };
    ETemail = (EditText) findViewById(R.id.et_email);
    ETpassword = (EditText) findViewById(R.id.et_password);



    findViewById(R.id.btn_sign_in).setOnClickListener(this);
    findViewById(R.id.btn_registration).setOnClickListener(this);



    FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_sign_in) {
            signin(ETemail.getText().toString(), ETpassword.getText().toString());
        } else if (view.getId() == R.id.btn_registration) {
            registration(ETemail.getText().toString(), ETpassword.getText().toString());
        }

    }

    public void signin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AuthActivity.this, "Authorization successful ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                    startActivity(intent);
                } else
                    Toast.makeText(AuthActivity.this, "Authorization failed ", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void registration(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AuthActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(AuthActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}