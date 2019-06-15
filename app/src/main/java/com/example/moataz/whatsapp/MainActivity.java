package com.example.moataz.whatsapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
private Toolbar toolbar;
private TabLayout tabLayout;
private ViewPager viewPager;
 AccerosPagerAdapter accerosPagerAdapter;
private FirebaseUser currentUser;
private FirebaseAuth userAuth;
private DatabaseReference dataref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.main_toolbar);
        tabLayout=findViewById(R.id.main_tablayout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("WhatsApp");
        getSupportActionBar();
        viewPager=findViewById(R.id.main_viewpager);
        accerosPagerAdapter=new AccerosPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(accerosPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        userAuth=FirebaseAuth.getInstance();
        currentUser=userAuth.getCurrentUser();
        dataref=FirebaseDatabase.getInstance().getReference();



    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser==null)
        {
            sendUserToLoginActivity();

        }
        else
        {
verfyUserExestance();
        }

    }

    private void verfyUserExestance() {
        String currentUserId=userAuth.getCurrentUser().getUid();
        dataref.child("userId").child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if((dataSnapshot.child("name").exists()))
                {

                    Toast.makeText(MainActivity.this,"welcome "+dataSnapshot.child("name").getValue().toString(),Toast.LENGTH_LONG).show();

                }
                else
                {
                    sendUserToSittingActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void sendUserToLoginActivity() {
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {

case R.id.item_one:

    break;
            case R.id.item_sitting:
                sendUserToSittingActivity1();
                break;
            case R.id.item_logout:
                userAuth.signOut();
                sendUserToLoginActivity();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendUserToSittingActivity1() {
        Intent settingIntent=new Intent(MainActivity.this,SittingActivity.class);
        startActivity(settingIntent);

    }

    private void sendUserToSittingActivity() {
        Intent settingIntent=new Intent(MainActivity.this,SittingActivity.class);
        settingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(settingIntent);
        finish();

    }


}
