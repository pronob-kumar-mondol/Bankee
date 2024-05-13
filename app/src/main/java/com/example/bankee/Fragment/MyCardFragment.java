package com.example.bankee.Fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bankee.Model.CardDetails;
import com.example.bankee.Model.UserDetails;
import com.example.bankee.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class MyCardFragment extends Fragment implements AddCardBottomSheetDialogFragment.OnCardAddedListener {

    private TextView noCardTextView,balance,holder_name,card_no,expiry;
    private ImageView cardImageView,cardType;
    private FloatingActionButton addCardFab,deleteCardFab;
    ImageView ivBack,ivMenu;
    TextView tvTitle,tvCardno;
    ProgressBar progressBar;
     ConstraintLayout my_card,mainLayout;
     FirebaseAuth fAuth=FirebaseAuth.getInstance();

     DatabaseReference reference= FirebaseDatabase.getInstance().getReference("UserDetails").child(Objects.requireNonNull(fAuth.getCurrentUser()).getUid());


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_my_card, container, false);

        noCardTextView = v.findViewById(R.id.no_card_text_view);
        my_card = v.findViewById(R.id.my_card);
        addCardFab = v.findViewById(R.id.add_card_fab);
        deleteCardFab = v.findViewById(R.id.delete_card_fab);
        progressBar = v.findViewById(R.id.progressBarCard);
        progressBar.setVisibility(View.VISIBLE);
        mainLayout = v.findViewById(R.id.mainLayout);
        tvCardno = v.findViewById(R.id.textView27);
        mainLayout.setVisibility(View.INVISIBLE);
        noCardTextView.setVisibility(View.VISIBLE);
        my_card.setVisibility(View.GONE);
        ivBack = v.findViewById(R.id.ivBack);
        ivMenu = v.findViewById(R.id.ivMenu);
        tvTitle = v.findViewById(R.id.tvTitle);

        tvTitle.setText("My Card");
        ivMenu.setVisibility(View.GONE);
        ivBack.setVisibility(View.GONE);



//        ??card layout initialization
        balance = v.findViewById(R.id.balance);
        holder_name = v.findViewById(R.id.holder_name);
        card_no = v.findViewById(R.id.card_no);
        expiry = v.findViewById(R.id.expire_date);
        cardType = v.findViewById(R.id.cardType);


        addCardFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCardBottomSheet();

            }
        });
        deleteCardFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MyCardFragment.this.getContext());
                dialog.setContentView(R.layout.card_delete_dialog);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();

                AppCompatButton cancelBtn=dialog.findViewById(R.id.btn);
                TextView deleteBtn=dialog.findViewById(R.id.deleteBtn);

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteCard();
                        dialog.dismiss();
                    }
                });
            }
        });


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("CardDetails").exists()) {
                    UserDetails userDetails = null;
                    try {
                        userDetails = snapshot.getValue(UserDetails.class);
                        CardDetails cardDetails = snapshot.child("CardDetails").getValue(CardDetails.class);
                        balance.setText(String.valueOf(cardDetails.getBalance()));
                        holder_name.setText(userDetails.getUserName());
                        card_no.setText(cardDetails.getCardNumber());
                        expiry.setText(cardDetails.getExpDate());
                        if (cardDetails.getCardType().equals("Visa")) {
                            cardType.setImageResource(R.drawable.visa);
                        } else {
                            cardType.setImageResource(R.drawable.mastercard_logo);
                        }
                    } catch (Exception e) {
                        balance.setText("0.0");
                        card_no.setText("0000 0000 0000 0000");
                        expiry.setText("00/00");
                        holder_name.setText(userDetails.getUserName());
                        cardType.setVisibility(View.INVISIBLE);
                        tvCardno.setVisibility(View.GONE);
                    }
                    onCardAdded();
                    mainLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                }else {
                    addCardFab.setVisibility(View.VISIBLE);
                    deleteCardFab.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    mainLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });












        return v;
    }


    private void deleteCard() {

        reference.child("CardDetails").removeValue();
        noCardTextView.setVisibility(View.VISIBLE);
        my_card.setVisibility(View.GONE);
        addCardFab.setVisibility(View.VISIBLE);
        deleteCardFab.setVisibility(View.GONE);

    }
    private void showAddCardBottomSheet() {
        // Show bottom sheet dialog for adding card
        AddCardBottomSheetDialogFragment bottomSheet = new AddCardBottomSheetDialogFragment();
        bottomSheet.setOnCardAddedListener(this::showAddCardBottomSheet); // Set the listener to the MyCardFragment
        bottomSheet.show(getChildFragmentManager(), bottomSheet.getTag());
    }


    @Override
    public void onCardAdded() {
        // Update UI when a new card is added
        noCardTextView.setVisibility(View.GONE);
        my_card.setVisibility(View.VISIBLE);
        addCardFab.setVisibility(View.GONE);
        deleteCardFab.setVisibility(View.VISIBLE);
    }
}