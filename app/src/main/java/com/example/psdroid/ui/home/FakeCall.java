package com.example.psdroid.ui.home;
//Import Class
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.psdroid.R;
//Fake Call
public class FakeCall extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fake_caller_content);
        TextView fakename = findViewById(R.id.fake_name);
        Intent intent = getIntent();
        String set_fakename = intent.getStringExtra("fakename_key");
        fakename.setText(set_fakename);
    }
}
