package com.example.restro.my_db.my_entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Add_on_Table implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getAdd_name() {
        return add_name;
    }



    @ColumnInfo(name = "add_name")
    private String add_name;
    @ColumnInfo(name = "product_id")
    private int productId;
    @ColumnInfo(name = "category_id")
    private int categoryId;

    public void setAdd_name(String add_name) {
        this.add_name = add_name;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
