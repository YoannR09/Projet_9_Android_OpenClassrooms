package com.openclassrooms.realestatemanager.domain.usecases.property

import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import com.openclassrooms.realestatemanager.domain.repositories.Repository

class UpdatePropertyUseCase {
    suspend fun updateProperty(property: PropertyEntity) {
        Repository.propertyRepository.updateProperty(property)
    }
}