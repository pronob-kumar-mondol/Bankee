package com.example.bankee.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.preference.PreferenceManager;

import com.example.bankee.Fragment.HomeFragment;
import com.example.bankee.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {
    TextInputEditText inputfullName;
    TextInputEditText inputemail;
    TextInputEditText inputpass;
    AppCompatButton btn;
    String emailPattern="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    String passPattern="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    DatabaseReference firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        inputfullName=findViewById(R.id.fullNameEditTxt);
        inputemail=findViewById(R.id.emailEditTxt);
        inputpass=findViewById(R.id.passEditTxt);
        btn=findViewById(R.id.btn);
        progressBar=findViewById(R.id.progressBar);
        fAuth=FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance().getReference("UserDetails");




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformAuth();
            }
        });


    }

    private void PerformAuth() {
        String name= inputfullName.getText().toString();
        String email= inputemail.getText().toString().trim();
        String pass= inputpass.getText().toString();
        String phoneNumber="00000";
        String address="Add Your Address";
        String imageLink="https://firebasestorage.googleapis.com/v0/b/bankee-ba7df.appspot.com/o/pronob.jpg?alt=media&token=bf1a7013-d048-4509-a8f2-01529b33807d";


        if (!email.matches(emailPattern)){
            inputemail.setError("Enter Correct Email");
            inputemail.requestFocus();
        } else if (!pass.matches(passPattern) || pass.isEmpty()) {
            inputpass.setError("Password not valid");
        }else {
            progressBar.setVisibility(View.VISIBLE);


            fAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        firebaseDatabase.child(fAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                snapshot.child("UserName").getRef().setValue(name);
                                snapshot.child("UserEmail").getRef().setValue(email);
                                snapshot.child("UserPhone").getRef().setValue(phoneNumber);
                                snapshot.child("UserAddress").getRef().setValue(address);
                                snapshot.child("imageLink").getRef().setValue(imageLink);
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(SignupActivity.this, "Registration Sucsessful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(SignupActivity.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                    }
                }
            });




        }


    }


}