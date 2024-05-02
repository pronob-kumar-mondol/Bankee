package com.example.bankee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bankee.Controller.ViewPagerAdapter;
import com.example.bankee.Fragment.HomeFragment;
import com.example.bankee.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class SendMoneyActivity extends AppCompatActivity {


    ImageView ivMenu, ivBack;
    TextView app_title;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    AppCompatButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);

        tabLayout=findViewById(R.id.tabLayout);
        app_title=findViewById(R.id.tvTitle);
        ivMenu=findViewById(R.id.ivMenu);
        viewPager=findViewById(R.id.viewPager_layout);
        ivBack=findViewById(R.id.ivBack);
        btn = findViewById(R.id.btn2);
        app_title.setText("Send Money");
        ivMenu.setVisibility(View.GONE);


        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SendMoneyActivity.this, SendMoney_WithEmail.class);
                startActivity(intent);
            }
        });



        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // Set the title of each tab using the getTabTitle method from the adapter
            tab.setText(adapter.getTabTitle(position));
        }).attach();



    }
}




