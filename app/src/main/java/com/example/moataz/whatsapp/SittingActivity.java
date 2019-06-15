package com.example.moataz.whatsapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SittingActivity extends AppCompatActivity {
private CircleImageView userprofileImg;
private EditText userNameSitting,userStatusSitting;
private Button updateButton;
FirebaseAuth userUth;
FirebaseUser currentUser;
    String userId;
DatabaseReference databaseReferencee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitting);
        intializeField();
        userUth=FirebaseAuth.getInstance();
        currentUser=userUth.getCurrentUser();
        userId=userUth.getCurrentUser().getUid();
        databaseReferencee=FirebaseDatabase.getInstance().getReference();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSitting();
            }
        });
        retrieveUserInfo();

    }

    private void intializeField() {
        userprofileImg=findViewById(R.id.image_profile_sitting);
        userNameSitting=findViewById(R.id.user_name_sitting);
        userStatusSitting=findViewById(R.id.user_status_sitting);
        updateButton=findViewById(R.id.update_button);



    }

  private void retrieveUserInfo() {
        databaseReferencee.child("userId").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ((dataSnapshot.exists())&&(dataSnapshot.hasChild("name"))&&(dataSnapshot.hasChild("image ")))
                {
                    String retrieveName=dataSnapshot.child("name").getValue().toString();
                    String retrieveStatus=dataSnapshot.child("statu").getValue().toString();
                    String retrieveImage=dataSnapshot.child("image").getValue().toString();
                    userNameSitting.setText(retrieveName);
                    userStatusSitting.setText(retrieveStatus);
                }
                else if((dataSnapshot.exists())&&(dataSnapshot.hasChild("name")))

                {
                    String retrieveName=dataSnapshot.child("name").getValue().toString();
                    String retrieveStatus=dataSnapshot.child("status").getValue().toString();

                    userNameSitting.setText(retrieveName);
                    userStatusSitting.setText(retrieveStatus);

                }
                else {
                    Toast.makeText(SittingActivity.this,"please set epdate to your profile",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    private void updateSitting() {
        String name=userNameSitting.getText().toString();
        String status=userStatusSitting.getText().toString();
        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(SittingActivity.this,"please enter name...",Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(status))
        {
            Toast.makeText(SittingActivity.this,"please enter status...",Toast.LENGTH_LONG).show();
        }
        else
        {

            HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("uId",userId);
                    hashMap.put("name",name);
                    hashMap.put("status",status);
                    databaseReferencee.child("userId").child(userId).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                sendUserToMainActivity();
                            }
                            else
                            {
                                String message=task.getException().toString();
                                Toast.makeText(SittingActivity.this,"Eror:"+message,Toast.LENGTH_LONG).show();

                            }
                        }
                    });


        }
    }

    private void sendUserToMainActivity() {
        Intent mainintetnt=new Intent(SittingActivity.this,MainActivity.class);
        mainintetnt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainintetnt);
        finish();
    }

}
