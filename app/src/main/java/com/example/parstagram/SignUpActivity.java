package com.example.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
    public static final String TAG = "SignUpActivity";
    private EditText etSetUsername;
    private EditText etSetPassword;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        etSetUsername = findViewById(R.id.etSetUsername);
        etSetPassword = findViewById(R.id.etSetPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick signup button");
                String username = etSetUsername.getText().toString();
                String password = etSetPassword.getText().toString();
                Log.i(TAG, "signup user called");
                signupUser(username, password);
            }
        });
    }

    private void signupUser(String username, String password) {
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    goLoginActivity();
                    Toast.makeText(SignUpActivity.this,"Signup success", Toast.LENGTH_SHORT).show();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Log.e(TAG, "Signup issue", e);
                    Toast.makeText(SignUpActivity.this,"Signup failed", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    private void goLoginActivity() {
        Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(i);
        // allows back button not to lead us back to login
        finish();
    }
}
