package com.openclassrooms.realestatemanager.data.dao

import androidx.room.Query
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity

interface PropertiesDao {
    suspend fun list(): List<PropertyEntity>
    suspend fun createProperty(entity: PropertyEntity?)
    suspend fun getPropertyById(id: String): PropertyEntity
    fun deleteProperty(id: String)
    suspend fun updateStateProperty(state: String, date: String, propertyId: String)
    // suspend fun updateProperty(propertyEntity: PropertyEntity)
}