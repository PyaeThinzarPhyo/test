package com.example.restro.my_db.my_entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class OrderTable implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="order_list")
    private ArrayList<String> order_list;
    @ColumnInfo(name="price_list")
    private ArrayList<String> price_list;
    @ColumnInfo(name="table_name")
    private String table_name;
    @ColumnInfo(name="floor")
    private String floor;
    @ColumnInfo(name="date")
    private String date;
    @ColumnInfo(name="status")
    private String status;
    @ColumnInfo(name="Time")
    private String time;
    @ColumnInfo(name="Total")
    private String total;

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    @ColumnInfo(name = "Invoice")
    private String invoice;
    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getOrder_list(){
        return order_list;
    }
    public void setOrder_list(ArrayList order_list){
        this.order_list=order_list;
    }
    public ArrayList<String> getPrice_list(){
        return price_list;
    }
    public void setPrice_list(ArrayList price_list){
        this.price_list=price_list;
    }

    public String getTable_name(){
        return table_name;
    }
    public void setTable_name(String table_name){
        this.table_name=table_name;
    }
    public String getFloor(){
        return floor;
    }
    public void setFloor(String floor){
        this.floor=floor;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date=date;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time=time;
    }
    public String getTotal(){
        return total;
    }
    public void setTotal(String total){
        this.total=total;
    }

}
