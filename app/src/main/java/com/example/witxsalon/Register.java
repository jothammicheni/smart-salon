package com.example.witxsalon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;
import com.example.witxsalon.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    TextView backToLogin;
    EditText editName, editPassword, editEmail;
    ProgressBar  progressBar;
    Button btnRegister;
    private AppBarConfiguration appBarConfiguration;
    private ActivityRegisterBinding binding;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        // Set components
        btnRegister = findViewById(R.id.btn_signup);
        backToLogin = findViewById(R.id.back_to_login);
        editEmail = findViewById(R.id.email);
        editName = findViewById(R.id.username);
        editPassword = findViewById(R.id.password);
        progressBar=findViewById(R.id.PBprogress);

        //Firebase instance
        mAuth=FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               signupNewUser();
            }
        });

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
            }
        });
    }

    private void signupNewUser() {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter an Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter a password", Toast.LENGTH_SHORT).show();
            return;
        }

        //now register user

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(Register.this, "User Registered", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            //   Toast.makeText(Register.this, "Registration failed!!"
                            //          + " Please try again later", Toast.LENGTH_SHORT).show();


                            String errorMessage = "";
                            String errorCode = null;
                            if (task.getException() != null) {
                                errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                                switch (errorCode) {
                                    case "ERROR_INVALID_EMAIL":
                                        errorMessage = "Invalid email address";
                                        break;
                                    case "ERROR_WRONG_PASSWORD":
                                        errorMessage = "Incorrect password";
                                        break;
                                    case "ERROR_USER_NOT_FOUND":
                                        errorMessage = "User not found. Please register first.";
                                        break;
                                    // Add more cases for specific error codes if needed
                                }
                            }

                            Toast.makeText(Register.this, errorCode, Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


    }
    }

