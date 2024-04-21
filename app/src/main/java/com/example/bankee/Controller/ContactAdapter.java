package com.example.bankee.Controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankee.Model.ContactModel;
import com.example.bankee.R;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    Context context;
    Activity activity;
    ArrayList<ContactModel> datalist;

    public ContactAdapter(Activity activity,ArrayList<ContactModel>datalist){
        this.activity=activity;
        this.datalist=datalist;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_card,parent,false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactViewHolder holder, int position) {
        holder.contactName.setText(datalist.get(position).  getContactName());
        holder.contactNumber.setText(datalist.get(position).getNumber());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView contactName,contactNumber;
        ImageView image;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            contactName=itemView.findViewById(R.id.contactName);
            contactNumber=itemView.findViewById(R.id.contactNumber);
            image=itemView.findViewById(R.id.action_image);

        }
    }
}
