package com.example.bankee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bankee.R;
import com.example.bankee.Utility;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    ImageView ivBack,ivMenu;
    EditText fullNameEditTxt,emailEditTxt,passEditTxt;
    TextView tvTitle;
    androidx.appcompat.widget.AppCompatButton btn;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            ivBack=findViewById(R.id.ivBack);
            ivMenu=findViewById(R.id.ivMenu);
            ivMenu.setVisibility(View.GONE);
            tvTitle=findViewById(R.id.tvTitle);
            tvTitle.setText("Sign Up");
            btn=findViewById(R.id.btn);
            firebaseAuth=FirebaseAuth.getInstance();

            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firebaseAuth.createUserWithEmailAndPassword(emailEditTxt.getText().toString().trim(),passEditTxt.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if (authResult.getUser()!=null){
                                Toast.makeText(SignupActivity.this, "Sign Up DONE", Toast.LENGTH_SHORT).show();
                                Intent i= new Intent(SignupActivity.this, MainActivity.class);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(SignupActivity.this, "Sign Up Not Done", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });




            return insets;
        });
    }


    public void togglePasswordVisibility(View view) {
        EditText editTextPassword = findViewById(R.id.passEditTxt);
        Utility.togglePasswordVisibility(editTextPassword);
    }
}