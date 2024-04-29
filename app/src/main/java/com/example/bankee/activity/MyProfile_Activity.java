package com.example.bankee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bankee.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfile_Activity extends AppCompatActivity {

    CircleImageView imageView;
    TextView fullNmae,eMail,phonenumber,address;

    ImageView ivBack,ivMenu;
    TextView tvTitle;
    DatabaseReference reference;
    FirebaseAuth fAuth;
    int newImageResourceId = R.drawable.edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        ivBack=findViewById(R.id.ivBack);
        ivMenu=findViewById(R.id.ivMenu);
        tvTitle=findViewById(R.id.tvTitle);
        imageView=findViewById(R.id.circleImageView);
        fullNmae=findViewById(R.id.textView6);
        eMail=findViewById(R.id.textView8);
        phonenumber=findViewById(R.id.textView10);
        address=findViewById(R.id.address);


        fAuth=FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference("UserDetails");

        String userId=fAuth.getUid().toString();

        ivMenu.setImageResource(newImageResourceId);

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name=snapshot.child("UserName").getValue(String.class);
                String email=snapshot.child("UserEmail").getValue(String.class);
                String phoneNumber=snapshot.child("UserPhone").getValue(String.class);
                String Address= snapshot.child("UserAddress").getValue(String.class);

                fullNmae.setText(name);
                eMail.setText(email);
                phonenumber.setText(phoneNumber);
                address.setText(Address);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyProfile_Activity.this, EditProfile_Activity.class);
                startActivity(intent);
            }
        });


    }
}