package com.example.bankee.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bankee.R;
import com.example.bankee.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        inputfullName=findViewById(R.id.fullNameEditTxt);
        inputemail=findViewById(R.id.emailEditTxt);
        inputpass=findViewById(R.id.passEditTxt);
        btn=findViewById(R.id.btn);
        progressBar=findViewById(R.id.progressBar);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        firebaseDatabase= FirebaseDatabase.getInstance().getReference("UserDEtails");




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

        if (!email.matches(emailPattern)){
            inputemail.setError("Enter Correct Email");
            inputemail.requestFocus();
        } else if (!pass.matches(passPattern) || pass.isEmpty()) {
            inputpass.setError("Password not valid");
        }else {
            progressBar.setVisibility(View.VISIBLE);


            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        firebaseDatabase.push().addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                snapshot.child("UserName").getRef().setValue(name);
                                snapshot.child("UserEmail").getRef().setValue(email);
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