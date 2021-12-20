package com.openclassrooms.realestatemanager.domain.repositories

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.data.dao.PropertiesDao
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity

class PropertyRepository(private val dao: PropertiesDao) {

    suspend fun getList(): List<PropertyEntity> {
        return this.dao.list()
    }
}