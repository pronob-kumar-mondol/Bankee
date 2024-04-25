package com.example.bankee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bankee.R;

public class MyProfile_Activity extends AppCompatActivity {

    ImageView ivBack,ivMenu;
    TextView tvTitle;
    int newImageResourceId = R.drawable.edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        ivBack=findViewById(R.id.ivBack);
        ivMenu=findViewById(R.id.ivMenu);
        tvTitle=findViewById(R.id.tvTitle);

        ivMenu.setImageResource(newImageResourceId);

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyProfile_Activity.this, EditProfile_Activity.class);
                startActivity(intent);
            }
        });


    }
}