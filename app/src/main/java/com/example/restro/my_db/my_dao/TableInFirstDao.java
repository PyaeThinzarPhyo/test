package com.example.restro.my_db.my_dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.restro.my_db.my_entity.TablesInFirst;

import java.util.List;

@Dao
public interface TableInFirstDao {
    @Query("SELECT * FROM TablesInFirst")
    List<TablesInFirst> getAll();


//    @Query("Delete FROM Stores where storeId=:storeid")
//    void deleteByid(String storeid);

    @Insert
    void insert(TablesInFirst historyTable);
    @Delete
    void delete(TablesInFirst historyTable);
}
