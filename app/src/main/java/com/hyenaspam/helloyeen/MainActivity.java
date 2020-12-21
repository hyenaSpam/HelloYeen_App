package com.hyenaspam.helloyeen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
//import com.iterable.iterableapi.ui.inbox.IterableInboxActivity;

public class MainActivity extends AppCompatActivity {


    // TODO: 12/10/20 generate QR code:
    // Don't set an email and user ID in the same session. Doing so causes the SDK to treat them as different users.
    // String userID = "kingYeen";

    Button coffeeButton, myProfileButton, sendTestButton;

    Button iterInbox;
    IterableAPIHelper iterableAPIHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("YEEN", "MainActivity onCreate()");

        UserProfile.getInstance();

        initViews();

        Toast.makeText(this, UserProfile.getInstance().getEmail(), Toast.LENGTH_SHORT).show();

    }


    public void initViews(){

        iterableAPIHelper = new IterableAPIHelper().getInstance();

        coffeeButton = findViewById(R.id.coffeeButton);
        coffeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CoffeeActivity.class));
            }
        });

        myProfileButton = findViewById(R.id.myProfileButton);
        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        sendTestButton = findViewById(R.id.sendTestButton);
        sendTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SendActivity.class));
            }
        });

        iterInbox = findViewById(R.id.iterInboxButton);
        iterInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), IterableInboxActivity.class));
                Toast.makeText(getApplicationContext(), "Button Currently Disabled", Toast.LENGTH_SHORT).show();
            }
        });

    }
}