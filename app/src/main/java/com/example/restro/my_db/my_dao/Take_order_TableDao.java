package com.example.restro.my_db.my_dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.restro.my_db.my_entity.Take_order_Table;
import java.util.List;


@Dao
public interface Take_order_TableDao {
    @Query("SELECT * FROM Take_order_Table order by id DESC")
    List<Take_order_Table> getAll();

//    @Query("Delete FROM Stores where storeId=:storeid")
//    void deleteByid(String storeid);

    @Insert
    void insert(Take_order_Table myMenuTable);
    @Delete
    void delete(Take_order_Table myMenuTable);
}
