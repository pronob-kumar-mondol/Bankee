package com.example.bankee.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bankee.Model.CardDetails;
import com.example.bankee.Model.UserDetails;
import com.example.bankee.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class AddCardBottomSheetDialogFragment extends BottomSheetDialogFragment {

    TextInputEditText cardNumberET, expiryDateET, cvvCodeET;
    Spinner  cardTypeET;
    AppCompatButton btn;
    FirebaseAuth fAuth;
    DatabaseReference reference;
    UserDetails userDetails;
    CardDetails cardDetails;
    private OnCardAddedListener listener;

    public interface OnCardAddedListener {
        void onCardAdded();
    }

    public void setOnCardAddedListener(OnCardAddedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCardAddedListener) {
            listener = (OnCardAddedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnCardAddedListener");
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_add_card_bottom_sheet_dialog, container, false);

        cardNumberET=v.findViewById(R.id.card_number_edit_text);
        expiryDateET=v.findViewById(R.id.expiry_date_edit_text);
        cvvCodeET=v.findViewById(R.id.cvv_code_edit_text);
        cardTypeET=v.findViewById(R.id.card_type_spinner);
        btn=v.findViewById(R.id.btn);

        fAuth=FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference("UserDetails");



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addCardDetails();
                dismiss();
            }
        });




        return v;
    }

    private void addCardDetails() {
        String cardNumber=String.valueOf(cardNumberET.getText());
        String expiryDate=String.valueOf(expiryDateET.getText());
        String cvvCode=String.valueOf(cvvCodeET.getText());
        String cardType=String.valueOf(cardTypeET.getSelectedItem());
        int balance=10000;


        if (cardNumber.isEmpty() || expiryDate.isEmpty() || cvvCode.isEmpty() || cardType.isEmpty()) {
            Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
        }else {
            cardDetails = new CardDetails(cardType,cardNumber, expiryDate, cvvCode, balance);
            reference.child(Objects.requireNonNull(fAuth.getUid())).child("CardDetails").setValue(cardDetails);
            Toast.makeText(getContext(), "Card added successfully", Toast.LENGTH_SHORT).show();
        }


        if (listener != null) {
            listener.onCardAdded();
        }




    }
}