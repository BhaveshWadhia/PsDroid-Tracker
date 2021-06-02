package com.example.psdroid.ui.register;
//Import Class
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.psdroid.MainScreen;
import com.example.psdroid.R;
// Account Created Successfully Activity
public class AccountCreatedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_created);
        Button startBtn = findViewById(R.id.startButton);
        String _user = getIntent().getStringExtra("user");
        String _mail = getIntent().getStringExtra("mail");
        String _name = getIntent().getStringExtra("name");
        String _mobile = getIntent().getStringExtra("mob");
        String _pass = getIntent().getStringExtra("pass");
        String _cpass = getIntent().getStringExtra("cpass");
        startBtn.setOnClickListener(view -> {
            Intent intent = new Intent(AccountCreatedActivity.this, MainScreen.class);
            intent.putExtra("user",_user);
            intent.putExtra("name",_name);
            intent.putExtra("mob",_mobile);
            intent.putExtra("mail",_mail);
            intent.putExtra("pass",_pass);
            intent.putExtra("cpass",_cpass);
            startActivity(intent);
            //startActivity(new Intent(getApplicationContext(), MainScreen.class));
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_right);
            finish();
        });
    }
}
