package com.openclassrooms.realestatemanager.domain.usecases.property

import android.content.Context
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import com.openclassrooms.realestatemanager.domain.repositories.Repository

class CreatePropertyUseCase {

    suspend fun createProperty(context: Context, entity: PropertyEntity) {
        Repository.getPropertyRepository(context)?.createProperty(entity)
    }
}