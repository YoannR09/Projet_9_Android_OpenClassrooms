package com.openclassrooms.realestatemanager.data.dao

import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity

interface PropertiesDao {
    suspend fun list(): List<PropertyEntity>
    fun createProperty(entity: PropertyEntity?)
    fun deleteProperty(id: String?)
}