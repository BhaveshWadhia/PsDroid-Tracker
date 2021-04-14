package com.example.psdroid.ui.login;
//Import Class
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.psdroid.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginTabFragment extends Fragment {
    EditText username,pass;
    TextView forget;
    Button login;

    float v=0;
    private FirebaseAuth auth;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container,false);

        username = root.findViewById(R.id.username);
        pass = root.findViewById(R.id.pass);
        forget = root.findViewById(R.id.forget);
        login = root.findViewById(R.id.reset);

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

        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(v -> {
            String txt_user = username.getText().toString();
            String txt_pass = pass.getText().toString();
            loginUser(txt_user,txt_pass);
        });
        forget.setOnClickListener(v -> callforget());
        return root;
    }

    private void callforget() {
        startActivity(new Intent(getActivity(),ForgotPassword.class));

    }


/*  @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = findViewById(R.id.username);
        pass = findViewById(R.id.pass);
        forget = findViewById(R.id.forget);
        login = findViewById(R.id.button);
        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_user = username.getText().toString();
                String txt_pass = pass.getText().toString();
                loginUser(txt_user,txt_pass);
            }
        });
    }  */

    private void loginUser(String txt_user, String txt_pass) {

        auth.signInWithEmailAndPassword(txt_user,txt_pass).addOnSuccessListener(authResult -> {
            Toast.makeText(getActivity(), "Login Successful!", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(LoginTabFragment.this,));
            //finish();
        });
    }
}

