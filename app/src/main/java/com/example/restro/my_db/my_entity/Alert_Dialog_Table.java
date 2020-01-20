package com.example.restro.my_db.my_entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Alert_Dialog_Table implements Serializable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableListSpinner() {
        return tableListSpinner;
    }

    public void setTableListSpinner(String tableListSpinner) {
        this.tableListSpinner = tableListSpinner;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "tableListSpinner")
    private  String tableListSpinner;
}
