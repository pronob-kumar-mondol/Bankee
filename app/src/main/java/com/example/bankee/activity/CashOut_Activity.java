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
    String emailPattern="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

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
        dialog.setContentView(R.layout.recharge_dialog);


        ImageView image=dialog.findViewById(R.id.image);
        TextView transferType=dialog.findViewById(R.id.to0);
        TextView from=dialog.findViewById(R.id.from);
        TextView to=dialog.findViewById(R.id.to);
        TextView ammounts=dialog.findViewById(R.id.ammount);
        TextView money=dialog.findViewById(R.id.money);
        AppCompatButton cancelBtn=dialog.findViewById(R.id.cancelBtn);
        AppCompatButton confirmBtn=dialog.findViewById(R.id.confirmBtn);

        String reciveremail=email.getText().toString();
        String reciverAmmount=ammount.getText().toString();
        String userEmail= fAuth.getCurrentUser().getEmail().toString();

        if (reciveremail.isEmpty() || reciverAmmount.isEmpty() || !reciveremail.matches(emailPattern) || Integer.parseInt(reciverAmmount) <= 0 || Integer.parseInt(reciverAmmount) > 10000 || fAuth.getCurrentUser().getEmail().toString().matches(reciveremail)) {
            email.setError("Please enter valid email");
            ammount.setError("Please enter valid ammount");
            return;
        }else{

            from.setText(userEmail);
            to.setText(reciveremail);
            ammounts.setText(reciverAmmount);
            money.setText(reciverAmmount);
            image.setImageResource(R.drawable.request);
            transferType.setText(R.string.cash_out);

            dialog.show();
        }



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
                showCofirmDialog();
                updateUserBalance(reciverAmmount);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showCancelDialog();

            }
        });

    }

    private void showCancelDialog() {
        Dialog dialog=new Dialog(CashOut_Activity.this);
        dialog.setContentView(R.layout.failed_transfer);
        dialog.show();

        AppCompatButton gobackBtn=dialog.findViewById(R.id.gobackBtn);
        TextView onCancelBtn=dialog.findViewById(R.id.onCancel);

        gobackBtn.setOnClickListener(v -> {
            dialog.dismiss();
        });

        onCancelBtn.setOnClickListener(v -> {
            dialog.dismiss();
            startActivity(new Intent(CashOut_Activity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
        });
    }

    private void showCofirmDialog() {


        Dialog dialog=new Dialog(CashOut_Activity.this);
        dialog.setContentView(R.layout.sucsessful_transfer);
        dialog.show();

        AppCompatButton btn= dialog.findViewById(R.id.gobackBtn);

        btn.setOnClickListener(v -> {
            dialog.dismiss();
            startActivity(new Intent(CashOut_Activity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CashOut_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}