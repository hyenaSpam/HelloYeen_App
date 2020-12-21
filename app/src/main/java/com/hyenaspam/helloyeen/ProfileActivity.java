package com.hyenaspam.helloyeen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.iterable.iterableapi.IterableApi;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ProfileActivity extends AppCompatActivity {

    ConstraintLayout profileContainer;

    EditText emailET, firstNameET, lastNameET, roleET, phoneET, coffeeET;
    Button editButton, logoutButton;
    Boolean editMode = true;

    // TODO: 12/14/20 note that the getUser logic here wouldn't work for clients b/c getUser is rate-limited (3 requests per second)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initViews();
    }

    private void initViews() {
        profileContainer = findViewById(R.id.profileConstraintView);

        emailET = findViewById(R.id.emailEditText);
        if (!UserProfile.getInstance().getEmail().isEmpty()) {
            emailET.setText(UserProfile.getInstance().getEmail());
        }

        firstNameET = findViewById(R.id.firstNameEditText);
        if (!UserProfile.getInstance().getFirstName().isEmpty()){
            firstNameET.setText(UserProfile.getInstance().getFirstName());
        }

        lastNameET = findViewById(R.id.lastNameEditText);
        if (!UserProfile.getInstance().getLastName().isEmpty()){
            lastNameET.setText(UserProfile.getInstance().getLastName());
        }

        coffeeET = findViewById(R.id.coffeeEditText);
        if (!UserProfile.getInstance().getFavoriteCoffee().isEmpty()){
            coffeeET.setText(UserProfile.getInstance().getFavoriteCoffee());
        }

        roleET = findViewById(R.id.roleEditText);
        if (!UserProfile.getInstance().getRole().isEmpty()){
            roleET.setText(UserProfile.getInstance().getRole());
        }

        phoneET = findViewById(R.id.phoneEditText);
        if (!UserProfile.getInstance().getPhoneNumber().isEmpty()){
            phoneET.setText(
                   String.format( "+%s (%s) %s-%s",
                           UserProfile.getInstance().getPhoneNumber().substring(0,1),
                           UserProfile.getInstance().getPhoneNumber().substring(1,4),
                           UserProfile.getInstance().getPhoneNumber().substring(4,7),
                           UserProfile.getInstance().getPhoneNumber().substring(7))
            );
        }

        editButton = findViewById(R.id.enableEditButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEditText(profileContainer);
            }
        });

        enableEditText(profileContainer);

        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

    }

    private void saveData(){

        UserProfile.getInstance().setEmail(emailET.getText().toString());
        UserProfile.getInstance().setFirstName(firstNameET.getText().toString());
        UserProfile.getInstance().setLastName(lastNameET.getText().toString());
        UserProfile.getInstance().setRole(roleET.getText().toString());
        UserProfile.getInstance().setPhoneNumber(phoneET.getText().toString());
        UserProfile.getInstance().setFavoriteCoffee(coffeeET.getText().toString());


        JSONObject userData = new JSONObject();
        try {
            userData.put("firstName", UserProfile.getInstance().getFirstName());
            userData.put("lastName", UserProfile.getInstance().getLastName());
            userData.put("Role", UserProfile.getInstance().getRole());
            userData.put("phoneNumber", UserProfile.getInstance().getPhoneNumber());
            userData.put("favoriteCoffee", UserProfile.getInstance().getFavoriteCoffee());
            IterableApi.getInstance().updateUser(userData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void enableEditText(ViewGroup viewGroup){
        int cnt = viewGroup.getChildCount();
        if (editMode){
            // exit editing mode
            editMode = false;
            editButton.setText("EDIT");
            for (int i = 0; i < cnt; i++) {
                View view = viewGroup.getChildAt(i);
                if (view instanceof EditText) {
                    EditText e = (EditText) view;
                    changeEditTextMode(e, editMode);
                }
            }
            saveData();
        } else if (!editMode) {
            // enter editing mode
            editMode = true;
            editButton.setText("SAVE");
            for (int i = 0; i < cnt; i++) {
                View view = viewGroup.getChildAt(i);
                if (view instanceof EditText) {
                    if (view.getId() != R.id.emailEditText) {
                        EditText e = (EditText) view;
                        changeEditTextMode(e, editMode);
                    }
                }
            }
        }
    }

    private void logOut(){
        IterableApi.getInstance().disablePush();
        deleteSharedPreferences("com.hyenaspam.helloyeen");
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        finish();
    }

    private void changeEditTextMode(EditText editText, boolean state) {
        editText.setFocusable(state);
        editText.setFocusableInTouchMode(state);
        editText.setEnabled(state);
        editText.setCursorVisible(state);
        if (state == true) {
            editText.setTextColor(getResources().getColor(R.color.hyenaDark));
        } else {
            editText.setTextColor(getResources().getColor(R.color.hyenaAccent));
        }
    }
}