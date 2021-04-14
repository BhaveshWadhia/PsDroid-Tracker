package com.example.psdroid.ui.login;
//Import Class
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.psdroid.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText forgetemail;
    private Button reset;
    private ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgetemail = findViewById(R.id.forgtemail);
        reset =  findViewById(R.id.reset);

        progressBar = findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();

        reset.setOnClickListener(v -> resetPassword());
    }

    private void resetPassword() {

        String email = forgetemail.getText().toString().trim();
        if(email.isEmpty()){
            forgetemail.setError("Email is required");
            forgetemail.requestFocus();
            return;
        }

        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            forgetemail.setError("Please provide valid email");
            forgetemail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(ForgotPassword.this, "Check your email to reset your password!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(ForgotPassword.this, "Try again! Something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });
    }
}