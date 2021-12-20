package com.openclassrooms.realestatemanager.data.database;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;


import com.openclassrooms.realestatemanager.data.dao.PropertiesDaoRoom;
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity;

import java.sql.Timestamp;
import java.util.Date;

@Database(entities = {PropertyEntity.class}, version = 1, exportSchema = false)
public abstract class RealEstateManagerDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static RealEstateManagerDatabase INSTANCE;

    // --- DAO ---
    public abstract PropertiesDaoRoom propertiesDaoRoom();

    public static void createInstanceTest(Context context) {
        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                RealEstateManagerDatabase.class, new Timestamp(new Date().getTime()) + "ToDocTestAppDB.db")
                .allowMainThreadQueries()
                .addCallback(prepopulateDatabase())
                .build();
    }

    public static void createInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    RealEstateManagerDatabase.class, "realEstateManagerDB.db")
                    .addCallback(prepopulateDatabase())
                    .build();
        }
    }

    // --- INSTANCE ---
    public static RealEstateManagerDatabase getInstance() {
        return INSTANCE;
    }


    public static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }
        };
    }
}
