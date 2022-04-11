package com.openclassrooms.realestatemanager.domain.usecases.property

import com.openclassrooms.realestatemanager.domain.models.PropertyModel
import com.openclassrooms.realestatemanager.domain.repositories.Repository

class GetPropertyListUseCase {
    suspend fun getList(): Result<List<PropertyModel>> {
        return Repository.getPropertyRepository()!!.getList()
    }
}