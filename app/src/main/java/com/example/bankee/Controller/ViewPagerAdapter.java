package com.example.bankee.Controller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.bankee.Fragment.ContactAllFragment;
import com.example.bankee.Fragment.ContactFavourite_Fragment;
import com.google.android.material.tabs.TabLayoutMediator;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private final String[] tabTitles = new String[]{"All", "Favorites"};


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==0){
            return new ContactAllFragment();
        }else {
            return new ContactFavourite_Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }


    public String getTabTitle(int position) {
        return tabTitles[position];
    }



}
