package com.example.restro.my_db.my_entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class TablesInFirst implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="table_name")
    private String tablename;
    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTablename(){
        return tablename;
    }
    public void setTablename(String tablename){
        this.tablename=tablename;
    }
}
