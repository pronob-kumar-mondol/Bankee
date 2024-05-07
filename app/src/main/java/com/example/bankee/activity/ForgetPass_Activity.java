package com.example.bankee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.bankee.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPass_Activity extends AppCompatActivity {

    TextInputEditText email;
    AppCompatButton btn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    String strEmail;
    ImageView ivBack,ivMenu;
    TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        email=findViewById(R.id.emailET);
        btn=findViewById(R.id.btn);
        ivBack=findViewById(R.id.ivBack);
        ivMenu=findViewById(R.id.ivMenu);
        tvTitle=findViewById(R.id.tvTitle);
        progressBar=findViewById(R.id.progressBar);
        fAuth=FirebaseAuth.getInstance();

        ivMenu.setVisibility(View.GONE);
        tvTitle.setText("Forgot Password");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail=email.getText().toString().trim();
                if (!TextUtils.isEmpty(strEmail)) {
                    resetPass();
                }
                else {

                    email.setError("Enter Email");
                }
            }
        });

    }

    private void resetPass() {
        progressBar.setVisibility(View.VISIBLE);
        btn.setVisibility(View.INVISIBLE);

        fAuth.sendPasswordResetEmail(strEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ForgetPass_Activity.this, "Reset Link Sent To Your Registered Email Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ForgetPass_Activity.this,LoginActivity.class));
                finish();
            }
        })
           .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ForgetPass_Activity.this, "Error Occurred:- "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });btn.setVisibility(View.VISIBLE);

    }
}