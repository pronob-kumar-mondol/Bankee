package com.example.bankee.activity;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NwePass_Activity extends AppCompatActivity {
    TextInputEditText current_pass,new_pass,confirm_pass;
    AppCompatButton btn;
    FirebaseAuth fAuth;
    ImageView ivBack,ivMenu;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nwe_pass);
        current_pass = findViewById(R.id.currentPass);
        new_pass = findViewById(R.id.newPass);
        confirm_pass = findViewById(R.id.confPass);
        btn = findViewById(R.id.btn);
        ivBack = findViewById(R.id.ivBack);
        ivMenu = findViewById(R.id.ivMenu);
        tvTitle = findViewById(R.id.tvTitle);
        fAuth = FirebaseAuth.getInstance();

        ivMenu.setVisibility(View.GONE);
        tvTitle.setText("Change Password");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });



    }

    private void changePassword() {

        if (!current_pass.getText().toString().isEmpty() && !new_pass.getText().toString().isEmpty() && !confirm_pass.getText().toString().isEmpty()) {

            if (new_pass.getText().toString().equals(confirm_pass.getText().toString())) {
                FirebaseUser user=fAuth.getCurrentUser();
                if (user!=null && user.getEmail()!=null){

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
                    AuthCredential credential = EmailAuthProvider
                            .getCredential(user.getEmail(), current_pass.getText().toString());

// Prompt the user to re-provide their sign-in credentials
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d(TAG, "User re-authenticated.");
                                    if (task.isSuccessful()){

                                        user.updatePassword(new_pass.getText().toString())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            showCofirmDialog();
                                                            Log.d(TAG, "User password updated.");
                                                        }
                                                    }
                                                });
                                    }else{
                                        showCancelDialog();
                                    }
                                }
                            });

                }else {
                    startActivity(new Intent(this,LoginActivity.class));
                    finish();
                }

            }else{
                Toast.makeText(this, "Password Does Not Match", Toast.LENGTH_SHORT).show();
            }
            
        }else {
            Toast.makeText(this, "Please Enter All The Fields", Toast.LENGTH_SHORT).show();
        }

    }

    private void showCancelDialog() {
        Dialog dialog=new Dialog(NwePass_Activity.this);
        dialog.setContentView(R.layout.failed_transfer);
        dialog.show();

        TextView text=dialog.findViewById(R.id.textView38);
        text.setText(R.string.oops_something_went_wrong);

        TextView onCancel=dialog.findViewById(R.id.onCancel);
        onCancel.setVisibility(View.GONE);

        AppCompatButton btn=dialog.findViewById(R.id.gobackBtn);
        btn.setText(R.string.continuee);
        btn.setOnClickListener(v -> {
            dialog.dismiss();
            finish();
        });
    }

    private void showCofirmDialog() {

        Dialog dialog=new Dialog(NwePass_Activity.this);
        dialog.setContentView(R.layout.sucsessful_transfer);
        dialog.show();

        TextView text=dialog.findViewById(R.id.textView29);
        AppCompatButton btn=dialog.findViewById(R.id.gobackBtn);

        text.setText(R.string.password_changed_successfully);

        btn.setOnClickListener(v -> {
            dialog.dismiss();
            fAuth.signOut();
            startActivity(new Intent(NwePass_Activity.this,LoginActivity.class));
            finish();

        });

    }
}