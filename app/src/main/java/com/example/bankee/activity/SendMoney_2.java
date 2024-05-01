package com.example.bankee.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bankee.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class SendMoney_2 extends AppCompatActivity {
    ImageView ivMenu, ivBack;
    TextView app_title;
    TextView name,number;
    EditText wantedAmmount,reciverEmail;
    AppCompatButton btn;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money2);

        // Retrieve contact details from intent extras
        Intent intent = getIntent();
        String contactName = intent.getStringExtra("contactName");
        String contactNumber = intent.getStringExtra("contactNumber");

        ivMenu=findViewById(R.id.ivMenu);
        ivBack=findViewById(R.id.ivBack);
        app_title=findViewById(R.id.tvTitle);
        wantedAmmount = findViewById(R.id.editText);
        reciverEmail = findViewById(R.id.editText2);
        btn = findViewById(R.id.btn);
        reference= FirebaseDatabase.getInstance().getReference("UserDetails");


        app_title.setText("Send Money");
        ivMenu.setVisibility(View.GONE);

        // Set contact details to views
        name=findViewById(R.id.textView);
        number=findViewById(R.id.textView2);

        name.setText(contactName);
        number.setText(contactNumber);



        int wanted_ammount=Integer.parseInt(wantedAmmount.getText().toString());
        String reciver_Email=reciverEmail.getText().toString();


        //Querry to find reciverUid
        Query reciverEmailQuery = reference.orderByChild("UserEmail").equalTo(reciver_Email);

        reciverEmailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {

                        String reciverUID=childSnapshot.getKey();
                        String reciver_Ammount = childSnapshot.child("UserAmmount").getValue(String.class);

                        DatabaseReference reciverReference=reference.child(reciverUID).child("UserAmmount");
                        reciverReference.runTransaction(new Transaction.Handler() {
                            @NonNull
                            @Override
                            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                                Integer currentBalance = currentData.getValue(Integer.class);
                                if (currentBalance!=null && currentBalance >= wanted_ammount) {
                                    currentData.setValue(currentBalance - wanted_ammount);
                                    return Transaction.success(currentData);
                                }

                                return Transaction.abort();
                            }

                            @Override
                            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });












        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }


}