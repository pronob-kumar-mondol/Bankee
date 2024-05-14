package com.example.bankee.activity;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bankee.Model.TranSactionType;
import com.example.bankee.Model.TransactionDetails;
import com.example.bankee.Model.UserDetails;
import com.example.bankee.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SendMoney_WithEmail extends AppCompatActivity {

    EditText ammount,email;
    ImageView ivBack,ivMenu;
    TextView tvTitle;
    AppCompatButton btn;
    DatabaseReference reference;
    FirebaseAuth fAuth;
    UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money_with_email);
        reference= FirebaseDatabase.getInstance().getReference("UserDetails");
        fAuth=FirebaseAuth.getInstance();

        email=findViewById(R.id.email);
        ammount=findViewById(R.id.ammount);
        btn=findViewById(R.id.btn);
        tvTitle=findViewById(R.id.tvTitle);
        ivBack=findViewById(R.id.ivBack);
        ivMenu=findViewById(R.id.ivMenu);


        //Appbar Setup
        tvTitle.setText("Send Money With Email");
        ivMenu.setVisibility(View.INVISIBLE);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        //Dialog Setup
        Dialog dialog = new Dialog(SendMoney_WithEmail.this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTrasferDialog();
            }
        });

        String scannedData = getIntent().getStringExtra("data");
        if (scannedData != null) {
            email.setText(scannedData);
        }
    }

    private void showTrasferDialog() {
        Dialog dialog = new Dialog(SendMoney_WithEmail.this);
        dialog.setContentView(R.layout.transfer_popup);

        String reciveremail=email.getText().toString();
        String ammounts=ammount.getText().toString();
        String userEmail=fAuth.getCurrentUser().getEmail().toString();

        TextView from=dialog.findViewById(R.id.from);
        TextView to=dialog.findViewById(R.id.to);
        TextView ammount=dialog.findViewById(R.id.ammount);
        TextView money=dialog.findViewById(R.id.money);
        AppCompatButton cancelBtn=dialog.findViewById(R.id.cancelBtn);
        AppCompatButton confirmBtn=dialog.findViewById(R.id.confirmBtn);

        from.setText(userEmail);
        to.setText(reciveremail);
        ammount.setText(ammounts);
        money.setText(ammounts);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sendAmmount=Integer.parseInt(ammount.getText().toString());
                String reciverEmail=email.getText().toString();

                if (isValidEmail(reciverEmail)) {
                    performTransactionWithReceiverEmail(reciverEmail, sendAmmount);
                    updateTransactionDetails(userEmail,reciveremail,sendAmmount);

                    dialog.dismiss();
                }else{
                    Toast.makeText(SendMoney_WithEmail.this, "Invalid Input Format", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void updateTransactionDetails(String userEmail, String reciveremail, int sendAmmount) {
        DatabaseReference transactionReference=FirebaseDatabase.getInstance().getReference("Transactions");
        transactionReference.push().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TransactionDetails transactionDetails=new TransactionDetails(userEmail,reciveremail,sendAmmount,System.currentTimeMillis(),snapshot.getKey(), TranSactionType.SEND_MONEY);
                snapshot.getRef().setValue(transactionDetails);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        startActivity(new Intent(SendMoney_WithEmail.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));

    }

    private void performTransactionWithReceiverEmail(String reciverEmail, int sendAmmount) {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("UserDetails");
        Query query=reference.orderByChild("userEmail").equalTo(reciverEmail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot: snapshot.getChildren()){
                        String reciverUID=userSnapshot.getKey();
                        String senderUID= getCurrentUserId();

                        performTransaction(senderUID,reciverUID,sendAmmount);

                    }
                }else {
                    Toast.makeText(SendMoney_WithEmail.this, "Email not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SendMoney_WithEmail.this, "Database Eror", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void performTransaction(String senderUID, String reciverUID, int sendAmmount) {

        reference.child(senderUID).child("CardDetails").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int currentBalance= Integer.parseInt(snapshot.child("balance").getValue().toString());
                currentBalance=currentBalance-sendAmmount;
                snapshot.child("balance").getRef().setValue(currentBalance);

                Toast.makeText(SendMoney_WithEmail.this, "User balance Updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.child(reciverUID).child("CardDetails").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int currentBalance= Integer.parseInt(snapshot.child("balance").getValue().toString());
                        currentBalance=currentBalance+sendAmmount;
                        snapshot.child("balance").getRef().setValue(currentBalance);
                        Toast.makeText(SendMoney_WithEmail.this, "Reciver balance Updated", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // The user is signed in
            return user.getUid();
        } else {
            // No user is signed in
            return null;
        }
    }

    private boolean isValidEmail(CharSequence reciverEmail) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(reciverEmail).matches();
    }


}