package com.example.bankee.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bankee.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MyCardFragment extends Fragment {

    private TextView noCardTextView;
    private ImageView cardImageView;
    private FloatingActionButton addCardFab;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_my_card, container, false);

        noCardTextView = v.findViewById(R.id.no_card_text_view);
        cardImageView = v.findViewById(R.id.card_image_view);
        addCardFab = v.findViewById(R.id.add_card_fab);

        addCardFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCardBottomSheet();
            }
        });












        return v;
    }

    private void showAddCardBottomSheet() {
        // Show bottom sheet dialog for adding card
        AddCardBottomSheetDialogFragment bottomSheet = new AddCardBottomSheetDialogFragment();
        bottomSheet.show(getChildFragmentManager(), bottomSheet.getTag());
    }


    public void updateUIWithNewCard() {
        // Hide TextView
        noCardTextView.setVisibility(View.GONE);
        // Show ImageView
        cardImageView.setVisibility(View.VISIBLE);
        // Update ImageView with card image
        // You may want to dynamically set the image based on card type or use a placeholder image
        cardImageView.setImageResource(R.drawable.card_1); // Replace with actual image resource
    }
}