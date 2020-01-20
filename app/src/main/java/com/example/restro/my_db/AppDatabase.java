package com.example.restro.my_db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.restro.my_db.my_converters.Converters;
import com.example.restro.my_db.my_dao.Add_on_TableDao;
import com.example.restro.my_db.my_dao.Alert_Dialog_TableDao;
import com.example.restro.my_db.my_dao.MyMenuDao;
import com.example.restro.my_db.my_dao.OrderTableDao;
import com.example.restro.my_db.my_dao.ProductDao;
import com.example.restro.my_db.my_dao.TableInFirstDao;
import com.example.restro.my_db.my_dao.Take_order_TableDao;
import com.example.restro.my_db.my_dao.UsersDao;
import com.example.restro.my_db.my_entity.Add_on_Table;
import com.example.restro.my_db.my_entity.Alert_Dialog_Table;
import com.example.restro.my_db.my_entity.MyMenuTable;
import com.example.restro.my_db.my_entity.OrderTable;
import com.example.restro.my_db.my_entity.ProductTable;
import com.example.restro.my_db.my_entity.TablesInFirst;
import com.example.restro.my_db.my_entity.Take_order_Table;
import com.example.restro.my_db.my_entity.Users;

@Database(entities ={ TablesInFirst.class, Users.class, ProductTable.class, MyMenuTable.class, OrderTable.class, Take_order_Table.class, Add_on_Table.class, Alert_Dialog_Table.class},version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract TableInFirstDao tableInFirstDao();
    public abstract UsersDao usersDao();
    public abstract ProductDao productDao();
    public abstract MyMenuDao myMenuDao();
    public abstract OrderTableDao orderTableDao();
    public abstract Take_order_TableDao take_order_tableDao();
    public abstract Add_on_TableDao add_on_tableDao();
    public abstract Alert_Dialog_TableDao alert_dialog_tableDao();
}

