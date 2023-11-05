package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dao.UserDAO;
import com.example.myapplication.model.User;

public class UpdateActivity extends AppCompatActivity {

    AppCompatEditText editTextName, editTextPhone, editTextEmail;
    AppCompatButton buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences("appLogin", Context.MODE_PRIVATE);

        setContentView(R.layout.activity_update);

        editTextName = findViewById(R.id.EditTextName);
        editTextEmail = findViewById(R.id.EditTextEmail);
        editTextPhone = findViewById(R.id.EditTextPhone);
        buttonUpdate = findViewById(R.id.ButtonUpdate);

        User user = new UserDAO(getApplicationContext(), new User()).findUser(sp.getString("id", "-1"));

        editTextName.setText(user.getName());
        editTextEmail.setText(user.getEmail());
        editTextPhone.setText(user.getPhone());

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(
                        TextUtils.isEmpty(editTextName.getText()) || TextUtils.isEmpty(editTextPhone.getText()) || TextUtils.isEmpty(editTextEmail.getText())
                ){
                    Toast.makeText(UpdateActivity.this, "É necessário preencher todos os campos!", Toast.LENGTH_SHORT).show();
                }
               else if( new UserDAO(getApplicationContext(), new User(
                       editTextName.getText().toString(),
                       null,
                       editTextEmail.getText().toString(),
                       editTextPhone.getText().toString()
               )).update(sp.getString("id", "-1"))){
                    startActivity(new Intent(UpdateActivity.this, HomeActivity.class));
               }
               else{
                    Toast.makeText(UpdateActivity.this, "Ocorreu algum erro interno!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}