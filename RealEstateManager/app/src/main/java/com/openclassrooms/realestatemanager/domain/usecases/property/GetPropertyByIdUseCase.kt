package com.openclassrooms.realestatemanager.domain.usecases.property

import com.openclassrooms.realestatemanager.domain.models.PropertyModel
import com.openclassrooms.realestatemanager.domain.repositories.Repository

class GetPropertyByIdUseCase {
    suspend fun get(id: String): Result<PropertyModel> {
        return Repository.getPropertyRepository()!!.getPropertyById(id)
    }
}