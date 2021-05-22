package com.example.psdroid.ui.login;
//Import Class
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebHistoryItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.psdroid.R;
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

    private EditText mobile,user,email,pass,conpass;
    private Button button;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private FirebaseAuth auth;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.tab_signup,container,false);
        email = root.findViewById(R.id.email);
        mobile= root.findViewById(R.id.mobile);
        user= root.findViewById(R.id.user);
        pass= root.findViewById(R.id.pass);
        conpass= root.findViewById(R.id.conpass);
        button= root.findViewById(R.id.reset);

        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(validateMobile(mobile.getText().toString())){
                        button.setEnabled(true);
                    }else {
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

            String txt_email = email.getText().toString().trim();
            String txt_mobile = mobile.getText().toString().trim();
            String txt_user = user.getText().toString().trim();
            String txt_pass = pass.getText().toString().trim();
            String txt_conpass = conpass.getText().toString().trim();
            String npWhiteSpace = "(?=\\s+$)";

            if(TextUtils.isEmpty(txt_email)|| TextUtils.isEmpty(txt_mobile)|| TextUtils.isEmpty(txt_user)||TextUtils.isEmpty(txt_pass)|| TextUtils.isEmpty(txt_conpass)){
                Toast.makeText(getActivity(), "Empty Credentials!", Toast.LENGTH_SHORT).show();
            }
            else if(txt_user.matches(npWhiteSpace)){
                Toast.makeText(getActivity(), "Invalid username", Toast.LENGTH_SHORT).show();
            }else if ((txt_pass.length() <8 ) & (txt_conpass.length() < 8)){
                Toast.makeText(getActivity(), "Password too short", Toast.LENGTH_SHORT).show();
            } else if (!txt_pass.equals(txt_conpass)){
                Toast.makeText(getActivity(), "Password did not match!", Toast.LENGTH_SHORT).show();
            }  else if (txt_mobile.length() < 10){
                Toast.makeText(getActivity(), "Invalid mobile number!", Toast.LENGTH_SHORT).show();
            }  else if(!user.getText().toString().isEmpty()){
              //  Toast.makeText(getActivity(), "Entering", Toast.LENGTH_SHORT).show();

                String userEnteredUsername = user.getText().toString().trim();
               // String userEnteredMob = mobile.getText().toString().trim();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

                Query checkUser = reference.orderByChild("user").equalTo(userEnteredUsername);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Toast.makeText(getActivity(), "Entering again", Toast.LENGTH_SHORT).show();
                        if (snapshot.exists()) {
                            //Toast.makeText(getActivity(), "Username is already in use!", Toast.LENGTH_SHORT).show();
                            user.requestFocus();
                        }
                        else{
                            //Toast.makeText(getActivity(), "Hii", Toast.LENGTH_SHORT).show();
                            checkmob(txt_email,txt_mobile,txt_user,txt_pass,txt_conpass);


                            /*
                            Intent intent = new Intent(getActivity(), VerifyOTP.class);

                            intent.putExtra("user", txt_user);
                            intent.putExtra("mob", txt_mobile);
                            intent.putExtra("mail", txt_email);
                            intent.putExtra("pass", txt_pass);
                            intent.putExtra("cpass", txt_conpass);
                            startActivity(intent);
                            getActivity().finish();*/
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                      /*  else {
                            Intent intent = new Intent(getActivity(), VerifyOTP.class);

                            intent.putExtra("user", txt_user);
                            intent.putExtra("mob", txt_mobile);
                            intent.putExtra("mail", txt_email);
                            intent.putExtra("pass", txt_pass);
                            intent.putExtra("cpass", txt_conpass);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/
                //      registerUser(txt_email,txt_pass);

            /*    UserHeplerClass heplerClass = new UserHeplerClass(txt_email,txt_user,txt_mobile,txt_pass,txt_conpass);
                reference.child(txt_user).setValue(heplerClass);
                Toast.makeText(getActivity(), "Registered successfully!", Toast.LENGTH_SHORT).show();*/
            }/*
            else{
                checkmob(txt_email,txt_mobile,txt_user,txt_pass,txt_conpass);
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            }*/
        });
        return root;
    }

    private void checkmob(String txt_email, String txt_mobile, String txt_user, String txt_pass, String txt_conpass) {
        Toast.makeText(getActivity(), "Entering function", Toast.LENGTH_SHORT).show();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        Query checkmob = ref.orderByChild("mobile");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String mob = ds.child("mobile").getValue(String.class);
                    Toast.makeText(getActivity(), ""+mob, Toast.LENGTH_SHORT).show();
                    if (mob.equals(txt_mobile)) {
                        Toast.makeText(getActivity(), "Mobile Number is already in use!", Toast.LENGTH_SHORT).show();
                        mobile.requestFocus();
                    }
                    else {
                        Intent intent = new Intent(getActivity(), VerifyOTP.class);

                        intent.putExtra("user", txt_user);
                        intent.putExtra("mob", txt_mobile);
                        intent.putExtra("mail", txt_email);
                        intent.putExtra("pass", txt_pass);
                        intent.putExtra("cpass", txt_conpass);
                        startActivity(intent);
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
   /* @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        email= findViewById(R.id.email);
        mobile= findViewById(R.id.mobile);
        pass= findViewById(R.id.pass);
        conpass= findViewById(R.id.conpass);
        button= findViewById(R.id.button);
        auth = FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_mobile = mobile.getText().toString();
                String txt_pass = pass.getText().toString();
                String txt_conpass = conpass.getText().toString();

                if(TextUtils.isEmpty(txt_email)|| TextUtils.isEmpty(txt_mobile)|| TextUtils.isEmpty(txt_pass)|| TextUtils.isEmpty(txt_conpass)){
                    Toast.makeText(SignupTabFragment.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                } else if ((txt_pass.length() <8 ) & (txt_conpass.length() < 8)){
                    Toast.makeText(SignupTabFragment.this, "Password too short", Toast.LENGTH_SHORT).show();
                } else if (txt_pass != txt_conpass){
                    Toast.makeText(SignupTabFragment.this, "Password did not match!", Toast.LENGTH_SHORT).show();
                }  else if (txt_mobile.length() < 10){
                    Toast.makeText(SignupTabFragment.this, "Invalid mobile number!", Toast.LENGTH_SHORT).show();
                } else{
                    registerUser(txt_email,txt_pass);
                }

            }
        });
    }*/

}
