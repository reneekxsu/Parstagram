package com.example.parstagram.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parstagram.MainActivity;
import com.example.parstagram.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvSignUpClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ParseUser.getCurrentUser()!=null){
            goFeedActivity();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });
        tvSignUpClick = findViewById(R.id.tvSignUpClick);
        tvSignUpClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick sign up button");
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Trying to login user " + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null){
                    Log.e(TAG, "login issue", e);
                    Toast.makeText(LoginActivity.this,"Wrong username or password", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // navigate to main activity if sign in credentials are correct
                    goFeedActivity();
                    Toast.makeText(LoginActivity.this,"Login success", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goFeedActivity() {
//        Intent i = new Intent(this, FeedActivity.class);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        // allows back button not to lead us back to login
        finish();
    }
}