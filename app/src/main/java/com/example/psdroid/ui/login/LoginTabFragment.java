package com.example.psdroid.ui.login;
//Import Class
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.psdroid.MainScreen;
import com.example.psdroid.R;
import com.example.psdroid.ui.forget_password.ForgotPassword;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
// Login Tab Fragment
public class LoginTabFragment extends Fragment {
    EditText username, pass;
    TextView forget;
    ProgressBar progressBar;
    Button login;
    float v = 0;
    private FirebaseAuth firebaseAuth;




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.tab_login, container, false);


        firebaseAuth = FirebaseAuth.getInstance();
        username = root.findViewById(R.id.username);
        pass = root.findViewById(R.id.pass);
        forget = root.findViewById(R.id.forget);
        login = root.findViewById(R.id.signupBtn);
        progressBar = root.findViewById(R.id.login_progressBar);



        username.setTranslationX(800);
        pass.setTranslationX(800);
        forget.setTranslationX(800);
        login.setTranslationX(800);

        username.setAlpha(v);
        pass.setAlpha(v);
        forget.setAlpha(v);
        login.setAlpha(v);

        username.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forget.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        login.setOnClickListener(v -> {
            String txt_user = username.getText().toString();
            String txt_pass = pass.getText().toString();
            if (txt_user.isEmpty() || txt_pass.isEmpty()) {
                Toast.makeText(getActivity(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                isUser();
            }
        });
        forget.setOnClickListener(v -> callforget());
        return root;
    }



    private void isUser() {
        progressBar.setVisibility(View.VISIBLE);
        String userEnteredUsername = username.getText().toString().trim();
        String userEnteredPassword = pass.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("user").equalTo(userEnteredUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String passwordfromdatabase = snapshot.child(userEnteredUsername).child("pass").getValue(String.class);
                    assert passwordfromdatabase != null;
                    if (passwordfromdatabase.equals(userEnteredPassword)) {
                        Intent intent = new Intent(getActivity(), MainScreen.class);
                        intent.putExtra("user", userEnteredUsername);
                        startActivity(intent);
                        progressBar.setVisibility(View.INVISIBLE);
                        getActivity().finish();
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), "Wrong Password", Toast.LENGTH_SHORT).show();
                        pass.requestFocus();
                    }
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "No such user exists", Toast.LENGTH_SHORT).show();
                    username.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }



    private void callforget() {
        String userEnteredUsername = username.getText().toString().trim();
        Intent intent = new Intent(getActivity(), ForgotPassword.class);
        intent.putExtra("user", userEnteredUsername);
        startActivity(intent);
    }
}


