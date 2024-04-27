package com.example.bankee.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "MyAppPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    TextInputEditText inputEmail;
    TextInputEditText inputPass;
    AppCompatButton btn;
    TextView signUp;
    String emailPattern="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    String passPattern="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
    ProgressBar progressBar;
    FirebaseAuth fAuth;
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
        fAuth=FirebaseAuth.getInstance();
        mUser=fAuth.getCurrentUser();
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);



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

            fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Login Sucsessful", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(KEY_IS_LOGGED_IN, true);
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this,"Login Failed! Try again.",Toast.LENGTH_SHORT).show();
                        }
                    }

            });



        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(fAuth.getCurrentUser()!=null){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        else {

        }
    }


}