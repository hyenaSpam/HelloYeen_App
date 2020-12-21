package com.hyenaspam.helloyeen;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.iterable.iterableapi.IterableApi;
import com.iterable.iterableapi.IterableConfig;
import com.iterable.iterableapi.IterableHelper;
import com.iterable.iterableapi.IterableInAppHandler;
import com.iterable.iterableapi.IterableInAppMessage;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IterableAPIHelper {

    static IterableAPIHelper instance = null;
    private final String key = "6e40f94c85164efba94c199c82560274";

    public static IterableAPIHelper getInstance(){
        if (instance == null){
            instance = new IterableAPIHelper();
        }
        return instance;
    }


    private IterableConfig buildConfig(){
//        IterableInAppHandler inAppHandler = new IterableInAppHandler() {
//            @NonNull
//            @Override
//            public InAppResponse onNewInApp(@NonNull IterableInAppMessage message) {
//                JSONObject customPayload = message.getCustomPayload();
//                String msgPriority ="";
//                String yeenType = "";
//                boolean yeenTest = false;
//                try {
//                    msgPriority = customPayload.getString("msgPriority").toLowerCase();
//                    yeenType = customPayload.getString("yeenType");
//                    yeenTest = customPayload.getBoolean("yeenTest");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                if (msgPriority.equals("high")) {
//
//                    return InAppResponse.SHOW;
//                } else {
//                    Log.i("YEEN", String.format("message skipped due to priority: %s", msgPriority));
//                    return InAppResponse.SKIP;
//                }
//            }
//        };

        IterableConfig config = new IterableConfig
                .Builder()
//                .setLogLevel(Log.VERBOSE)
//                .setInAppHandler(inAppHandler)
                .build();
        return config;
    }

    public void initialize(Context context, String key){
        buildConfig();
        IterableApi.initialize(context, key, buildConfig() );
        IterableApi.getInstance().registerForPush();
    }

    public void setEmail(final String email){
        IterableApi.getInstance().updateEmail(email, new IterableHelper.SuccessHandler() {

                    @Override
                    public void onSuccess(@NonNull JSONObject data) {
                        successHandler(data, "updateEmail"
                        );
                    }
                },
                new IterableHelper.FailureHandler() {
                    @Override
                    public void onFailure(@NonNull String reason, @Nullable JSONObject data) {
                        failureHandler(reason, data, email);
                    }
                });

    }

    private void successHandler(JSONObject data, String type) {
        // what are we actually going to do with this JSON object. current NADA happens
        JSONObject jsonObject = new JSONObject();

        switch (type) {
            case "updateEmail":

                try {
                    jsonObject.put("email", data.get("email"));
                    jsonObject.put("firstName", data.get("firstName"));
                    jsonObject.put("phoneNumber", data.get("phoneNumber"));
                    jsonObject.put("isVerified", data.get("isVerified"));
                    jsonObject.put("Role", data.get("Role"));
                    jsonObject.put("favoriteCoffee", jsonObject.get("favoriteCoffee"));
                    Log.i("YEEN_successHandler", jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }


    public void failureHandler(String reason, JSONObject data, String email){
        Log.e("YEEN_failureHandler", reason);
        if (reason.contains("email already exists")){
            IterableApi.getInstance().setEmail(email);
        }
    }

    public void getUser(String email){
        OkHttpClient client = new OkHttpClient();
        String url = String.format("https://app.iterable.com/api/users/%s", email);
        Request request = new Request.Builder()
                .header("api_key", key )
                .header("content-type", "application/json")
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (!response.isSuccessful()){
                    throw new IOException("IOException: " + response);
                } else if (response.isSuccessful()){
                    String results = response.body().string();
                    try {
                        JSONObject data = new JSONObject(results);
                        JSONObject usrObj = (JSONObject) data.getJSONObject("user").getJSONObject("dataFields");
                        UserProfile.getInstance().setFirstName(usrObj.getString("firstName"));
                        UserProfile.getInstance().setLastName(usrObj.getString("lastName"));
                        UserProfile.getInstance().setPhoneNumber(usrObj.getString("phoneNumber"));
                        UserProfile.getInstance().setFavoriteCoffee(usrObj.getString("favoriteCoffee"));
                        UserProfile.getInstance().setIsVerified(Boolean.valueOf(usrObj.getString("isVerified")));
                        UserProfile.getInstance().setRole(usrObj.getString("Role"));
                        UserProfile.getInstance().setFavoriteCoffee(usrObj.getString("favoriteCoffee"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public void disablePush(){
        IterableApi.getInstance().disablePush();
    }

    public void enablePush(){
        IterableApi.getInstance().registerForPush();
    }

    public void trackEvent(String name, JSONObject obj){
        IterableApi.getInstance().track(name, obj);
    }
}
