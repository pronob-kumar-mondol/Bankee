package com.example.bankee.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bankee.R;
import com.example.bankee.activity.ContactsActivity;
import com.example.bankee.activity.SendMoneyActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeFragment extends Fragment {

    RelativeLayout sendMoney;
    RelativeLayout contacts;
    TextView user_name;
    RelativeLayout home;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_home, container, false);
        sendMoney=v.findViewById(R.id.sendMoney);
        contacts=v.findViewById(R.id.contacts);
        user_name=v.findViewById(R.id.user_name);
        user= FirebaseAuth.getInstance().getCurrentUser();


        if (user != null) {
            // User is signed in
            // Retrieve user's display name asynchronously
            user.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        String displayName = user.getDisplayName();
                        // Assuming you have a TextView with id "userNameTextView" to display the user's name
                        user_name.setText(displayName);
                    } else {
                        // Handle error
                        Log.e("TAG", "Failed to reload user: " + task.getException());
                    }
                }
            });
        } else {
            // User is not signed in
            // Handle this case accordingly, for example, redirect to the login page
        }

        sendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SendMoneyActivity.class);
                startActivity(intent);
            }
        });

        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ContactsActivity.class));
                
            }
        });





        return v;
    }
}