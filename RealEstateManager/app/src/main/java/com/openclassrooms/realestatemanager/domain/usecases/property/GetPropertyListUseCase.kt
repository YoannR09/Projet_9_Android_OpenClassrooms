package com.openclassrooms.realestatemanager.domain.usecases.property

import androidx.lifecycle.LiveData
import android.content.Context
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import com.openclassrooms.realestatemanager.domain.repositories.Repository

class GetPropertyListUseCase {
    suspend fun getList(context: Context): List<PropertyEntity> {
        return Repository.getPropertyRepository(context)!!.getList()
    }
}