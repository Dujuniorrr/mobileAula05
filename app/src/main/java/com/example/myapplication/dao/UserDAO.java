package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.helper.DatabaseConn;
import com.example.myapplication.model.User;

public class UserDAO {

    private User user;
    private DatabaseConn conn;
    private SQLiteDatabase db;

    public UserDAO(Context ctx, User user) {
        this.user = user;
        this.conn = new DatabaseConn(ctx);
        this.db = this.conn.getReadableDatabase();
    }

    public Boolean createUser(){
        try{
            ContentValues values = new ContentValues();
            values.put("name", user.getName());
            values.put("email", user.getEmail());
            values.put("phone", user.getPhone());
            values.put("password", user.getPassword());

            db.insert(user.getTableName(), null, values);

            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public Boolean verifyLogin(){
        Cursor cursor = this.db.rawQuery(
                "SELECT * FROM user WHERE email LIKE ? AND password LIKE ?",
                new String[]{this.user.getEmail(), this.user.getPassword()}
                );
        if (cursor.getCount() > 0) return true;

        return false;
    }


}
