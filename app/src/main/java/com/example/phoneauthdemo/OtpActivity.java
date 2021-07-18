package com.example.phoneauthdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends BaseActivity{

    String mobile;
    String verificationId;
    String otpCode;
    FirebaseAuth mAuth;
    EditText otpeditText;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);


        mobile = getIntent().getStringExtra("mobileNo");
        mAuth = FirebaseAuth.getInstance();
        initiateOtp();
        otpeditText = findViewById(R.id.editTextTextOTp);
        btn = findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otpeditText.getText().toString().isEmpty() | otpeditText.getText().toString().length() != 6) {
                    Toast.makeText(OtpActivity.this, "Invalid otp", Toast.LENGTH_LONG).show();
                } else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otpCode);
                    signInWithPhoneAuthCredential(credential);

                }
            }
        });


    }

    private void initiateOtp() {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mobile)       // Phone number to verify
                        .setTimeout(30L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                phoneAuthCredential.getSmsCode();
                                otpeditText.setText(phoneAuthCredential.getSmsCode());
                                otpCode = phoneAuthCredential.getSmsCode();
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(OtpActivity.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
                                ;
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                forceResendingToken.toString();
                                verificationId = s;


                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    task.getResult();
                    Intent i = new Intent(OtpActivity.this, WelcomeActivity.class);
                    i.putExtra("result", task.getResult().getUser());
                    startActivity(i);
                } else {
                    Toast.makeText(OtpActivity.this, "sign in failed", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}