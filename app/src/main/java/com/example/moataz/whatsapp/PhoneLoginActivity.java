package com.example.moataz.whatsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneLoginActivity extends AppCompatActivity {
private EditText inputphoneNumber,verifyCode;
private Button sendPhoneNumber,verify;
private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
private String mVerificationId;
private FirebaseAuth mAuth;
private PhoneAuthProvider.ForceResendingToken mResendToken;
private ProgressDialog progressDialog;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
intializeField();
progressDialog=new ProgressDialog(this);
mAuth=FirebaseAuth.getInstance();
sendPhoneNumber.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        String myphoneNumber=inputphoneNumber.getText().toString();
        if(TextUtils.isEmpty(myphoneNumber))
        {
            Toast.makeText(PhoneLoginActivity.this,"please enter phone number",Toast.LENGTH_SHORT).show();
        }
        else
        {
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("wait for sending code plaese");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    myphoneNumber,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    PhoneLoginActivity.this,               // Activity (for callback binding)
                    callbacks);        // OnVerificationStateChangedCallbacks

        }

    }
});
callbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
    @Override
    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

signInWithPhoneAuthCredential(phoneAuthCredential);
    }

    @Override
    public void onVerificationFailed(FirebaseException e) {
        progressDialog.dismiss();
        Toast.makeText(PhoneLoginActivity.this,"invalid phone number",Toast.LENGTH_SHORT).show();
        inputphoneNumber.setVisibility(View.VISIBLE);
        sendPhoneNumber.setVisibility(View.VISIBLE);
        verifyCode.setVisibility(View.INVISIBLE);
        verify.setVisibility(View.INVISIBLE);


    }
    @Override
    public void onCodeSent(String verificationId,
                           PhoneAuthProvider.ForceResendingToken token) {
progressDialog.dismiss();
        // Save verification ID and resending token so we can use them later
        mVerificationId = verificationId;
        mResendToken = token;
        Toast.makeText(PhoneLoginActivity.this,"code has been send",Toast.LENGTH_SHORT).show();
        inputphoneNumber.setVisibility(View.INVISIBLE);
        sendPhoneNumber.setVisibility(View.INVISIBLE);
        verifyCode.setVisibility(View.VISIBLE);
        verify.setVisibility(View.VISIBLE);
        // ...

    }
};
verify.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        inputphoneNumber.setVisibility(View.INVISIBLE);
        sendPhoneNumber.setVisibility(View.INVISIBLE);
        String code=verifyCode.getText().toString();
        if(TextUtils.isEmpty(code))
        {
            Toast.makeText(PhoneLoginActivity.this,"please enter the code",Toast.LENGTH_SHORT).show();

        }
        else
        {  progressDialog.setTitle("Loading");
            progressDialog.setMessage("wait for sending code plaese");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            signInWithPhoneAuthCredential(credential);
        }
    }
});
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            sendUserToMainActivity();

                        } else {
String meesage=task.getException().toString();
Toast.makeText(PhoneLoginActivity.this,meesage,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                );
}

    private void sendUserToMainActivity() {
        Intent intent=new Intent(PhoneLoginActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    private void intializeField() {
        inputphoneNumber=findViewById(R.id.phone_number);
        verifyCode=findViewById(R.id.code_verfiey);
        sendPhoneNumber=findViewById(R.id.send_phone);
        verify=findViewById(R.id.send_code);
    }

}
