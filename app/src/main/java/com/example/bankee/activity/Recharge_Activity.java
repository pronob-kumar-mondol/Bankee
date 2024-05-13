package com.example.bankee.activity;

import android.annotation.SuppressLint;
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

import com.example.bankee.Model.CardDetails;
import com.example.bankee.Model.TranSactionType;
import com.example.bankee.Model.TransactionDetails;
import com.example.bankee.Model.UserDetails;
import com.example.bankee.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Recharge_Activity extends AppCompatActivity {
    ImageView ivBack,ivMenu;
    TextView tvTitle;
    EditText number,ammount;
    AppCompatButton btn;
    FirebaseAuth fAuth;
    DatabaseReference userReference;
    String phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        ivBack = findViewById(R.id.ivBack);
        ivMenu = findViewById(R.id.ivMenu);
        tvTitle = findViewById(R.id.tvTitle);

        number = findViewById(R.id.editPhonenumber);
        ammount = findViewById(R.id.ammount);
        btn = findViewById(R.id.btn);

        fAuth=FirebaseAuth.getInstance();
        userReference=FirebaseDatabase.getInstance().getReference("UserDetails").child(fAuth.getCurrentUser().getUid());


        ivMenu.setVisibility(View.GONE);
        tvTitle.setText("Recharge");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (number.getText().toString().isEmpty() || ammount.getText().toString().isEmpty()) {
                    number.setError("Please enter valid number");
                    ammount.setError("Please enter valid ammount");
                }else {
                    showRechargeDialog();
                }

            }
        });





    }

    private void showRechargeDialog() {
        Dialog dialog = new Dialog(Recharge_Activity.this);
        dialog.setContentView(R.layout.recharge_dialog);

        TextView from=dialog.findViewById(R.id.from);
        TextView to=dialog.findViewById(R.id.to);
        TextView ammountS=dialog.findViewById(R.id.ammount);
        TextView money=dialog.findViewById(R.id.money);
        AppCompatButton cancelBtn=dialog.findViewById(R.id.cancelBtn);
        AppCompatButton confirmBtn=dialog.findViewById(R.id.confirmBtn);

        String reciverNumber =number.getText().toString();
        int rechargeAmmount = Integer.parseInt(ammount.getText().toString());


        if (reciverNumber.isEmpty() || rechargeAmmount <= 0) {

            Toast.makeText(Recharge_Activity.this, "Please enter valid number", Toast.LENGTH_SHORT).show();

        }else{
            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserDetails userDetails = snapshot.getValue(UserDetails.class);
                    phoneNumber = userDetails.getUserNumber();
                    from.setText(phoneNumber);
                    to.setText(reciverNumber);
                    ammountS.setText(String.valueOf(rechargeAmmount));
                    money.setText(String.valueOf(rechargeAmmount));
                    dialog.show();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            UserDetails userDetails = snapshot.getValue(UserDetails.class);
                            CardDetails cardDetails = snapshot.child("CardDetails").getValue(CardDetails.class);

                            updateUserBalance(cardDetails.getBalance(), rechargeAmmount);
                            preformRecharge(reciverNumber,phoneNumber,rechargeAmmount);
                            Toast.makeText(Recharge_Activity.this, "RECHARGING Successful", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });

        }




    }

    private void updateUserBalance(int userBalance, int rechargeAmmount) {

        userBalance = userBalance - rechargeAmmount;
        userReference.child("CardDetails").child("balance").setValue(userBalance);


    }

    private void preformRecharge(String reciverNumber, String phoneNumber, int rechargeAmmount) {

        DatabaseReference transactionReference=FirebaseDatabase.getInstance().getReference("Transactions");
        transactionReference.push().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                TransactionDetails transactionDetails=new TransactionDetails(phoneNumber,reciverNumber,rechargeAmmount,System.currentTimeMillis(),snapshot.getKey(), TranSactionType.RECHARGE);
                snapshot.getRef().setValue(transactionDetails);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        startActivity(new Intent(Recharge_Activity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}