package com.example.restro.my_db.my_dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.restro.my_db.my_entity.Add_on_Table;
import com.example.restro.my_db.my_entity.MyMenuTable;

import java.util.List;

@Dao
public interface Add_on_TableDao {
    @Query("SELECT * FROM Add_on_Table")
    List<Add_on_Table> getAll();
    @Query("SELECT * FROM Add_on_Table where add_name=:product_code")
    List<Add_on_Table> getAllByCode(String product_code);
    @Query("SELECT * FROM Add_on_Table where product_id=:product_id")
    List<Add_on_Table> getAllByProductId(int product_id);

//    @Query("Delete FROM Stores where storeId=:storeid")
//    void deleteByid(String storeid);

    @Insert
    void insert(Add_on_Table add_on_table);
    @Delete
    void delete(Add_on_Table add_on_table);
}
