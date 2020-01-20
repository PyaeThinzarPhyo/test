package com.example.restro.my_db.my_dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.restro.my_db.my_entity.Alert_Dialog_Table;

import java.util.List;

@Dao
public interface Alert_Dialog_TableDao {
    @Query( "SELECT * FROM Alert_Dialog_Table")
    List<Alert_Dialog_Table> getAll();

    @Insert
    void insert(Alert_Dialog_Table assignStudent);
    @Query( "SELECT * FROM Alert_Dialog_Table WHERE tableListSpinner=:tableListSpinner" )
    List<Alert_Dialog_Table> getAllbySpinnerfromAdd_On(String tableListSpinner);
}
