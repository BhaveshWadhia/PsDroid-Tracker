package com.example.psdroid.ui.register;
//Import Class
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.psdroid.R;
import com.example.psdroid.ui.forget_password.VerifyOTP;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//Signup Tab Fragment
public class SignupTabFragment extends Fragment {

    private EditText mobile,user,email,pass,conpass,name;
    private Button button;
    FirebaseDatabase rootNode;
    public String txt_user,txt_email,txt_mobile,txt_pass,txt_conpass,npWhiteSpace,txt_name;
    DatabaseReference reference;
    private FirebaseAuth auth;
    ProgressBar progressBar;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.tab_signup,container,false);
        email = root.findViewById(R.id.email);
        mobile= root.findViewById(R.id.mobile);
        user= root.findViewById(R.id.user);
        pass= root.findViewById(R.id.pass);
        conpass= root.findViewById(R.id.conpass);
        button= root.findViewById(R.id.signupBtn);
        name = root.findViewById(R.id.username);
        progressBar = root.findViewById(R.id.signup_progressBar);

        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(validateMobile(mobile.getText().toString())){
                        button.setEnabled(true);
                    }
                    else {
                        button.setEnabled(false);
                        mobile.setError("Invalid Mobile No");
                    }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        button.setOnClickListener(v -> {
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("users");
           txt_email = email.getText().toString().trim();
           txt_name = name.getText().toString().trim();
           txt_mobile = mobile.getText().toString().trim();
           txt_user = user.getText().toString().trim();
           txt_pass = pass.getText().toString().trim();
           txt_conpass = conpass.getText().toString().trim();
           npWhiteSpace = "(?=\\s+$)";

            if(TextUtils.isEmpty(txt_email)|| TextUtils.isEmpty(txt_mobile)|| TextUtils.isEmpty(txt_user)||TextUtils.isEmpty(txt_pass)|| TextUtils.isEmpty(txt_conpass)){
                Toast.makeText(getActivity(), "Empty Credentials!", Toast.LENGTH_SHORT).show();
            }
            else if(txt_name.length()==0)
            {
                name.requestFocus();
                name.setError("FIELD CANNOT BE EMPTY");
            }
            else if(!txt_name.matches("[a-zA-Z ]+"))
            {
                name.requestFocus();
                name.setError("Enter valid full name");
            }
            else if(txt_user.matches(npWhiteSpace)){
                Toast.makeText(getActivity(), "Invalid username", Toast.LENGTH_SHORT).show();
            }
            else if ((txt_pass.length() <8 ) & (txt_conpass.length() < 8)){
                Toast.makeText(getActivity(), "Password too short", Toast.LENGTH_SHORT).show();
            }
            else if (!txt_pass.equals(txt_conpass)){
                Toast.makeText(getActivity(), "Password did not match!", Toast.LENGTH_SHORT).show();
            }
            else if (txt_mobile.length() < 10){
                Toast.makeText(getActivity(), "Invalid mobile number!", Toast.LENGTH_SHORT).show();
            }
            else if(!user.getText().toString().isEmpty()) {
                progressBar.setVisibility(View.VISIBLE);
                DatabaseReference nouser_reference = FirebaseDatabase.getInstance().getReference();
                nouser_reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!snapshot.hasChild("users")) {
                            Intent intent = new Intent(getActivity(), VerifyOTP.class);
                            intent.putExtra("user", txt_user);
                            intent.putExtra("name", txt_name);
                            intent.putExtra("mob", txt_mobile);
                            intent.putExtra("mail", txt_email);
                            intent.putExtra("pass", txt_pass);
                            intent.putExtra("cpass", txt_conpass);
                            startActivity(intent);
                            progressBar.setVisibility(View.INVISIBLE);
                            getActivity().finish();
                        }
                        else
                        {
                            checkUsername();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        return root;
    }

    private void checkUsername() {
        String userEnteredUsername = user.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("user").equalTo(userEnteredUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "Username is already in use!", Toast.LENGTH_SHORT).show();
                    user.setText("");
                    user.requestFocus();
                }
                else {
                    checkmob(txt_email, txt_mobile, txt_user, txt_pass, txt_conpass);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void checkmob(String txt_email, String txt_mobile, String txt_user, String txt_pass, String txt_conpass) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        Query checkmob = ref.orderByChild("user");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String mob = ds.child("mobile").getValue(String.class);
                   // Toast.makeText(getActivity(), ""+mob, Toast.LENGTH_SHORT).show();
                    assert mob != null;
                    if (mob.equals(txt_mobile)) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), "Mobile Number is already in use!", Toast.LENGTH_SHORT).show();
                        mobile.setText("");
                        mobile.requestFocus();
                    }
                    else {
                        Intent intent = new Intent(getActivity(), VerifyOTP.class);
                        intent.putExtra("user", txt_user);
                        intent.putExtra("name", txt_name);
                        intent.putExtra("mob", txt_mobile);
                        intent.putExtra("mail", txt_email);
                        intent.putExtra("pass", txt_pass);
                        intent.putExtra("cpass", txt_conpass);
                        startActivity(intent);
                        progressBar.setVisibility(View.INVISIBLE);
                        getActivity().finish();
                    }
                }}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        checkmob.addListenerForSingleValueEvent(eventListener);
    }

    boolean validateMobile(String input){
        Pattern p = Pattern.compile("[+][0-9]{2}"+"[6-9][0-9]{9}");
        Matcher m = p.matcher(input);
        return m.matches();
    }
}
