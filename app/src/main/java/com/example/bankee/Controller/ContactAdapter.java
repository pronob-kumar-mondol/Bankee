package com.example.bankee.Controller;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankee.R;
import com.example.bankee.activity.SendMoney_2;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private Cursor cursor;
    private Context context;


    public ContactAdapter(Context context) {
        this.context = context;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_card, parent, false);
        return new ContactAdapter.ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactViewHolder holder, int position) {
        if (cursor != null && cursor.moveToPosition(position)) {
            final int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);


            if (nameIndex != -1 && numberIndex != -1) {
                String name = cursor.getString(nameIndex);
                String number = cursor.getString(numberIndex);
                holder.bind(name, number);
            } else {
                // Log or handle the case where the required columns are not found in the cursor
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cursor!=null && cursor.moveToPosition(holder.getAdapterPosition())){
                        // Retrieve data from the cursor
                        String name = cursor.getString(nameIndex);
                        String number = cursor.getString(numberIndex);

                        // Create Intent to start the new activity
                        Intent intent = new Intent(context, SendMoney_2.class);
                        // Pass data to the new activity using Intent extras
                        intent.putExtra("contactName", name);
                        intent.putExtra("contactNumber", number);
                        // Start the new activity
                        context.startActivity(intent);
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView numberTextView;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.contactName);
            numberTextView = itemView.findViewById(R.id.contactNumber);
        }

        public void bind(String name, String number) {
            nameTextView.setText(name);
            numberTextView.setText(number);
        }
    }
}
