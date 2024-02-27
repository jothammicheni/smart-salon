package com.example.witxsalon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.witxsalon.adminTasks.AddNewProduct;
import com.example.witxsalon.adminTasks.AdminPanel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class login extends AppCompatActivity {
TextView backToRegister,editEmail,editPassword;
ProgressBar setprogressBar;
Button  btnLogin;
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        backToRegister=findViewById(R.id.back_to_signup) ;
        btnLogin =findViewById(R.id.btn_login);
        setprogressBar=findViewById(R.id.PBprogress);
        editPassword=findViewById(R.id.password);
        editEmail=findViewById(R.id.email);
        mAuth=FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //LoginUser();
               Intent intent=new Intent(getApplicationContext(),MainActivity.class);
               startActivity(intent);


            }
        });
        backToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Register.class);
                startActivity(intent);

            }
        });

    }

    public void LoginUser(){
        String email=editEmail.getText().toString().trim();
        String password=editPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        //sign in the user
        setprogressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                         if(task.isSuccessful()){
                             Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                             startActivity(intent);
                             Toast.makeText(login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();

                             setprogressBar.setVisibility(View.GONE);
                         }
                         else {
                             //   Toast.makeText(login.this, "Error Logging in", Toast.LENGTH_SHORT).show();

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

                             Toast.makeText(login.this, errorCode, Toast.LENGTH_SHORT).show();
                             setprogressBar.setVisibility(View.GONE);
                         }
                    }
                });

    }
}