package com.example.witxsalon;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.example.witxsalon.adminTasks.AdminPanel;

public class SplashActivity extends AppCompatActivity {

    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the SignupActivity or replace it with your desired destination
                startActivity(new Intent(SplashActivity.this, AdminPanel.class));
                finish(); // Optional: close the SplashActivity to prevent going back
            }
        });
    }
}
