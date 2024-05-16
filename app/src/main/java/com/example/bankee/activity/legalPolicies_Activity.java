package com.example.bankee.activity;

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

public class legalPolicies_Activity extends AppCompatActivity {

    TextView tvTitle;
    ImageView ivBack,ivMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal_policies);
        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBack);
        ivMenu = findViewById(R.id.ivMenu);

        EdgeToEdge.enable(this);
        tvTitle.setText(R.string.legal_policies);
        ivMenu.setVisibility(View.GONE);

        ivBack.setOnClickListener(view -> {
            finish();
        });



    }
}