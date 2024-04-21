package com.example.bankee.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bankee.Controller.ViewPagerAdapter;
import com.example.bankee.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/** @noinspection deprecation*/
public class SendMoneyActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);

        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager_layout);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);





        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // Set the title of each tab using the getTabTitle method from the adapter
            tab.setText(adapter.getTabTitle(position));
        }).attach();



    }
}




