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
    }

    public Boolean create(){
        this.db = this.conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("phone", user.getPhone());
        values.put("password", user.getPassword());

        long id = db.insert(user.getTableName(), null, values);
        this.user.setId(Long.toString(id));

        if (id > 0) {
            return true;
        }

        return false;
    }

    public Boolean update(String id){
        this.db = this.conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("phone", user.getPhone());

        if(this.db.update(this.user.getTableName(), values, "id = ?", new String[]{ id }) > 0){
            return true;
        }

        return false;
    }

    public Boolean delete(String id){
        this.db = this.conn.getWritableDatabase();
        if(db.delete(this.user.getTableName(), "id = ?", new String[]{id}) > 0){
            return true;
        }
        return false;
    }

    public User findUser(String id){
        this.db = this.conn.getWritableDatabase();
        String sql = "SELECT * FROM user WHERE id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{id});

        if(cursor.moveToFirst()){
            this.user.setId(cursor.getString(cursor.getColumnIndexOrThrow("id")));
            this.user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            this.user.setPhone(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
            this.user.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        }

        return this.user;
    }

    public Boolean verifyLogin(){
        this.db = this.conn.getReadableDatabase();

        Cursor cursor = this.db.rawQuery(
                "SELECT * FROM user WHERE email LIKE ? AND password LIKE ?",
                new String[]{this.user.getEmail(), this.user.getPassword()}
                );

        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            this.findUser(cursor.getString(cursor.getColumnIndexOrThrow("id")));
            return true;
        }

        return false;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
