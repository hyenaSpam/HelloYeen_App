package com.hyenaspam.helloyeen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.iterable.iterableapi.IterableApi;

import org.json.JSONException;
import org.json.JSONObject;

public class CoffeeActivity extends AppCompatActivity {

    String myPath = "helloyeen://coffee";

    TextView currentCoffee;
//    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);

        Log.i("YEEN", "Coffee Activity onCreate()");

        initViewItems();
        retrieveIntent();
    }

    private void retrieveIntent(){

        String myCoffee = "";

        if (getIntent() != null) {

            if (IterableApi.getInstance().getPayloadData() != null) {
                Log.i("YEEN", IterableApi.getInstance().getPayloadData().toString());
            }

            try {

                /*
                TODO: 11/17/20 move all this logic to RouterActivity
                OLD AppLink Management, re-implement later in RouterActivity along with this full method
                Intent appLinkIntent = getIntent();
                String appLinkAction = appLinkIntent.getAction();
                Uri appLinkData = appLinkIntent.getData();
                String appLinkPath = appLinkIntent.getData().getScheme() + "://" + appLinkIntent.getData().getHost() + appLinkIntent.getData().getPath();
                String i = appLinkAction;
                */

                myCoffee = getIntent().getData().getPath();
                Log.i("YEEN", myCoffee);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (myCoffee.isEmpty()){
                    if ( !UserProfile.getInstance().getFavoriteCoffee().isEmpty() ) {
                        myCoffee = UserProfile.getInstance().getFavoriteCoffee();
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("coffee", myCoffee);
                            IterableAPIHelper.getInstance().trackEvent("Coffee Viewed", jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        myCoffee = "black coffee";
                    }
                }
                currentCoffee.setText(myCoffee.toUpperCase().replace('_', ' ').replace("/",""));
            }


        }
    }

    public void initViewItems(){

        currentCoffee = findViewById(R.id.currentCoffeeTextView);
    }
}