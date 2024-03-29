package com.example.psdroid.ui.forget_password;
//Import Class
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.chaos.view.PinView;
import com.example.psdroid.R;
import com.example.psdroid.ui.login.LoginActivity;
import com.example.psdroid.ui.login.UserHeplerClass;
import com.example.psdroid.ui.register.AccountCreatedActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;
// Verify OPT Activity
public class VerifyOTP extends AppCompatActivity {
    PinView pinview;
    String codebysystem;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    String WhatToDo;
    ImageView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_t_p);
        pinview = findViewById(R.id.pinview);
        cancel = findViewById(R.id.cross);
        String _user = getIntent().getStringExtra("user");
        String _name = getIntent().getStringExtra("name");
        String _mail = getIntent().getStringExtra("mail");
        String _mobile = getIntent().getStringExtra("mob");
        String _pass = getIntent().getStringExtra("pass");
        String _cpass = getIntent().getStringExtra("cpass");
        WhatToDo = getIntent().getStringExtra("whatToDo");
        cancel.setOnClickListener(view -> {
            startActivity(new Intent(VerifyOTP.this, LoginActivity.class));
            finish();
        });
        sendVerificationCodeToUser(_mobile);
    }

    private void sendVerificationCodeToUser(String mobile) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mobile)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codebysystem = s;
                }
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        String code = phoneAuthCredential.getSmsCode();
                        if(code!=null){
                            pinview.setText(code);
                            verifyCode(code);
                        }
                }
                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    public void callnextscreenfromotp(View view){
        String code = pinview.getText().toString();
        if(!code.isEmpty()){
            verifyCode(code);
        }
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codebysystem,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        if(("updateData").equalsIgnoreCase(WhatToDo)){
                            updateOldUserData();
                        }
                        else{
                            storeNewUser();
                        }
                    }
                    else {
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(this, "Verification Not Completed! Try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateOldUserData() {
        String _mobile = getIntent().getStringExtra("mob");
        String _user = getIntent().getStringExtra("user");
        String _name = getIntent().getStringExtra("name");
        Intent intent = new Intent(getApplicationContext(), SetPassword.class);
        intent.putExtra("mob",_mobile);
        intent.putExtra("name",_name);
        intent.putExtra("user",_user);
        startActivity(intent);
        finish();
    }
    private void storeNewUser() {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");
        String _user = getIntent().getStringExtra("user");
        String _name = getIntent().getStringExtra("name");
        String _mail = getIntent().getStringExtra("mail");
        String _mobile = getIntent().getStringExtra("mob");
        String _pass = getIntent().getStringExtra("pass");
        String _cpass = getIntent().getStringExtra("cpass");
        System.out.println("Storing Data");
        UserHeplerClass heplerClass = new UserHeplerClass(_user,_name,_mail,_mobile,_pass,_cpass);
        reference.child(_user).setValue(heplerClass);
        Intent intent = new Intent(getApplicationContext(), AccountCreatedActivity.class);
        System.out.println("After storing");
        Toast.makeText(getApplicationContext(), "Verification Completed!", Toast.LENGTH_SHORT).show();
        intent.putExtra("user",_user);
        intent.putExtra("mob",_mobile);
        intent.putExtra("name",_name);
        intent.putExtra("mail",_mail);
        intent.putExtra("pass",_pass);
        intent.putExtra("cpass",_cpass);
        startActivity(intent);
        finish();
    }
    //End of Code
}