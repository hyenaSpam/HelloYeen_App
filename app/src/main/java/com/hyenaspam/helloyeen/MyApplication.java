package com.hyenaspam.helloyeen;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.iterable.iterableapi.IterableAction;
import com.iterable.iterableapi.IterableActionSource;
import com.iterable.iterableapi.IterableApi;
import com.iterable.iterableapi.IterableConfig;
import com.iterable.iterableapi.IterableFirebaseMessagingService;
import com.iterable.iterableapi.IterableInAppHandler;
import com.iterable.iterableapi.IterableInAppManager;
import com.iterable.iterableapi.IterableInAppMessage;
import com.iterable.iterableapi.IterableUrlHandler;

import org.json.JSONException;
import org.json.JSONObject;


public class MyApplication extends Application {

    String mobile_key;

    // TODO: 12/2/20 add iterable Deeplinking 
    // TODO: 12/2/20 events button sending to Iterable

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("YEEN", "MyApplication onCreate()");
        mobile_key = getString(R.string.iter_mobile_api_key);

        IterableAPIHelper.getInstance().initialize(getApplicationContext(), mobile_key);

//        IterableApi.getInstance().getInAppManager().setAutoDisplayPaused(true);

    }
}

// adb shell 'am start -d "helloyeen://"'
