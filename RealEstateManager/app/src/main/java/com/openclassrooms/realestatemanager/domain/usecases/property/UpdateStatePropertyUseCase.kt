package com.openclassrooms.realestatemanager.domain.usecases.property

import com.openclassrooms.realestatemanager.domain.repositories.Repository

class UpdateStatePropertyUseCase {
    suspend fun updateStateProperty(state: String, date: String, id: String) {
        Repository.propertyRepository.updateStateProperty(state, date, id)
    }
}