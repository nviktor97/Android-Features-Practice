package com.example.navdraw.room;

import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DAO {

    @Insert
    void insertAllData(UserRoomModel model);

    @Insert(onConflict = IGNORE)
    void insertAll(List<UserRoomModel> inventoryObjects);


    //Select All Data
    @Query("select * from  user")
    List<UserRoomModel> getAllData();

    //DELETE DATA
    @Query("delete from user where 'id'= :id")
    void deleteData(int id);

//    @Query("delete from UserRoomModel")
    @Query("delete from user")
    void deleteAllData();
}
