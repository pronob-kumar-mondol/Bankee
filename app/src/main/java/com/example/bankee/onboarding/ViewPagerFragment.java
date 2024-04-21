package com.example.bankee.onboarding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bankee.R;
import com.example.bankee.onboarding.screens.First_Screen_Fragment;
import com.example.bankee.onboarding.screens.Second_Screen_Fragment;
import com.example.bankee.onboarding.screens.Third_Screen_Fragment;

public class ViewPagerFragment extends Fragment {

    private ViewPager2 viewPager;

    public ViewPagerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_view_pager, container, false);

        viewPager = v.findViewById(R.id.viewPager);
        ViewPager_Adapter adapter = new ViewPager_Adapter(getChildFragmentManager(), getLifecycle());
        adapter.addFragment(new First_Screen_Fragment());
        adapter.addFragment(new Second_Screen_Fragment());
        adapter.addFragment(new Third_Screen_Fragment());
        viewPager.setAdapter(adapter);

        return v;
    }
}