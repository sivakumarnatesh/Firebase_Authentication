package com.example.findtree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button reg, login;
    EditText email, pwd;
    FirebaseAuth fa;
    private FirebaseAuth.AuthStateListener mAuthstateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (EditText) findViewById(R.id.editTextTextEmailAddress3);
        pwd = (EditText) findViewById(R.id.editTextTextPassword);
        login = (Button) findViewById(R.id.button2);
        fa = FirebaseAuth.getInstance();
        reg = (Button) findViewById(R.id.button3);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
        mAuthstateListener = new FirebaseAuth.AuthStateListener() {
            FirebaseUser mFirebaseUser = fa.getCurrentUser();

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (mFirebaseUser != null) {
                    Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, Search.class);
                    startActivity(i);
                } else {

                }
            }
        };
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailid = email.getText().toString();
                String password = pwd.getText().toString();
                if (emailid.isEmpty()) {
                    email.setError("please enter your email id");
                    email.requestFocus();
                }
                else if (password.isEmpty()) {
                    pwd.setError("please enter your password");
                    pwd.requestFocus();
                }
                else if (!(emailid.isEmpty() && password.isEmpty())) {
                    fa.signInWithEmailAndPassword(emailid, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "please enter valid email and password", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Intent intent = new Intent(MainActivity.this, Search.class);
                                startActivity(intent);
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(MainActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

        protected void onStart () {
            super.onStart();
            fa.addAuthStateListener(mAuthstateListener);
        }
}