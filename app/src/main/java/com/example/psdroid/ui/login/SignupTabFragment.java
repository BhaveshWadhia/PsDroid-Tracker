package com.example.psdroid.ui.login;
//Import Class
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.psdroid.R;
import com.google.firebase.auth.FirebaseAuth;

public class SignupTabFragment extends Fragment {

    private EditText mobile,email,pass,conpass;
    private Button button;
    private FirebaseAuth auth;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment,container,false);
        email = root.findViewById(R.id.email);
        mobile= root.findViewById(R.id.mobile);
        pass= root.findViewById(R.id.pass);
        conpass= root.findViewById(R.id.conpass);
        button= root.findViewById(R.id.reset);
        auth = FirebaseAuth.getInstance();

        button.setOnClickListener(v -> {
            String txt_email = email.getText().toString();
            String txt_mobile = mobile.getText().toString();
            String txt_pass = pass.getText().toString();
            String txt_conpass = conpass.getText().toString();

            if(TextUtils.isEmpty(txt_email)|| TextUtils.isEmpty(txt_mobile)|| TextUtils.isEmpty(txt_pass)|| TextUtils.isEmpty(txt_conpass)){
                Toast.makeText(getActivity(), "Empty Credentials!", Toast.LENGTH_SHORT).show();
            } else if ((txt_pass.length() <8 ) & (txt_conpass.length() < 8)){
                Toast.makeText(getActivity(), "Password too short", Toast.LENGTH_SHORT).show();
            } else if (txt_pass.toString().trim() != txt_conpass.toString().trim()){
                Toast.makeText(getActivity(), "Password did not match!", Toast.LENGTH_SHORT).show();
            }  else if (txt_mobile.length() < 10){
                Toast.makeText(getActivity(), "Invalid mobile number!", Toast.LENGTH_SHORT).show();
            } else{
                registerUser(txt_email,txt_pass);
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

    private void registerUser(String txt_email, String txt_pass) {

        auth.createUserWithEmailAndPassword(txt_email, txt_pass).addOnCompleteListener(getActivity(), task -> {
            if(task.isSuccessful()){
                Toast.makeText(getActivity(), "Registering User Successful!", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(SignupTabFragment.this,));
                //finish();
            }
            else{
                Toast.makeText(getActivity(), "Registration Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
