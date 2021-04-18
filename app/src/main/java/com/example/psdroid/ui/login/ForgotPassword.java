package com.example.psdroid.ui.login;
//Import Class
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.psdroid.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword extends AppCompatActivity {

    private EditText number;
    private Button next;
    ImageView back;
    private ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_forgot_password);

        back = findViewById(R.id.backtologin);
        number = findViewById(R.id.mobile);
        next = findViewById(R.id.next);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPassword.this,LoginActivity.class));
                finish();
            }
        });
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (validateMobile(number.getText().toString())) {
                    next.setEnabled(true);

                } else {
                    next.setEnabled(false);
                    number.setError("Invalid Mobile No");
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });





    }
        boolean validateMobile(String input){
            Pattern p = Pattern.compile("[+][0-9]{2}"+"[6-9][0-9]{9}");
            Matcher m = p.matcher(input);
            return m.matches();
        }







    public void verifyphoneno(View view){
        String _user = getIntent().getStringExtra("user");
        String valmob = number.getText().toString();
        Query checkuser = FirebaseDatabase.getInstance().getReference("users").orderByChild("user").equalTo(_user);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Intent intent = new Intent(getApplicationContext(),VerifyOTP.class);
                    intent.putExtra("mob",valmob);
                    intent.putExtra("user",_user);
                    intent.putExtra("whatToDo","updateData");
                    startActivity(intent);
                    finish();

                }
                else{
                    number.setError("No such user exists or you may have provided wrong username. Please provide correct username");
                    startActivity(new Intent(ForgotPassword.this,LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}