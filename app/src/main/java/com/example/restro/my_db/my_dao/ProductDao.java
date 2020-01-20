package com.example.restro.my_db.my_dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.restro.my_db.my_entity.MyMenuTable;
import com.example.restro.my_db.my_entity.ProductTable;

import java.util.List;

@Dao
public interface ProductDao  {
    @Query("SELECT * FROM ProductTable")
    List<ProductTable> getAll();

    @Query("SELECT * FROM ProductTable where product_code=:product_code")
    List<ProductTable> getAllByProductCode(String product_code);

    @Query("SELECT * FROM ProductTable where product_name=:product_name")
    List<ProductTable> getByProductName(String product_name);

    @Query("SELECT * FROM ProductTable where product_name=:product_name and product_code=:product_code")
    List<ProductTable> getDuplicateProduct(String product_name,String product_code);
    @Query("SELECT * FROM ProductTable where product_code=:product_name")
    List<ProductTable> getAllByCode(String product_name);


//    @Query("Delete FROM Stores where storeId=:storeid")
//    void deleteByid(String storeid);

    @Insert
    void insert(ProductTable productTable);
    @Delete
    void delete(ProductTable productTable);

}
