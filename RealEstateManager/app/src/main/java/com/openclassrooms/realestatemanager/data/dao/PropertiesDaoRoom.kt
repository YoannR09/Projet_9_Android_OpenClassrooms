package com.openclassrooms.realestatemanager.data.dao

import android.database.Cursor
import androidx.room.*
import com.openclassrooms.realestatemanager.data.dao.converters.PictureEntityConverter
import com.openclassrooms.realestatemanager.data.dao.converters.StringConverter
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity

@Dao
interface PropertiesDaoRoom : PropertiesDao {

    @Query("SELECT * FROM propertyentity")
    override suspend fun list(): List<PropertyEntity>

    @Query("SELECT * FROM propertyentity")
    fun listCursor(): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun setProperty(entity: PropertyEntity?)

    @Query("DELETE FROM propertyentity WHERE id = :id")
    override fun deleteProperty(id: String)

    @Query("SELECT * FROM propertyentity WHERE id = :id")
    override suspend fun getPropertyById(id: String): PropertyEntity

    @Query("UPDATE propertyentity SET state = :state, soldDate = :date WHERE id = :propertyId")
    override suspend fun updateStateProperty(state: String, date: String, propertyId: String)



    // override suspend fun updateProperty(propertyEntity: PropertyEntity) = createProperty(propertyEntity)
}