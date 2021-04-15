package com.example.psdroid.ui.login;
//Import Class
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.example.psdroid.MainScreen;
import com.example.psdroid.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupTabFragment extends Fragment {

    private EditText mobile,user,email,pass,conpass;
    private Button button;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private FirebaseAuth auth;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment,container,false);
        email = root.findViewById(R.id.email);
        mobile= root.findViewById(R.id.mobile);
        user= root.findViewById(R.id.user);
        pass= root.findViewById(R.id.pass);
        conpass= root.findViewById(R.id.conpass);
        button= root.findViewById(R.id.reset);


        button.setOnClickListener(v -> {
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("users");

            String txt_email = email.getText().toString();
            String txt_mobile = mobile.getText().toString();
            String txt_user = user.getText().toString();
            String txt_pass = pass.getText().toString();
            String txt_conpass = conpass.getText().toString();
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
            } else{
          //      registerUser(txt_email,txt_pass);
                UserHeplerClass heplerClass = new UserHeplerClass(txt_email,txt_user,txt_mobile,txt_pass,txt_conpass);
                reference.child(txt_user).setValue(heplerClass);
                Toast.makeText(getActivity(), "Registered successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),MainScreen.class);
                startActivity(intent);
            }
        });

        return root;

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
