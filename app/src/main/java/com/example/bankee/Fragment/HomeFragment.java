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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bankee.R;
import com.example.bankee.activity.ContactsActivity;
import com.example.bankee.activity.MyProfile_Activity;
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



    SharedPreferences sharedPreferences;
    RelativeLayout sendMoney;
    RelativeLayout contacts;
    TextView user_name;
    ImageView profile_pic;

    DatabaseReference reference;
    FirebaseAuth fAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        sendMoney=v.findViewById(R.id.sendMoney);
        profile_pic=v.findViewById(R.id.profile_pic);
        contacts=v.findViewById(R.id.contacts);
        user_name=v.findViewById(R.id.user_name);
        fAuth=FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference("UserDetails");
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(requireContext());



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


        return v;
    }

    private void readData(String userId) {





        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String name=snapshot.child("UserName").getValue(String.class);
                    Picasso.get().load(snapshot.child("imageLink").getValue(String.class)).into(profile_pic);
                    user_name.setText(name);
                    Log.d("HomeFragment", "User name: " + name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("HomeFragment", "Error reading data from Firebase: " + error.getMessage());

            }
        });

//        reference.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//
//                if (task.isSuccessful()){
//                    if (task.getResult().exists()){
//                        DataSnapshot snapshot=task.getResult();
//                        String name=String.valueOf(snapshot.child("UserName").getValue());
//                        user_name.setText(name);
//
//                    }
//                }
//            }
//        });
    }
}