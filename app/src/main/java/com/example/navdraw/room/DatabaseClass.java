package com.example.navdraw.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserRoomModel.class}, version = 6)
public abstract class DatabaseClass extends RoomDatabase {

    public abstract DAO getDao();

    private static DatabaseClass instance;


    static DatabaseClass getDatabase(final Context context) {
        if (instance == null) {
            synchronized (DatabaseClass.class) {
                instance = Room.databaseBuilder(context, DatabaseClass.class, "DATABASE")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return instance;


    }


}
