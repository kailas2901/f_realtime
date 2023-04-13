package com.example.f_realtime;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText ed1,ed2,ed3;
    Button b1;
    FirebaseAuth Fauth;
    FirebaseDatabase fd;
    String Userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ed1 = findViewById(R.id.editTextTextPersonName);
        ed2 = findViewById(R.id.ed21);
        ed3 = findViewById(R.id.ed31);
        b1 = findViewById(R.id.b1);
        Fauth = FirebaseAuth.getInstance();
        fd = FirebaseDatabase.getInstance();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s1 = ed1.getText().toString().trim();
                String s2 = ed2.getText().toString().trim();
                String s3 = ed3.getText().toString().trim();
                if (TextUtils.isEmpty(s2)) {
                    ed2.setError("Enter data");
                } else if (TextUtils.isEmpty(s3)) {
                    ed3.setError("Enter password");
                }
                else{

                Fauth.createUserWithEmailAndPassword(s2,s3).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser fuser = Fauth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(Register.this, "Sucess", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),Home.class));
                                }
                            });
                            Userid = Fauth.getCurrentUser().getUid();
                            Map<String,Object> useraA = new HashMap<>();
                            useraA.put("username",s1);
                            useraA.put("email",s2);
                            fd.getReference("users").child(Userid).setValue(useraA).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this, "Data entered", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Register.this, "Data not Stored", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
                //realtime database



                }
        });

    }
}