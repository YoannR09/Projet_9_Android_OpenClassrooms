package com.openclassrooms.realestatemanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverters
import com.openclassrooms.realestatemanager.data.dao.converters.PictureEntityConverter
import com.openclassrooms.realestatemanager.data.dao.converters.StringConverter
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity

@Dao
interface PropertiesDaoRoom : PropertiesDao {

    @Query("SELECT * FROM propertyentity")
    override suspend fun list(): List<PropertyEntity>

    @Insert
    override suspend fun createProperty(entity: PropertyEntity?)

    @Query("DELETE FROM propertyentity WHERE id = :id")
    override fun deleteProperty(id: String)

    @Query("SELECT * FROM propertyentity WHERE id = :id")
    override suspend fun getPropertyById(id: String): PropertyEntity

    @Query("UPDATE propertyentity SET state = :state, soldDate = :date WHERE id = :propertyId")
    override suspend fun updateStateProperty(state: String, date: String, propertyId: String)

    //@Query("UPDATE propertyentity SET description = :propertyEntity WHERE id = 'todo'")
    //suspend fun updateProperty(propertyEntity: PropertyEntity)
}