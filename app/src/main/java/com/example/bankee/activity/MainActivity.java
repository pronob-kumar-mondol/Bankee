package com.example.bankee.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.bankee.Fragment.HistoryFragment;
import com.example.bankee.Fragment.HomeFragment;
import com.example.bankee.Fragment.MyCardFragment;
import com.example.bankee.Fragment.ProfileFragment;
import com.example.bankee.R;

import com.example.bankee.onboarding.ViewPagerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottom_nav;
    FrameLayout frame_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bottom_nav=findViewById(R.id.bottom_nav);
        frame_layout=findViewById(R.id.frame_layout);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();

        bottom_nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment=null;
                boolean isActive=false;

                if(menuItem.getItemId()==R.id.home){

                    selectedFragment=new HomeFragment();
                    isActive=true;

                }
                else if(menuItem.getItemId()==R.id.history){

                    selectedFragment=new HistoryFragment();
                    isActive=true;

                }
                else if(menuItem.getItemId()==R.id.my_card){

                    selectedFragment=new MyCardFragment();
                    isActive=true;

                }
                else if(menuItem.getItemId()==R.id.profile){

                    selectedFragment=new ProfileFragment();
                    isActive=true;

                }

                if (selectedFragment!=null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectedFragment).commit();

                }

                return isActive;
            }
  });






    }

}