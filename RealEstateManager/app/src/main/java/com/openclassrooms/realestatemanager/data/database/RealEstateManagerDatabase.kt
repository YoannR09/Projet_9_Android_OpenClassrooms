package com.openclassrooms.realestatemanager.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.realestatemanager.data.dao.PropertiesDaoRoom
import com.openclassrooms.realestatemanager.data.dao.converters.PictureEntityConverter
import com.openclassrooms.realestatemanager.data.dao.converters.StringConverter
import com.openclassrooms.realestatemanager.data.dao.entities.PictureEntity
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import java.sql.Timestamp
import java.util.*

@Database(entities = [
    PropertyEntity::class,
    PictureEntity::class
], version = 1, exportSchema = false)
@TypeConverters(
    PictureEntityConverter::class,
    StringConverter::class
)
abstract class RealEstateManagerDatabase : RoomDatabase() {
    // --- DAO ---
    abstract fun propertiesDaoRoom(): PropertiesDaoRoom?

    companion object {
        // --- INSTANCE ---
        // --- SINGLETON ---
        lateinit var instance: RealEstateManagerDatabase
            private set

        fun createInstanceTest(context: Context) {
            instance = Room.databaseBuilder(
                    context.applicationContext,
                    RealEstateManagerDatabase::class.java, Timestamp(Date().time).toString() + "ToDocTestAppDB.db")
                    .allowMainThreadQueries()
                    .addCallback(prepopulateDatabase())
                    .build()
        }

        fun createInstance(context: Context) {
            instance = Room.databaseBuilder(context.applicationContext,
                RealEstateManagerDatabase::class.java, "realEstateManagerDB.db")
                .addCallback(prepopulateDatabase())
                .build()
        }

        private fun prepopulateDatabase(): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                }
            }
        }
    }
}