package com.example.bankee.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bankee.R;
import com.example.bankee.activity.MyProfile_Activity;
import com.example.bankee.activity.SendMoneyActivity;


public class ProfileFragment extends Fragment {
    ImageView ivBack,ivMenu;
    TextView tvTitle;

    RelativeLayout your_profile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_profile, container, false);

        your_profile=v.findViewById(R.id.one);
        ivBack=v.findViewById(R.id.ivBack);
        ivMenu=v.findViewById(R.id.ivMenu);
        tvTitle=v.findViewById(R.id.tvTitle);

        ivBack.setVisibility(View.GONE);
        ivMenu.setVisibility(View.GONE);
        tvTitle.setText(R.string.profile);

        your_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MyProfile_Activity.class);
                startActivity(intent);
            }
        });




        return v;
    }
}