package com.hyenaspam.helloyeen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class RouterActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: 11/17/20 add intent logic from CoffeeActivity and create this as the router class for ALL intents
        // TODO: 11/17/20 update AndroidManifest so that ALL schemes point to this class ONLY for now
        // currently Inactive class

        try {
            Intent appIntent = getIntent();
            String intentAction = appIntent.getAction();
            Uri intentData = appIntent.getData();
            String intentScheme = appIntent.getScheme();
            String intentHost = appIntent.getData().getHost();
            String intentPath = appIntent.getData().getPath();

            String fullIntent = intentScheme + intentHost + intentPath;
            Log.i("YEEN", fullIntent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
