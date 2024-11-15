package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class VerifyActivity extends AppCompatActivity {

    private EditText otpEditText;
    private Button verifyOtpButton;
    private String generatedOtp;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        otpEditText = findViewById(R.id.otpET);
        verifyOtpButton = findViewById(R.id.verifyBtn);

        generatedOtp = getIntent().getStringExtra("otp");
        email = getIntent().getStringExtra("email");

        verifyOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredOtp = otpEditText.getText().toString();
                verifyOtp(enteredOtp);
            }
        });
    }

    private void verifyOtp(String enteredOtp) {
        if (enteredOtp.equals(generatedOtp)) {
            Toast.makeText(VerifyActivity.this, "OTP Verified Successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(VerifyActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(VerifyActivity.this, "Invalid OTP. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
