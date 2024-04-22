package com.example.bankee.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.bankee.R;
import com.example.bankee.activity.ContactsActivity;
import com.example.bankee.activity.SendMoneyActivity;


public class HomeFragment extends Fragment {

    RelativeLayout sendMoney;
    RelativeLayout contacts;
    RelativeLayout home;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_home, container, false);
        sendMoney=v.findViewById(R.id.sendMoney);
        contacts=v.findViewById(R.id.contacts);
        home=v.findViewById(R.id.homeies);

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