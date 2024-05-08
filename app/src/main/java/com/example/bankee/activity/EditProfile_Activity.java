package com.example.bankee.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bankee.Model.UserDetails;
import com.example.bankee.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile_Activity extends AppCompatActivity {

    CircleImageView miniCamera,ImageView;
    ImageView ivBack,ivMenu;
    TextView tvTitle;
    Uri guri;
    AppCompatButton btn;
    EditText editName,editEmail,editNumber,editAddress;
    StorageReference storageReference;
    FirebaseAuth fAuth;
    DatabaseReference databaseReference;
    UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        miniCamera=findViewById(R.id.miniCamera);
        ImageView=findViewById(R.id.circleImageView);
        editName=findViewById(R.id.editFullname);
        editEmail=findViewById(R.id.editTextEmail);
        editNumber=findViewById(R.id.editPhonenumber);
        editAddress=findViewById(R.id.editAddress);
        btn=findViewById(R.id.btn);
        ivBack = findViewById(R.id.ivBack);
        ivMenu = findViewById(R.id.ivMenu);
        tvTitle = findViewById(R.id.tvTitle);


        fAuth=FirebaseAuth.getInstance();
        storageReference=FirebaseStorage.getInstance().getReference();

        String userId =fAuth.getUid().toString();
        databaseReference= FirebaseDatabase.getInstance().getReference("UserDetails").child(userId);


        ivMenu.setVisibility(View.GONE);
        tvTitle.setText("Edit Profile");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        miniCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (guri!=null){
                    String newName=editName.getText().toString();
                    String newEmail=editEmail.getText().toString();
                    String newNumber=editNumber.getText().toString();
                    String newAddress=editAddress.getText().toString();

                    storageReference=storageReference.child(System.currentTimeMillis()+".jpg");
                     storageReference.putFile(guri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    databaseReference.child("imgLink").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(EditProfile_Activity.this, "Successflly set link", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });

                        }
                    });


                    Map<String, Object> updates = new HashMap<>();
                    updates.put("name",newName);
                    updates.put("email",newEmail);
                    updates.put("phoneNumber",newNumber);
                    updates.put("address",newAddress);


                    databaseReference.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(EditProfile_Activity.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    });

                startActivity(new Intent(EditProfile_Activity.this, MyProfile_Activity.class));


                }






            }
        });



    }


    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    guri=uri;
                    ImageView.setImageURI(uri);
                }
            });
}