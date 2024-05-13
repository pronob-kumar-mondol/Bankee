package com.example.bankee.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bankee.Model.TranSactionType;
import com.example.bankee.Model.TransactionDetails;
import com.example.bankee.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class CashOut_Activity extends AppCompatActivity {
    ImageView ivBack,ivMenu;
    TextView tvTitle;
    EditText ammount,email;
    AppCompatButton btn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_out);

        ivBack = findViewById(R.id.ivBack);
        ivMenu = findViewById(R.id.ivMenu);
        tvTitle = findViewById(R.id.tvTitle);

        ammount = findViewById(R.id.ammount);
        email = findViewById(R.id.email);
        btn = findViewById(R.id.btn);
        fAuth=FirebaseAuth.getInstance();

        ivMenu.setVisibility(View.GONE);
        tvTitle.setText("Cash Out");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showCashOutDialog();
            }
        });


    }

    private void showCashOutDialog() {
        Dialog dialog = new Dialog(CashOut_Activity.this);
        dialog.setContentView(R.layout.cashout_dialog);


        TextView from=dialog.findViewById(R.id.from);
        TextView to=dialog.findViewById(R.id.to);
        TextView ammounts=dialog.findViewById(R.id.ammount);
        TextView money=dialog.findViewById(R.id.money);
        AppCompatButton cancelBtn=dialog.findViewById(R.id.cancelBtn);
        AppCompatButton confirmBtn=dialog.findViewById(R.id.confirmBtn);

        String reciveremail=email.getText().toString();
        String reciverAmmount=ammount.getText().toString();
        String userEmail= fAuth.getCurrentUser().getEmail().toString();

        from.setText(userEmail);
        to.setText(reciveremail);
        ammounts.setText(reciverAmmount);
        money.setText(reciverAmmount);

        dialog.show();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performCashOutTransaction(userEmail,reciveremail,reciverAmmount);
                dialog.dismiss();
            }
        });


    }

    private void performCashOutTransaction(String userEmail, String reciveremail, String reciverAmmount) {
        DatabaseReference transactionReference= FirebaseDatabase.getInstance().getReference("Transactions");

        transactionReference.push().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String transactionId=snapshot.getKey();
                TransactionDetails transactionDetails=new TransactionDetails(userEmail,reciveremail,Integer.parseInt(reciverAmmount),System.currentTimeMillis(),snapshot.getKey(), TranSactionType.CASH_OUT);
                snapshot.getRef().setValue(transactionDetails);
                Toast.makeText(CashOut_Activity.this, "Transaction Successful", Toast.LENGTH_SHORT).show();
                updateUserBalance(reciverAmmount);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CashOut_Activity.this, "something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void updateUserBalance(String reciverAmmount) {

        DatabaseReference userReference= FirebaseDatabase.getInstance().getReference("UserDetails").child(fAuth.getCurrentUser().getUid()).child("CardDetails");
        userReference.child("balance").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int currentBalance= Integer.parseInt(snapshot.getValue().toString());
                currentBalance=currentBalance-Integer.parseInt(reciverAmmount);
                snapshot.getRef().setValue(currentBalance);
                startActivity(new Intent(CashOut_Activity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CashOut_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}