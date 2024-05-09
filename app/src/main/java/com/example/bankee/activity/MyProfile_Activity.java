package com.example.bankee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bankee.Model.UserDetails;
import com.example.bankee.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfile_Activity extends AppCompatActivity {

    CircleImageView imageView;
    TextView fullNmae,eMail,phonenumber,address;

    ImageView ivBack,ivMenu;
    TextView tvTitle;
    DatabaseReference reference;
    FirebaseAuth fAuth;
    UserDetails userDetails;
    ConstraintLayout mainLayout;
    ProgressBar progressBar;
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
        mainLayout = findViewById(R.id.mainLayout);
        mainLayout.setVisibility(View.INVISIBLE);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        fAuth=FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference("UserDetails");

        String userId=fAuth.getUid().toString();

        ivMenu.setImageResource(newImageResourceId);
        tvTitle.setText("My Profile");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userDetails=snapshot.getValue(UserDetails.class);
                fullNmae.setText(userDetails.getName());
                eMail.setText(userDetails.getEmail());
                phonenumber.setText(userDetails.getPhoneNumber());
                address.setText(userDetails.getAddress());

                if(userDetails.getImgLink().isEmpty()){
                    Picasso.get().load(R.drawable.user).into(imageView);
                }else {
                    Picasso.get().load(userDetails.getImgLink()).placeholder(R.drawable.user).into(imageView);
                }

                progressBar.setVisibility(View.INVISIBLE);
                mainLayout.setVisibility(View.VISIBLE);

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