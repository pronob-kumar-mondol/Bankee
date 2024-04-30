package com.example.bankee.activity;

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
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bankee.Controller.ViewPagerAdapter;
import com.example.bankee.Fragment.ContactAllFragment;
import com.example.bankee.Fragment.ContactFavourite_Fragment;
import com.example.bankee.R;

public class ContactsActivity extends AppCompatActivity {

    TextView tvTitle;
    ImageView ivBack,ivMenu;

    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contacts);


        viewPager=findViewById(R.id.viewPager_layout);
        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBack);
        ivMenu = findViewById(R.id.ivMenu);


        tvTitle.setText("Contacts");
        ivMenu.setVisibility(View.VISIBLE);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





        ViewPagerAdapter adapter = new ViewPagerAdapter(this){
            @Override
            public Fragment createFragment(int position) {
                return new ContactAllFragment();
            }
            @Override
            public int getItemCount() {
                return 1;
            }
        };
        viewPager.setAdapter(adapter);




    }
}