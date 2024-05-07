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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bankee.R;
import com.example.bankee.activity.ForgetPass_Activity;
import com.example.bankee.activity.LoginActivity;
import com.example.bankee.activity.MyProfile_Activity;
import com.example.bankee.activity.NwePass_Activity;
import com.example.bankee.activity.SendMoneyActivity;
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
    RelativeLayout changePass;

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
        contactNumber=v.findViewById(R.id.contactNumber);
        change_pass=v.findViewById(R.id.change_pass);
        logout=v.findViewById(R.id.logout);
        changePass=v.findViewById(R.id.three);

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




        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Picasso.get().load(snapshot.child("imageLink").getValue(String.class)).into(imageView);
                userName.setText(snapshot.child("UserName").getValue(String.class));
                contactNumber.setText(snapshot.child("UserPhone").getValue(String.class));

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