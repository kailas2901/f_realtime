package com.example.f_realtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class Home extends AppCompatActivity {
TextView t1,t2;
FirebaseDatabase fd;
String userID;
FirebaseAuth Fauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fd = FirebaseDatabase.getInstance();
        Fauth = FirebaseAuth.getInstance();
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        userID = Fauth.getCurrentUser().getUid();
        fd.getReference("users").child(userID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                     DataSnapshot dataSnapshot = task.getResult();
                        String Name = (String) dataSnapshot.child("username").getValue();
                        t1.setText(Name);
                        String Name1 = (String) dataSnapshot.child("email").getValue();
                        t2.setText(Name1);




                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Home.this, "Data not exists", Toast.LENGTH_SHORT).show();
            }
        });



    }
}