package com.example.bankee.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankee.Model.TransactionDetails;
import com.example.bankee.R;

import java.util.ArrayList;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder> {

    Context context;
    ArrayList<TransactionDetails> datalist;

    public TransactionHistoryAdapter(Context context, ArrayList<TransactionDetails> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public TransactionHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.history_card,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHistoryAdapter.ViewHolder holder, int position) {
        holder.trasactionType.setText(datalist.get(position).getType().toString());
        holder.transactionID.setText(datalist.get(position).getTransactionId());
        holder.amount.setText(String.valueOf(datalist.get(position).getSendAmmount()));
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView transactionID,trasactionType,amount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionID=itemView.findViewById(R.id.transactionID);
            trasactionType=itemView.findViewById(R.id.trasactionType);
            amount=itemView.findViewById(R.id.amount);

        }
    }
}
