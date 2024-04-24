package com.example.bankee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.bankee.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText inputEmail;
    TextInputEditText inputPass;
    AppCompatButton btn;
    TextView signUp;
    String emailPattern="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    String passPattern="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        inputEmail=findViewById(R.id.emailEditTxt);
        inputPass=findViewById(R.id.passEditTxt);
        btn=findViewById(R.id.btn);
        signUp=findViewById(R.id.signUp);
        progressBar=findViewById(R.id.progressBar);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();




            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(LoginActivity.this, SignupActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

           btn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   prformAuth();
               }
           });

    }

    private void prformAuth() {
        String email= inputEmail.getText().toString().trim();
        String pass= inputPass.getText().toString();

        if (!email.matches(emailPattern)){
            inputEmail.setError("Enter Correct Email");
            inputEmail.requestFocus();
        } else if (!pass.matches(passPattern) || pass.isEmpty()) {
            inputPass.setError("Password not valid");
        }else {
            progressBar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Login Sucsessful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // Incorrect password
                            Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                        } else {
                            // Other errors, including incorrect email or user not found
                            if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                FirebaseAuthInvalidUserException e = (FirebaseAuthInvalidUserException) task.getException();
                                if (e.getErrorCode().equals("ERROR_USER_NOT_FOUND")) {
                                    // Email not registered
                                    Toast.makeText(LoginActivity.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Other errors related to user authentication
                                    Toast.makeText(LoginActivity.this, "Authentication failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Other general errors
                                Toast.makeText(LoginActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }
            });



        }
    }


}