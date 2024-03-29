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
    String userEnteredUsername,userEnteredPassword;
    Button login;
    SharedPreferences acctDetails,isLoggedIn;
    float v = 0;
    FirebaseAuth firebaseAuth;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.tab_login, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        username = root.findViewById(R.id.username);
        pass = root.findViewById(R.id.pass);
        forget = root.findViewById(R.id.forget);
        login = root.findViewById(R.id.LoginBtn);
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
        //When login button is clicked
        login.setOnClickListener(v -> {
            String txt_user = username.getText().toString();
            String txt_pass = pass.getText().toString();
            if (txt_user.isEmpty() || txt_pass.isEmpty()) {
                Toast.makeText(getActivity(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            }
            else {
                isUser();
            }
        });
        // When clicked on forget password
        forget.setOnClickListener(v -> callforget());
        return root;
    }

    private void isUser() {
        progressBar.setVisibility(View.VISIBLE);
        userEnteredUsername = username.getText().toString().trim();
        userEnteredPassword = pass.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("user").equalTo(userEnteredUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String passwordfromdatabase = snapshot.child(userEnteredUsername).child("pass").getValue(String.class);
                    assert passwordfromdatabase != null;
                    if (passwordfromdatabase.equals(userEnteredPassword)) {
                        UserDetails();  // Load details & store details into shared pref
                        call_intent();  // Go to main screen of application
                    }
                    else {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), "Wrong Password", Toast.LENGTH_SHORT).show();
                        pass.requestFocus();
                    }
                }
                else {
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

    // Get Current Details of user from the database & store it in account details shared preference
    private void UserDetails() {
        // Load user account details from firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userEnteredUsername);
        ValueEventListener listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fullname = (String) snapshot.child("name").getValue();
                String mobile = (String) snapshot.child("mobile").getValue();
                String email = (String) snapshot.child("email").getValue();

                // Store data in shared preference when user login first time
                acctDetails = getActivity().getSharedPreferences("ACCOUNT_SHARED_PREF", Context.MODE_PRIVATE);
               SharedPreferences.Editor DtlsEditor = acctDetails.edit();
                DtlsEditor.clear();  //Clear old data
                DtlsEditor.putString("fullname",fullname);
                DtlsEditor.putString("user", userEnteredUsername);
                DtlsEditor.putString("email", email);
                DtlsEditor.putString("mob", mobile);
                DtlsEditor.apply();

                //Set a shared pref to indicate user has logged in
                isLoggedIn = getActivity().getSharedPreferences("LOGIN_SHARED_PREF",Context.MODE_PRIVATE);
                SharedPreferences.Editor loginEditor = isLoggedIn.edit();
                loginEditor.clear();
                loginEditor.putString("isLoggedIn","true");
                loginEditor.apply();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    // Open Main screen of the application
    private void call_intent() {
        Intent intent = new Intent(getActivity(), MainScreen.class);
        intent.putExtra("user", userEnteredUsername);
        startActivity(intent);
        progressBar.setVisibility(View.INVISIBLE);
        getActivity().finish();
    }
    // Open forget password activity
    private void callforget() {
        String userEnteredUsername = username.getText().toString().trim();
        Intent intent = new Intent(getActivity(), ForgotPassword.class);
        intent.putExtra("user", userEnteredUsername);
        startActivity(intent);
    }
    //End of Code
}


