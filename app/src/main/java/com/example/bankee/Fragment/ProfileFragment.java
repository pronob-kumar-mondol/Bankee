package com.example.bankee.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bankee.Model.UserDetails;
import com.example.bankee.R;
import com.example.bankee.activity.ForgetPass_Activity;
import com.example.bankee.activity.LoginActivity;
import com.example.bankee.activity.MyProfile_Activity;
import com.example.bankee.activity.Notification_Activity;
import com.example.bankee.activity.NwePass_Activity;
import com.example.bankee.activity.SendMoneyActivity;
import com.example.bankee.activity.legalPolicies_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {
    CircleImageView imageView;
    TextView userName,contactNumber,forgot_pass,change_pass;
    ImageView ivBack,ivMenu;
    TextView tvTitle;
    RelativeLayout changePass,mainLayout,HistoryTransaction,legalPolicies,notification;
    ProgressBar progressBar;
    UserDetails userDetails;

    DatabaseReference reference;
    FirebaseAuth fAuth;

    RelativeLayout your_profile;
    TextView logout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_profile, container, false);

        forgot_pass=v.findViewById(R.id.forgot_pass);
        your_profile=v.findViewById(R.id.one);
        ivBack=v.findViewById(R.id.ivBack);
        ivMenu=v.findViewById(R.id.ivMenu);
        tvTitle=v.findViewById(R.id.tvTitle);
        imageView=v.findViewById(R.id.profile_pic);
        userName=v.findViewById(R.id.user_name);
        notification=v.findViewById(R.id.four);
        legalPolicies=v.findViewById(R.id.five);
        contactNumber=v.findViewById(R.id.contactNumber);
        change_pass=v.findViewById(R.id.change_pass);
        HistoryTransaction=v.findViewById(R.id.HistoryTransaction);
        logout=v.findViewById(R.id.logout);
        changePass=v.findViewById(R.id.three);
        progressBar=v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        mainLayout=v.findViewById(R.id.mainLayout);
        mainLayout.setVisibility(View.INVISIBLE);

        reference= FirebaseDatabase.getInstance().getReference("UserDetails");
        fAuth=FirebaseAuth.getInstance();

        String userId=fAuth.getUid().toString();

        ivBack.setVisibility(View.GONE);
        ivMenu.setVisibility(View.GONE);
        tvTitle.setText(R.string.profile);


        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NwePass_Activity.class));
            }
        });

        notification.setVisibility(View.GONE);



        legalPolicies.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), legalPolicies_Activity.class));
        });




        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                try {
                    userDetails=snapshot.getValue(UserDetails.class);
                    userName.setText(userDetails.getUserName());
                    contactNumber.setText(userDetails.getUserNumber());
                    Picasso.get().load(userDetails.getImageLink()).placeholder(R.drawable.user).into(imageView);
                    progressBar.setVisibility(View.GONE);
                    mainLayout.setVisibility(View.VISIBLE);
                }
                catch (Exception e) {
                    userName.setText("User Name");
                    contactNumber.setText("Contact Number");
                    mainLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    Picasso.get().load(R.drawable.user).into(imageView);
                }



            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        your_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MyProfile_Activity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showLogoutDialog();

            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), ForgetPass_Activity.class));
            }
        });




        return v;
    }

    private void showLogoutDialog() {
        Dialog dialog=new Dialog(this.getActivity());
        dialog.setContentView(R.layout.logout_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        AppCompatButton cancelBtn=dialog.findViewById(R.id.btn);
        TextView logout=dialog.findViewById(R.id.logout);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                dialog.dismiss();
            }
        });


    }
}