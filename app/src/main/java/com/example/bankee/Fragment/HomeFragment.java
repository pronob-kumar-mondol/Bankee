package com.example.bankee.Fragment;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bankee.Model.CardDetails;
import com.example.bankee.Model.UserDetails;
import com.example.bankee.R;
import com.example.bankee.activity.CashOut_Activity;
import com.example.bankee.activity.ContactsActivity;
import com.example.bankee.activity.MyProfile_Activity;
import com.example.bankee.activity.Recharge_Activity;
import com.example.bankee.activity.SendMoneyActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;




public class HomeFragment extends Fragment {

    RelativeLayout sendMoney,cashOut,mainLayout,recharge;
    RelativeLayout contacts;
    TextView user_name,holder_balance,holder_name,card_no,expire_date;
    ImageView profile_pic,cardType;

    UserDetails userDetails;
    DatabaseReference reference;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        mainLayout=v.findViewById(R.id.layout);
        mainLayout.setVisibility(View.INVISIBLE);
        progressBar=v.findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        sendMoney=v.findViewById(R.id.sendMoney);
        profile_pic=v.findViewById(R.id.profile_pic);
        contacts=v.findViewById(R.id.contacts);
        user_name=v.findViewById(R.id.user_name);
        holder_balance=v.findViewById(R.id.balance);
        holder_name=v.findViewById(R.id.holder_name);
        card_no=v.findViewById(R.id.card_no);
        expire_date=v.findViewById(R.id.expire_date);
        cashOut=v.findViewById(R.id.cashOut);
        recharge=v.findViewById(R.id.recharge);
        cardType=v.findViewById(R.id.cardType);
        fAuth=FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference("UserDetails");

        String userId =fAuth.getUid().toString();
        readData(userId);




       sendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SendMoneyActivity.class);
                startActivity(intent);
            }
        });

       contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ContactsActivity.class));
                
            }
        });


       profile_pic.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(getActivity(), MyProfile_Activity.class);
               startActivity(intent);
           }
       });
       user_name.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent=new Intent(getActivity(), MyProfile_Activity.class);
               startActivity(intent);
           }
       });

       cashOut.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getActivity(), CashOut_Activity.class));
           }
       });

       recharge.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getActivity(), Recharge_Activity.class));
           }
       });

        return v;
    }

    private void readData(String userId) {


        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                try {
                    userDetails=snapshot.getValue(UserDetails.class);
                    CardDetails cardDetails=snapshot.child("CardDetails").getValue(CardDetails.class);
                    user_name.setText(userDetails.getUserName());
                    holder_balance.setText(String.valueOf(cardDetails.getBalance()));
                    holder_name.setText(userDetails.getUserName());
                    card_no.setText(cardDetails.getCardNumber());
                    expire_date.setText(cardDetails.getExpDate());
                    profile_pic.setImageResource(userDetails.getImageLink().hashCode());
                    Picasso.get().load(userDetails.getImageLink()).placeholder(R.drawable.user).into(profile_pic);

                    if (cardDetails.getCardType().equals("Visa")) {
                        cardType.setImageResource(R.drawable.visa);
                    }else {
                        cardType.setImageResource(R.drawable.mastercard_logo);
                    }


                }catch (Exception e){
                    holder_balance.setText("0.0");
                    card_no.setText("0000 0000 0000 0000");
                    expire_date.setText("00/00");
                    holder_name.setText(userDetails.getUserName());
                    Picasso.get().load(R.drawable.user).into(profile_pic);
                    cardType.setVisibility(View.INVISIBLE);
                }


                    progressBar.setVisibility(View.INVISIBLE);
                    mainLayout.setVisibility(View.VISIBLE);
                    Log.d("HomeFragment", "User name: " + userDetails.getUserName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("HomeFragment", "Error reading data from Firebase: " + error.getMessage());

            }
        });


    }
}