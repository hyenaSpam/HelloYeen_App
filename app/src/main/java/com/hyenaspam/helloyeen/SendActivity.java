package com.hyenaspam.helloyeen;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendActivity extends AppCompatActivity {

    Button sendPush, sendInapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        initView();
    }

    private void initView() {
        sendPush = findViewById(R.id.sendPushButton);
        sendPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPush();
            }
        });

        sendInapp = findViewById(R.id.sendInAppButton);
        sendInapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendInapp();
            }
        });

    }



    private void sendPush() {
        String url = "https://api.iterable.com/api/push/target?api_key="+getString(R.string.iter_web_api_key);
        OkHttpClient client = new OkHttpClient();

        JSONObject json = new JSONObject();
        try {
            json.put("recipientEmail", UserProfile.getInstance().getEmail());
            json.put("campaignId", 1836925);
            String jsonString = json.toString();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonString);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("content-type", "application/json")
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("IOException: " + response);

                    }
                    if (response.isSuccessful()) {
                        // do something?
                    }

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // TODO: 12/18/20 need to implement inAppHandler for when multiple messages come in and also so inapp doesn't display over splash screen 
    private void sendInapp(){
        String url = "https://api.iterable.com/api/inApp/target?api_key="+getString(R.string.iter_web_api_key);
        OkHttpClient client = new OkHttpClient();

        JSONObject json = new JSONObject();
        try {
            json.put("recipientEmail", UserProfile.getInstance().getEmail());
            json.put("campaignId", 1837120);
            String jsonString = json.toString();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonString);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("content-type", "application/json")
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("IOException: " + response);

                    }
                    if (response.isSuccessful()) {
                        // do something?
                    }

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}