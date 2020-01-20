package com.example.restro.my_db.my_entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class Users implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="user_name")
    private String user_name;
    @ColumnInfo(name="password")
    private String password;
    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name(){
        return user_name;
    }
    public void setUser_name(String user_name){
        this.user_name=user_name;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.user_name=password;
    }
}
