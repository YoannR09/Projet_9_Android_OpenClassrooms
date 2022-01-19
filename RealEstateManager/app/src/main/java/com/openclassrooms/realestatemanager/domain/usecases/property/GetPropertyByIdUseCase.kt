package com.openclassrooms.realestatemanager.domain.usecases.property

import android.content.Context
import com.openclassrooms.realestatemanager.domain.models.PropertyModel
import com.openclassrooms.realestatemanager.domain.repositories.Repository

class GetPropertyByIdUseCase {
    suspend fun get(context: Context, id: Int): PropertyModel {
        return Repository.getPropertyRepository(context)!!.getPropertyById(id)
    }
}