package com.example.findtree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText uname,emailid,pwd;
    Button next,back;
    FirebaseAuth fa;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        uname = (EditText)findViewById(R.id.editTextTextPersonName);
        emailid = (EditText)findViewById(R.id.editTextTextEmailAddress3);
        pwd = (EditText)findViewById(R.id.editTextTextPassword);

        back = (Button)findViewById(R.id.button);
        fa = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Student");
        next = (Button)findViewById(R.id.button2);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = uname.getText().toString();
                final String email = emailid.getText().toString();
                final String password = pwd.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this,"Please enter email",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this,"Please enter password",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(Register.this,"Please enter name",Toast.LENGTH_SHORT).show();
                }

                    fa.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Student information = new Student(
                                        name,
                                        email,
                                        password
                                );
                                FirebaseDatabase.getInstance().getReference("Student")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(Register.this,"Registration complete",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),Search.class));

                                    }
                                });
                            }
                            else {

                            }


                        }
                    });
                }
        });
    }
}