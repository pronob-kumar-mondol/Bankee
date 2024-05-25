package com.example.bankee.Fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bankee.Controller.TransactionHistoryAdapter;
import com.example.bankee.Model.TransactionDetails;
import com.example.bankee.R;
import com.example.bankee.activity.SendMoney_WithEmail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HistoryFragment extends Fragment implements TransactionHistoryAdapter.OnTransactionClickListener {
    RecyclerView recyclerView;
    TextView tvTitle;
    ImageView ivBack, ivMenu;
    TransactionDetails transactionModel;
    private TransactionHistoryAdapter historyAdapter;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView=v.findViewById(R.id.recyclerView);
        tvTitle=v.findViewById(R.id.tvTitle);
        tvTitle.setText("History");
        ivBack=v.findViewById(R.id.ivBack);
        ivBack.setVisibility(View.GONE);
        ivMenu=v.findViewById(R.id.ivMenu);
        ivMenu.setVisibility(View.GONE);
        ArrayList<TransactionDetails> datalist=new ArrayList<>();
        historyAdapter = new TransactionHistoryAdapter(getContext(),datalist,HistoryFragment.this);
        historyAdapter.setOnTransactionClickListener(this);
        recyclerView.setAdapter(historyAdapter);

        reference= FirebaseDatabase.getInstance().getReference("Transactions");



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    transactionModel=ds.getValue(TransactionDetails.class);
                    datalist.add(transactionModel);

                }
                historyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });










        return v;
    }

    @Override
    public void onTransactionClick(TransactionDetails transaction) {
        // Show dialog with transaction details
        showDialog(transaction);
    }

    private void showDialog(TransactionDetails transaction) {

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.transactio_detail_dialog);

        ImageView image=dialog.findViewById(R.id.transImage);
        TextView from=dialog.findViewById(R.id.transFrom);
        TextView to=dialog.findViewById(R.id.transTo);
        TextView bigAmmount=dialog.findViewById(R.id.bigMoney);
        TextView money=dialog.findViewById(R.id.smallMoney);
        AppCompatButton cancelBtn=dialog.findViewById(R.id.gobackBtn);

        from.setText(transaction.getSenderEmail());
        to.setText(transaction.getReciverEmail());
        bigAmmount.setText(String.valueOf(transaction.getSendAmmount()));
        money.setText(String.valueOf(transaction.getSendAmmount()));

        switch (transaction.getType()) {
            case RECHARGE:
                image.setImageResource(R.drawable.recharge);
                break;
            case SEND_MONEY:
                image.setImageResource(R.drawable.send_money);
                break;
            case CASH_OUT:
                image.setImageResource(R.drawable.request);
                break;
            default:
                image.setImageResource(R.drawable.transfermoney);
        }


        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });









        dialog.show();


    }


}