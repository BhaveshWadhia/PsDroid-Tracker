package com.example.psdroid.ui.forget_password;
//Import Class
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.psdroid.R;
import com.example.psdroid.ui.CheckInternet;
import com.example.psdroid.ui.login.LoginActivity;
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
            startActivity(new Intent(SetPassword.this, LoginActivity.class));
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
            if((np.length()<8) && (cnp.length()<8)) {
                Toast.makeText(this, "Password too small", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(getApplicationContext(), PasswordChanged.class);
                intent.putExtra("user", _user);
                startActivity(intent);
                finish();
            }
        }
    }
    //Show dialog when not connected to the internet
    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SetPassword.this);
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", (dialogInterface, i) -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                .setNegativeButton("Cancel", (dialogInterface, i) -> startActivity(new Intent(getApplicationContext(),LoginActivity.class)));
    }
//End of Code
}