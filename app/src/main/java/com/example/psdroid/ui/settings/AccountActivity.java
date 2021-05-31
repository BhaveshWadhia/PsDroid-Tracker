package com.example.psdroid.ui.settings;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.psdroid.R;

public class AccountActivity extends AppCompatActivity {
    TextView tv;
    String st;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);
        tv=findViewById(R.id.fullname);
        st=getIntent().getExtras().getString("user");
        tv.setText(st);

        tv=findViewById(R.id.name);
        st=getIntent().getExtras().getString("user");
        tv.setText(st);

        tv=findViewById(R.id.email);
        st=getIntent().getExtras().getString("mail");
        tv.setText(st);

        tv=findViewById(R.id.phone);
        st=getIntent().getExtras().getString("mob");
        tv.setText(st);
    }
}
