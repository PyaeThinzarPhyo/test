package com.example.restro.my_db.my_entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class ProductTable implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="image_uri")
    private String image_uri;
    @ColumnInfo(name="product_code")
    private String product_code;
    @ColumnInfo(name="product_name")
    private String product_name;
    @ColumnInfo(name="prize")
    private String prize;
    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name(){
        return product_name;
    }
    public void setProduct_name(String product_name){
        this.product_name=product_name;
    }
    public String getProduct_code(){
        return product_code;
    }
    public void setProduct_code(String product_code){
        this.product_code=product_code;
    }
    public String getPrize(){
        return prize;
    }
    public void setPrize(String prize){
        this.prize=prize;
    }
    public String getImage_uri(){
        return image_uri;
    }
    public void setImage_uri(String image_uri){
        this.image_uri=image_uri;
    }
}
