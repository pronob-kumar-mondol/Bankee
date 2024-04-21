package com.example.bankee.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.bankee.R;
import com.example.bankee.activity.SendMoneyActivity;


public class HomeFragment extends Fragment {

    RelativeLayout sendMoney;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_home, container, false);
        sendMoney=v.findViewById(R.id.sendMoney);

        sendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SendMoneyActivity.class);
                startActivity(intent);
            }
        });





        return v;
    }
}