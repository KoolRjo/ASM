package com.example.asm;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private static final String Tag = MainActivity.class.getSimpleName();
    private static final int Permission_Camera_Code = 1;
    MaterialButton btLogin;
    Button btCapture;
    Button btCallPhone;
    TextView txUserName;
    TextView txPassWord;
    ImageView captureImage;
    ImageButton googleSearch;
    TextView txSearchGoogle;
    ImageButton btSetting;

    public void getView(){
        btLogin = findViewById(R.id.bt_login);
        btCapture = findViewById(R.id.bt_register);
        btCallPhone = findViewById(R.id.bt_forget_register);
        txUserName = findViewById(R.id.tx_Username);
        txPassWord = findViewById(R.id.tx_Password);
        captureImage = findViewById(R.id.img_avarta);
        googleSearch = findViewById(R.id.bt_googleSearch);
        txSearchGoogle = findViewById(R.id.tx_Query);
        btSetting = findViewById(R.id.bt_SettingButton);
    }

    public void checkPermission(){
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
        }else if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.CAMERA)){
            Toast.makeText(MainActivity.this,"We cant get a picture without camera",Toast.LENGTH_LONG).show();
        }else{
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},Permission_Camera_Code);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Permission_Camera_Code: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Thanks you for your accept", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "App now can not use this function!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public void loadAnimation(){
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink);
        btSetting.startAnimation(animation);
    }
    public void setting(){
        btSetting.setOnClickListener(view-> loadAnimation());
    }

    public void putInforUser(){
        btLogin.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,userInfor.class);
            Bundle bundle = new Bundle();
            bundle.putString("key_tx_user",txUserName.getText().toString().trim());
            bundle.putString("key_tx_pass",txPassWord.getText().toString().trim());
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void captureImage(){
        ActivityResultLauncher<Intent> otherActivities = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null)
                    {
                        Bundle bundle = result.getData().getExtras();
                        Bitmap bitmap = (Bitmap)bundle.get("data");
                        captureImage.setImageBitmap(bitmap);
                    }
                }
        );

        btCapture.setOnClickListener(view -> {
            checkPermission();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager()) != null)
            {
                otherActivities.launch(intent);
            }else {
                Toast.makeText(MainActivity.this,"There is no app help support this action",Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    public void callPhone(){
        btCallPhone.setOnClickListener(view ->{
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("message/rfc822");
            emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{"boyyeurap@gmail.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Email from own Android app");
            emailIntent.putExtra(Intent.EXTRA_TEXT,"First time we sent a email from own app");
            emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try{
                startActivity(Intent.createChooser(emailIntent,"Bạn nên chọn Gmail nha"));
            }catch(ActivityNotFoundException ex) {
                Toast.makeText(MainActivity.this, "There are no Email clients installed", Toast.LENGTH_LONG).show();
            }

        });
    }

    public void searchGoogle(){
        googleSearch.setOnClickListener(view->{
            String searchQuery = txSearchGoogle.getText().toString().trim();
            Uri searchUri = Uri.parse("https://www.google.com/search?q=" + searchQuery);
            Intent intent = new Intent(Intent.ACTION_VIEW,searchUri);
            startActivity(intent);
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        //checkPermission();
        getView();
        setting();
        putInforUser();
        captureImage();
        callPhone();
        searchGoogle();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(Tag,"MainActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(Tag,"MainActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(Tag,"MainActivity onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(Tag,"MainActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(Tag,"MainActivity onDestroy");
    }
}