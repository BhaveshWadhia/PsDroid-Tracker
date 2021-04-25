package com.example.psdroid.ui.login;
//Import Class
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.psdroid.MainScreen;
import com.example.psdroid.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
// Set Password Activity
public class SetPassword extends AppCompatActivity {
    EditText npass,connpass;
    ImageView cancel;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        npass = findViewById(R.id.pass);
        connpass = findViewById(R.id.conpass);
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(view -> {
            startActivity(new Intent(SetPassword.this,LoginActivity.class));
            finish();
        });
    }
    public void setNewPassword(View view){
        CheckInternet checkInternet = new CheckInternet();
        if(!checkInternet.isConnected(this)){
            showCustomDialog();
        }
        else {
            String np = npass.getText().toString().trim();
            String cnp = connpass.getText().toString().trim();
         /*   String _user = getIntent().getStringExtra("user");
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("users");
            if (np.equals(cnp) && ) {
                reference.child(_user).child("pass").setValue(np);
                reference.child(_user).child("conpass").setValue(cnp);
                startActivity(new Intent(getApplicationContext(), MainScreen.class));
                finish();
            } else */
            if((np.length()<8) && (cnp.length()<8)) {
                Toast.makeText(this, "Password too match", Toast.LENGTH_SHORT).show();
            }
            else if (!np.equals(cnp)){
                Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
            }
            else{
                String _user = getIntent().getStringExtra("user");
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                reference.child(_user).child("pass").setValue(np);
                reference.child(_user).child("conpass").setValue(cnp);
                startActivity(new Intent(getApplicationContext(), MainScreen.class));
                finish();
            }
        }
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SetPassword.this);
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    }
                });
    }
}