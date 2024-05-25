package com.example.bankee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.bankee.Fragment.AddCardBottomSheetDialogFragment;
import com.example.bankee.Fragment.HistoryFragment;
import com.example.bankee.Fragment.HomeFragment;
import com.example.bankee.Fragment.MyCardFragment;
import com.example.bankee.Fragment.ProfileFragment;
import com.example.bankee.R;

import com.example.bankee.onboarding.ViewPagerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements AddCardBottomSheetDialogFragment.OnCardAddedListener {

    BottomNavigationView bottom_nav;
    FrameLayout frame_layout;
    FloatingActionButton fAb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_nav=findViewById(R.id.bottom_nav);
        frame_layout=findViewById(R.id.frame_layout);
        fAb = findViewById(R.id.favBtn);
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



        fAb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQRScan();
            }
        });






    }

    private void startQRScan() {

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scan a QR code");
        integrator.setOrientationLocked(true);
        integrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                // Handle the QR code result
                String scannedData = result.getContents();
                startActivity(new Intent(this, SendMoney_WithEmail.class).putExtra("data",scannedData));

            }
        }
    }




    @Override
    public void onCardAdded() {

    }
}