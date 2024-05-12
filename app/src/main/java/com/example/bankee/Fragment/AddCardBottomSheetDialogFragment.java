package com.example.bankee.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bankee.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

public class AddCardBottomSheetDialogFragment extends BottomSheetDialogFragment {

    TextInputEditText cardNumberET, expiryDateET, cvvCodeET, cardTypeET;
    AppCompatButton btn;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_add_card_bottom_sheet_dialog, container, false);

        cardNumberET=v.findViewById(R.id.card_number_edit_text);
        expiryDateET=v.findViewById(R.id.expiry_date_edit_text);
        cvvCodeET=v.findViewById(R.id.cvv_code_edit_text);
        cardTypeET=v.findViewById(R.id.card_type_edit_text);
        btn=v.findViewById(R.id.btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });








        return v;
    }
}