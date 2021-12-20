package com.openclassrooms.realestatemanager.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity

@Dao
interface PropertiesDaoRoom : PropertiesDao {

    @Query("SELECT * FROM property")
    override suspend fun list(): List<PropertyEntity>
    @Insert
    override fun createProperty(entity: PropertyEntity?)
    @Query("DELETE FROM property WHERE id = :id")
    override fun deleteProperty(id: String?)
}