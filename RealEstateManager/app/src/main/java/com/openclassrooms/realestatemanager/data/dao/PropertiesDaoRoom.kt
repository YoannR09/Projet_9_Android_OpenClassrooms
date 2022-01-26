package com.openclassrooms.realestatemanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity

@Dao
interface PropertiesDaoRoom : PropertiesDao {

    @Query("SELECT * FROM propertyentity")
    override suspend fun list(): List<PropertyEntity>

    @Insert
    override suspend fun createProperty(entity: PropertyEntity?)

    @Query("DELETE FROM propertyentity WHERE id = :id")
    override fun deleteProperty(id: Int)

    @Query("SELECT * FROM propertyentity WHERE id = :id")
    override suspend fun getPropertyById(id: Int): PropertyEntity
}