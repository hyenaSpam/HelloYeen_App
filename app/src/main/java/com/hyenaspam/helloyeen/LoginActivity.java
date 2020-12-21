package com.hyenaspam.helloyeen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iterable.iterableapi.IterableApi;

import org.apache.commons.validator.routines.*;

public class LoginActivity extends AppCompatActivity {

    IterableAPIHelper iterableAPIHelper;
    Button signUpButton, skipButton;
    EditText emailText;
    String email;
    // TODO: 11/30/20 implement sign up logic to allow user to input: email, firstname & whatever (no passwords required)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {

        iterableAPIHelper = new IterableAPIHelper().getInstance();

        emailText = findViewById(R.id.loginEmailEditText);

        signUpButton = findViewById(R.id.signUpButton);
        skipButton = findViewById(R.id.skipButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailText.getText().toString();
                if ( !email.isEmpty() || email != null) {
                    email = email.trim();
                    if (email!="" && EmailValidator.getInstance().isValid(email)) {
                        setSignUpButton(email);
                    } else {
                        Toast.makeText(getApplicationContext(),"Please enter a valid email", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Please enter a valid email", Toast.LENGTH_SHORT).show();
                }
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSkipButton();
            }
        });


    }


    private void setSignUpButton(String s) {
        UserProfile.getInstance().setEmail(email);
        iterableAPIHelper.setEmail(UserProfile.getInstance().getEmail());
        String url = String.format("https://app.iterable.com/api/users/%s", UserProfile.getInstance().getEmail());

        IterableAPIHelper.getInstance().getUser(UserProfile.getInstance().getEmail());

        startActivity(new Intent(this, MainActivity.class));

        SharedPreferences sharedPreferences = getSharedPreferences("com.hyenaspam.helloyeen", MODE_PRIVATE);
        sharedPreferences
                .edit()
                .putString("email", UserProfile.getInstance().getEmail())
                .apply();


    }

    public void setSkipButton(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}