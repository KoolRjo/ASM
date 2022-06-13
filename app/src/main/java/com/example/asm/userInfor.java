package com.example.asm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class userInfor extends AppCompatActivity {
    Button bt_backButton;
    TextView tx_showUser;
    TextView tx_showPass;

    public void getList(){
        bt_backButton = findViewById(R.id.bt_back);
        tx_showUser = findViewById(R.id.tx_showUser);
        tx_showPass = findViewById(R.id.tx_showPass);
    }
    public void getUserInfor(){
        Bundle bundle = getIntent().getExtras();
        String user_name = bundle.getString("key_tx_user","Cannot receive user name from main");
        String pass = bundle.getString("key_tx_pass","Cannot receive password from main");
        tx_showUser.setText(user_name);
        tx_showPass.setText(pass);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        getList();
        getUserInfor();
        bt_backButton.setOnClickListener(view -> {
            Intent intent = new Intent(userInfor.this,MainActivity.class);
            startActivity(intent);
        });
    }
}