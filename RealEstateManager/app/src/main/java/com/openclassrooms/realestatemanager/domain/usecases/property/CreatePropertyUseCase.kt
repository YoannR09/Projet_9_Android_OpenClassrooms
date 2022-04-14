package com.openclassrooms.realestatemanager.domain.usecases.property

import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import com.openclassrooms.realestatemanager.domain.repositories.Repository

class CreatePropertyUseCase {

    suspend fun createProperty(entity: PropertyEntity) {
        Repository.propertyRepository.createProperty(entity)
    }
}