package com.example.bankee.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bankee.R;

public class SendMoney_2 extends AppCompatActivity {
    ImageView ivMenu, ivBack;
    TextView app_title;
    TextView name,number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money2);

        // Retrieve contact details from intent extras
        Intent intent = getIntent();
        String contactName = intent.getStringExtra("contactName");
        String contactNumber = intent.getStringExtra("contactNumber");

        ivMenu=findViewById(R.id.ivMenu);
        ivBack=findViewById(R.id.ivBack);
        app_title=findViewById(R.id.tvTitle);

        app_title.setText("Send Money");
        ivMenu.setVisibility(View.GONE);

        // Set contact details to views
        name=findViewById(R.id.textView);
        number=findViewById(R.id.textView2);

        name.setText(contactName);
        number.setText(contactNumber);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }


}