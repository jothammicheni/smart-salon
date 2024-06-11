package com.example.rabbitmanagementsystem;

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

import com.example.rabbitmanagementsystem.adminTasks.AdminPanel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class login extends AppCompatActivity {
TextView editEmail,editPassword;
ProgressBar setprogressBar;

Button  btnToAdmin;
Button  btnLogin;
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnToAdmin=findViewById(R.id.back_to_signup) ;
        btnLogin =findViewById(R.id.btn_login);
        setprogressBar=findViewById(R.id.PBprogress);
        editPassword=findViewById(R.id.password);
        editEmail=findViewById(R.id.email);
        mAuth=FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               LoginUser();
//               Intent intent=new Intent(getApplicationContext(),MainActivity.class);
//               startActivity(intent);


            }
        });
        btnToAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=editEmail.getText().toString().trim();
                String password=editPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(login.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(login.this, "Enter valid password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!TextUtils.isEmpty(email)&&email.equals("admin@gmail.com")&&password.equals("brian2372")){

                    Intent intent=new Intent(getApplicationContext(), AdminPanel.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(login.this, "Admin Not found", Toast.LENGTH_SHORT).show();
                }



//

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
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(login.this, "Login Failed try Again" + e, Toast.LENGTH_SHORT).show();
                    }
                });

    }
}