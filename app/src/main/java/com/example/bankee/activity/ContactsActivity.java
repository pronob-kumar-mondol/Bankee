package com.example.bankee.activity;

import android.os.Bundle;

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

    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contacts);


        viewPager=findViewById(R.id.viewPager_layout);
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