package com.example.restro.my_db.my_dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.restro.my_db.my_entity.TablesInFirst;
import com.example.restro.my_db.my_entity.Users;

import java.util.List;
@Dao
public interface UsersDao {
    @Query("SELECT * FROM TablesInFirst")
    List<Users> getAll();


//    @Query("Delete FROM Stores where storeId=:storeid")
//    void deleteByid(String storeid);

    @Insert
    void insert(Users historyTable);
    @Delete
    void delete(TablesInFirst historyTable);
}
