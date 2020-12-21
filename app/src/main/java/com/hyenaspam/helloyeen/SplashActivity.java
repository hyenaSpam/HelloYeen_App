package com.hyenaspam.helloyeen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Log.i("YEEN", "SplashActivity onCreate()");

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               SharedPreferences mysharedpreferences = getSharedPreferences("com.hyenaspam.helloyeen", MODE_PRIVATE);
               String email = mysharedpreferences.getString("email", "");
               if (!email.equals("")){
                   UserProfile.getInstance().setEmail(email);
                   IterableAPIHelper.getInstance().getUser(UserProfile.getInstance().getEmail());
                   startActivity(new Intent(SplashActivity.this, MainActivity.class));
               } else {
                   // no email found
                   startActivity(new Intent(SplashActivity.this, LoginActivity.class));
               }
               SplashActivity.this.finish();
           }
       }, 3000);

    }
}