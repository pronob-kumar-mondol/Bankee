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

import de.hdodenhof.circleimageview.CircleImageView;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder> {

    Context context;
    ArrayList<TransactionDetails> datalist;
    private OnTransactionClickListener clickListener;

    public TransactionHistoryAdapter(Context context, ArrayList<TransactionDetails> datalist, OnTransactionClickListener clickListener) {
        this.context = context;
        this.datalist = datalist;
        this.clickListener = clickListener;
    }

    public interface OnTransactionClickListener {
        void onTransactionClick(TransactionDetails transaction);
    }


    public void setOnTransactionClickListener(OnTransactionClickListener listener) {
        this.clickListener = listener;
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
        holder.reciver_cred.setText(datalist.get(position).getReciverEmail());

        //Set transaction pic basen on type

        switch (datalist.get(position).getType()){
            case RECHARGE:
                holder.transaction_pic.setImageResource(R.drawable.recharge);
                break;
            case SEND_MONEY:
                holder.transaction_pic.setImageResource(R.drawable.send_money);
                break;
            case CASH_OUT:
                holder.transaction_pic.setImageResource(R.drawable.request);
                break;
            default:
                holder.transaction_pic.setImageResource(R.drawable.transfermoney);
        }

        holder.itemView.setOnClickListener(view -> {
            if (clickListener!=null){
                clickListener.onTransactionClick(datalist.get(position));
            }

        });


    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView transactionID,trasactionType,amount,reciver_cred;
        CircleImageView transaction_pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionID=itemView.findViewById(R.id.transactionID);
            trasactionType=itemView.findViewById(R.id.trasactionType);
            amount=itemView.findViewById(R.id.amount);
            reciver_cred=itemView.findViewById(R.id.reciver_cred);
            transaction_pic=itemView.findViewById(R.id.circleImageView2);

        }
    }
}
