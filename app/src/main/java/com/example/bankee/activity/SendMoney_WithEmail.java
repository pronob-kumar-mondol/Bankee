package com.example.bankee.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.bankee.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SendMoney_WithEmail extends AppCompatActivity {
    TextView email;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money_with_email);
        reference= FirebaseDatabase.getInstance().getReference("UserDetails");

        email=findViewById(R.id.editText2);
        String reciverEmail=email.getText().toString();


        Query query=reference.orderByChild("UserEmail").equalTo(reciverEmail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    String reciverUid=ds.getKey();
                    if(reciverUid!=null){


                        Log.d("UID", "Sagor's UID: " + reciverUid);
                    }else{
                        Log.d("UID", "No user found with the useremail");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}