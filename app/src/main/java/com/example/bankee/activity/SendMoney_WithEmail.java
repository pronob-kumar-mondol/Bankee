package com.example.bankee.activity;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.annotation.NonNull;
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

import com.example.bankee.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SendMoney_WithEmail extends AppCompatActivity {

    EditText ammount,email;
    ImageView ivBack,ivMenu;
    TextView tvTitle;
    AppCompatButton btn;
    DatabaseReference reference;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money_with_email);
        reference= FirebaseDatabase.getInstance().getReference("UserDetails");
        fAuth=FirebaseAuth.getInstance();

        email=findViewById(R.id.editText2);
        ammount=findViewById(R.id.editText);
        btn=findViewById(R.id.btn);
        tvTitle=findViewById(R.id.tvTitle);
        ivBack=findViewById(R.id.ivBack);
        ivMenu=findViewById(R.id.ivMenu);

        tvTitle.setText("Send Money With Email");
        ivMenu.setVisibility(View.INVISIBLE);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        Dialog dialog = new Dialog(SendMoney_WithEmail.this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showTrasferDialog();



            }
        });
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
                String userID=fAuth.getCurrentUser().getUid();
                int sendAmmount=Integer.parseInt(ammount.getText().toString());
                String reciverEmail=email.getText().toString();
                performSenderTransaction(userID,sendAmmount,reciverEmail);
                finish();
                dialog.dismiss();

            }
        });
    }


    private void performSenderTransaction(String userID, int sendAmmount,String reciverEmail) {
        reference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Object balanceObj = snapshot.child("userBalance").getValue();
                if (balanceObj != null) {
                    int balance = Integer.parseInt(balanceObj.toString());
                    int newBalance = balance - sendAmmount;
                    if (balance>sendAmmount) {
                        reference.child(userID).child("userBalance").setValue(newBalance);
                        performReciverTransaction(sendAmmount,reciverEmail);
                        Toast.makeText(SendMoney_WithEmail.this, "Transaction Successful", Toast.LENGTH_SHORT).show();
                        Log.d("UID", "Sender's UID: " + userID);
                        finish();
                    }else{
                        Toast.makeText(SendMoney_WithEmail.this, "Not Enough Balance", Toast.LENGTH_SHORT).show();
                        Log.d("UID", "Not Enough Balance for user: " + userID);
                    }
                }else {
                    Log.d("UID", "UserBalance is null for user: " + userID);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void performReciverTransaction(int sendAmmount, String reciverEmail) {
        Query query=reference.orderByChild("UserEmail").equalTo(reciverEmail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    String reciverUid=ds.getKey();
                    Object balanceObj = ds.child("userBalance").getValue();
                    if (balanceObj != null) {
                        int reciverBalance = Integer.parseInt(Objects.requireNonNull(balanceObj).toString());
                        int newBalance=reciverBalance+sendAmmount;

                        if(reciverUid!=null){
                            reference.child(reciverUid).child("userBalance").setValue(newBalance);

                            Log.d("UID", "Sagor's UID: " + reciverUid);
                        }else{
                            Log.d("UID", "No user found with the useremail"+ reciverUid);
                        }
                    }else {
                        Log.d("UID", "UserBalance is null for user: " + reciverUid);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SendMoney_WithEmail.this, "For some reason Transaction Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}