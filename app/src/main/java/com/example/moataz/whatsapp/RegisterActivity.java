package com.example.moataz.whatsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
EditText registerEmail,registerPassword;
Button createAccount;
TextView alreadyHaveAccount;
FirebaseAuth userAuth;
ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        intializeField();
        userAuth=FirebaseAuth.getInstance();
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToLoginActivity();

            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              createNewAccount();
            }
        });
    }

    private void createNewAccount() {
        String email=registerEmail.getText().toString();
        String password=registerPassword.getText().toString();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(RegisterActivity.this,"pleae enter email...",Toast.LENGTH_LONG).show();

        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(RegisterActivity.this,"pleae enter password...",Toast.LENGTH_LONG).show();

        }
        else
        {
            progressDialog.setTitle("creating new Account");
            progressDialog.setMessage("please wait, while we are creating new account for you");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();
            userAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                          if (task.isSuccessful())
                          { sendUserToLoginActivity();

                              Toast.makeText(RegisterActivity.this,"create new account successful",Toast.LENGTH_LONG).show();
                              progressDialog.dismiss();

                          }
                          else
                          {
                              String message=task.getException().toString();
                              Toast.makeText(RegisterActivity.this,"Error : "+message,Toast.LENGTH_LONG).show();
                              progressDialog.dismiss();
                          }
                        }
                    });

        }

    }

    private void intializeField() {
        progressDialog=new ProgressDialog(RegisterActivity.this);
        registerEmail=findViewById(R.id.e_mail_register);
        registerPassword=findViewById(R.id.password_register);
        createAccount=findViewById(R.id.login_register);
        alreadyHaveAccount=findViewById(R.id.new_account_register);

    }
    private void sendUserToLoginActivity()
    {
        Intent loginIntent=new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(loginIntent);
    }


}

