package com.example.psdroid.ui.home;
//Import Class
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.psdroid.R;
//Fake Call
public class FakeCall extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fake_caller_content);
        TextView fakename = findViewById(R.id.fake_name);
        Button rejectBtn = findViewById(R.id.fake_call_rejectBtn);
        Button acceptBtn = findViewById(R.id.fake_call_acceptBtn);
        Intent intent = getIntent();
        String set_fakename = intent.getStringExtra("fakename_key");
        fakename.setText(set_fakename);
        Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Ringtone ringPlayer = RingtoneManager.getRingtone(getApplicationContext(),ringtone);
        ringPlayer.setLooping(true);
        ringPlayer.play();
        //If reject button is clicked
        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ringPlayer.stop();
                finish();
            }
        });
        //If accept button is clicked

        // FUTURE SCOPE

        //Ring call for 45 seconds and end the call
        new Handler().postDelayed(() -> {
            ringPlayer.stop();
            finish();
        },45000);
    }
}
