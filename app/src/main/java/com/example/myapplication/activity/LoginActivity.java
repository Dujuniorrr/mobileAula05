package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dao.UserDAO;
import com.example.myapplication.model.User;

public class LoginActivity extends AppCompatActivity {
    AppCompatButton buttonSignIn, buttonSignUp;
    EditText editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonSignUp = findViewById(R.id.ButtonSignUp);
        buttonSignIn = findViewById(R.id.ButtonSignIn);
        editTextEmail = findViewById(R.id.EditTextEmail);
        editTextPassword = findViewById(R.id.EditTextPassword);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(new UserDAO(LoginActivity.this, new User(editTextEmail.getText().toString(), editTextPassword.getText().toString())).verifyLogin()){
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }
                else{
                    Toast.makeText(LoginActivity.this, "Email ou senha inválido!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}