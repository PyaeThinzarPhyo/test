package com.example.restro.my_db.my_entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class MyMenuTable implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="category_name")
    private String category_name;

    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String getCategory_name(){
        return category_name;
    }
    public void setCategory_name(String category_name){
        this.category_name=category_name;
    }

}
