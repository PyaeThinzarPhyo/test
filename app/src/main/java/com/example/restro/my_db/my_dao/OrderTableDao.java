package com.example.restro.my_db.my_dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.restro.my_db.my_entity.OrderTable;

import java.util.List;

@Dao
public interface OrderTableDao {
    @Query("SELECT * FROM OrderTable order by id DESC")
    List<OrderTable> getAll();

//    @Query("Delete FROM Stores where storeId=:storeid")
//    void deleteByid(String storeid);

    @Insert
    void insert(OrderTable myMenuTable);
    @Delete
    void delete(OrderTable myMenuTable);
}
