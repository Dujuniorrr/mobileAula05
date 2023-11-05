package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dao.UserDAO;
import com.example.myapplication.model.User;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPassword, editTextName, editTextPhone, editTextPasswordConfirm;
    AppCompatButton buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail = findViewById(R.id.EditTextEmail);
        editTextName = findViewById(R.id.EditTextName);
        editTextPassword = findViewById(R.id.EditTextPassword);
        editTextPhone = findViewById(R.id.EditTextPhone);
        editTextPasswordConfirm = findViewById(R.id.EditTextPasswordConfirm);
        buttonSignUp = findViewById(R.id.ButtonSignUp);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//              startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                if(
                        TextUtils.isEmpty(editTextPassword.getText()) || TextUtils.isEmpty(editTextName.getText()) || TextUtils.isEmpty(editTextPhone.getText()) || TextUtils.isEmpty(editTextEmail.getText()) || TextUtils.isEmpty(editTextPasswordConfirm.getText())
                ){
                    Toast.makeText(RegisterActivity.this, "É necessário preencher todos os campos!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(editTextPassword.getText().toString().equals(editTextPasswordConfirm.getText().toString())){
                        UserDAO userDao = new UserDAO(RegisterActivity.this,
                                new User(
                                        editTextName.getText().toString(),
                                        editTextPassword.getText().toString(),
                                        editTextEmail.getText().toString(),
                                        editTextPhone.getText().toString()));
                        if(userDao.create()){

                            SharedPreferences sp = getSharedPreferences("appLogin",
                                    Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("id", userDao.getUser().getId());
                            editor.commit();

                            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Houve algum problema no seu cadastro!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
//                        Toast.makeText(RegisterActivity.this, "As senhas não correspodem!", Toast.LENGTH_SHORT).show();
                         editTextPassword.setError("As senhas não correspodem");
                    }
                }

            }
        });

    }
}