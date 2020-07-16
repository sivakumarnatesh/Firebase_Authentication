package com.example.findtree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Closeable;

public class Search extends AppCompatActivity {
    Button btnlogout;
    FirebaseAuth fa;
    private FirebaseAuth.AuthStateListener mAuthstateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        btnlogout = findViewById(R.id.button);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent a = new Intent(Search.this, SplashScreen.class);
                startActivity(a);
            }
        });
    }
}