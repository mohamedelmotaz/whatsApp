package com.example.moataz.whatsapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
private Toolbar toolbar;
private TabLayout tabLayout;
private ViewPager viewPager;
 AccerosPagerAdapter accerosPagerAdapter;
private FirebaseUser currentUser;
FirebaseAuth userAuth;
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
        //currentUser=userAuth.getCurrentUser();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser==null)
        {
            sendUserToLoginActivity();
        }
    }

    private void sendUserToLoginActivity() {
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }

}
