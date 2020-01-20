package com.example.restro.my_db.my_dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.restro.my_db.my_entity.MyMenuTable;
import java.util.List;

@Dao
public interface MyMenuDao {
    @Query("SELECT * FROM MyMenuTable")
    List<MyMenuTable> getAll();
    @Query("SELECT * FROM MyMenuTable where category_name=:product_code")
    List<MyMenuTable> getAllByCode(String product_code);


//    @Query("Delete FROM Stores where storeId=:storeid")
//    void deleteByid(String storeid);

    @Insert
    void insert(MyMenuTable myMenuTable);
    @Delete
    void delete(MyMenuTable myMenuTable);
}
