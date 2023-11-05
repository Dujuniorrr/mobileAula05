package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.dao.UserDAO;
import com.example.myapplication.model.User;

public class HomeActivity extends AppCompatActivity {

    TextView textName, textEmail;
    AppCompatButton updateButton, deleteButton, logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = getSharedPreferences("appLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if(sp.getString("id", "null").equals("null")){
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textName = findViewById(R.id.textName);
        textEmail = findViewById(R.id.textEmail);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        logoutButton = findViewById(R.id.logoutButton);

        User user = new UserDAO(getApplicationContext(), new User()).findUser(sp.getString("id", "-1"));
        textName.setText(user.getName());
        textEmail.setText(user.getEmail());

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.remove("id");
                editor.commit();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new UserDAO(getApplicationContext(), new User()).delete(sp.getString("id", "-1"));
                editor.remove("id");
                editor.commit();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, UpdateActivity.class));
            }
        });
    }
}