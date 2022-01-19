package com.openclassrooms.realestatemanager.domain.repositories

import com.openclassrooms.realestatemanager.data.dao.PropertiesDao
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import com.openclassrooms.realestatemanager.domain.mappers.asModel
import com.openclassrooms.realestatemanager.domain.models.PropertyModel

class PropertyRepository(private val dao: PropertiesDao) {

    suspend fun getList(): List<PropertyModel> {
        val listModel = ArrayList<PropertyModel>()
        for(entity in this.dao.list()) {
            listModel.add(entity.asModel())
        }
        return listModel
    }

    suspend fun getPropertyById(id: Int): PropertyModel {
        return this.dao.getPropertyById(id).asModel()
    }

    suspend fun createProperty(propertyEntity: PropertyEntity) {
        return this.dao.createProperty(propertyEntity)
    }
}