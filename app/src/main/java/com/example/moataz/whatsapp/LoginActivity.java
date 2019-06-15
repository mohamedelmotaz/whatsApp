package com.example.moataz.whatsapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

private EditText emailLogin,passwordLogin;
private TextView fogetPassword,needNewAccount;
private Button loginButton,phoneLogin;
private FirebaseAuth userAuth;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intializeField();
        progressDialog=new ProgressDialog(LoginActivity.this);
        userAuth=FirebaseAuth.getInstance();

        needNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToRegisterActivity();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        }
    private void loginUser() {
        String email=emailLogin.getText().toString();
        String userpassword=passwordLogin.getText().toString();
        if (TextUtils.isEmpty(email))
        {

            Toast.makeText(LoginActivity.this,"please enter Email...",Toast.LENGTH_LONG).show();

        }
        if (TextUtils.isEmpty(userpassword))
        {

            Toast.makeText(LoginActivity.this,"please enter Password...",Toast.LENGTH_LONG).show();

        }
        else
        {
            progressDialog.setTitle("loading");
            progressDialog.setMessage("wait please");
            progressDialog.setCanceledOnTouchOutside(true);

            progressDialog.show();
            userAuth.signInWithEmailAndPassword(email,userpassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful())
                       {
                           sendUserToMainActivity();
                           finish();
                           progressDialog.dismiss();

                       }
                       else
                       {progressDialog.dismiss();
                           String message=task.getException().toString();
                           AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this).setTitle("Warning")
                                   .setMessage("Eror : "+message);
                           builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                               }
                           });
                           builder.create();
                           builder.show();
                       }
                        }
                    });

        }
    }


    private void intializeField() {
        emailLogin=findViewById(R.id.e_mail);
        passwordLogin=findViewById(R.id.password);
        fogetPassword=findViewById(R.id.forget_password);
        needNewAccount=findViewById(R.id.new_account);
        loginButton=findViewById(R.id.login);
        phoneLogin=findViewById(R.id.login_phone);
    }



    private void sendUserToRegisterActivity() {
        Intent registerIntent=new Intent(LoginActivity.this,RegisterActivity.class);

        startActivity(registerIntent);

    }
    private void sendUserToMainActivity() {
        Intent loginIntent=new Intent(LoginActivity.this,MainActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loginIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
