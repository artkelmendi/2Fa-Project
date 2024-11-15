package com.example.afinal;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import javax.mail.MessagingException;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private TextView registerTextView;
    private DatabaseHelper databaseHelper;
    private String generatedOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerTextView = findViewById(R.id.signupText);

        databaseHelper = new DatabaseHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (!validateLogin(username, password)) {
                    Toast.makeText(MainActivity.this, "Please enter both username and password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isValidUser = databaseHelper.validateUser(username, password);
                if (isValidUser) {
                    String email = databaseHelper.getUserEmail(username);
                    if (email == null || email.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Email not found for the user.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    generatedOtp = generateOtp();

                    new SendOtpTask(email, generatedOtp).execute();
                } else {
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerTextView.setOnClickListener(v -> {
            Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        });
    }

    private String generateOtp() {
        int otp = 100000 + new Random().nextInt(900000);
        return String.valueOf(otp);
    }

    private boolean validateLogin(String username, String password) {
        return !username.isEmpty() && !password.isEmpty();
    }

    private class SendOtpTask extends AsyncTask<Void, Void, Boolean> {
        private String recipientEmail;
        private String otpCode;

        public SendOtpTask(String recipientEmail, String otpCode) {
            this.recipientEmail = recipientEmail;
            this.otpCode = otpCode;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                EmailActivity2FA emailActivity2FA = new EmailActivity2FA();
                emailActivity2FA.sendOtpEmail(recipientEmail, otpCode);
                return true;
            } catch (MessagingException e) {
                e.printStackTrace();
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Intent intent = new Intent(MainActivity.this, VerifyActivity.class);
                intent.putExtra("otp", otpCode);
                intent.putExtra("email", recipientEmail);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Failed to send OTP. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
