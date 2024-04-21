package com.example.bankee.activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bankee.R;
import com.example.bankee.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    ImageView ivBack,ivMenu;
    EditText emailEditTxt,passEditTxt;
    TextView signUp,tvTitle;
    FirebaseAuth firebaseAuth;
    androidx.appcompat.widget.AppCompatButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


            ivBack=findViewById(R.id.ivBack);
            btn=findViewById(R.id.btn);
            ivBack.setVisibility(View.GONE);
            ivMenu=findViewById(R.id.ivMenu);
            ivMenu.setVisibility(View.GONE);
            signUp=findViewById(R.id.signUp);
            tvTitle=findViewById(R.id.tvTitle);
            passEditTxt=findViewById(R.id.passEditTxt);
            emailEditTxt=findViewById(R.id.emailEditTxt);
            tvTitle.setText("Log In");
            firebaseAuth=FirebaseAuth.getInstance();

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
                    firebaseAuth.signInWithEmailAndPassword(emailEditTxt.getText().toString().trim(),passEditTxt.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isComplete()){
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user=firebaseAuth.getCurrentUser();
                                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            });








//        });
    }


    public void togglePasswordVisibility(View view) {
        EditText editTextPassword=findViewById(R.id.passEditTxt);
        Utility.togglePasswordVisibility(editTextPassword);
    }
}