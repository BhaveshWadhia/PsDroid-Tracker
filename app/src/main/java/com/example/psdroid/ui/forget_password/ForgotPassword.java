package com.example.psdroid.ui.forget_password;
//Import Class
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.psdroid.R;
import com.example.psdroid.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//Forget Password Activity
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
        back.setOnClickListener(view -> {
            startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
            finish();
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
                    Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
                    intent.putExtra("mob",valmob);
                    intent.putExtra("user",_user);
                    intent.putExtra("whatToDo","updateData");
                    startActivity(intent);
                }
                else{
                    number.setError("No such user exists or you may have provided wrong username. Please provide correct username");
                    new Handler().postDelayed(() ->startActivity(new Intent(ForgotPassword.this,LoginActivity.class)),2000);
                }
                finish();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    //End of Code
}