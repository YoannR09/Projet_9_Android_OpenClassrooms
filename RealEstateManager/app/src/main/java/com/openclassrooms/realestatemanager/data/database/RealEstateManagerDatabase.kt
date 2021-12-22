package com.openclassrooms.realestatemanager.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.realestatemanager.data.dao.PropertiesDaoRoom
import com.openclassrooms.realestatemanager.data.dao.converters.PictureEntityConverter
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import java.sql.Timestamp
import java.util.*

@Database(entities = [PropertyEntity::class], version = 1, exportSchema = false)
abstract class RealEstateManagerDatabase : RoomDatabase() {
    // --- DAO ---
    abstract fun propertiesDaoRoom(): PropertiesDaoRoom?

    companion object {
        // --- INSTANCE ---
        // --- SINGLETON ---
        var instance: RealEstateManagerDatabase? = null
            private set

        fun createInstanceTest(context: Context) {
            instance = Room.databaseBuilder(
                    context.applicationContext,
                    RealEstateManagerDatabase::class.java, Timestamp(Date().time).toString() + "ToDocTestAppDB.db")
                    .addTypeConverter(PictureEntityConverter)
                    .allowMainThreadQueries()
                    .addCallback(prepopulateDatabase())
                    .build()
        }

        @JvmStatic
        fun createInstance(context: Context) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        RealEstateManagerDatabase::class.java, "realEstateManagerDB.db")
                        .addTypeConverter(PictureEntityConverter)
                        .addCallback(prepopulateDatabase())
                        .build()
            }
        }

        fun prepopulateDatabase(): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                }
            }
        }
    }
}