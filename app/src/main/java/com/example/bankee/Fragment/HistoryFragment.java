package com.example.bankee.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bankee.Controller.TransactionHistoryAdapter;
import com.example.bankee.Model.TransactionDetails;
import com.example.bankee.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {
    RecyclerView recyclerView;
    TransactionDetails transactionModel;
    ;
    DatabaseReference reference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_history, container, false);

        ArrayList<TransactionDetails> datalist=new ArrayList<>();
        TransactionHistoryAdapter historyAdapter=new TransactionHistoryAdapter(getContext(),datalist);
        recyclerView.setAdapter(historyAdapter);

        reference= FirebaseDatabase.getInstance().getReference("Transactions");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    transactionModel=ds.getValue(TransactionDetails.class);
                    datalist.add(transactionModel);
                    historyAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        return v;
    }
}