package com.openclassrooms.realestatemanager.presentation.create

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import com.openclassrooms.realestatemanager.domain.usecases.property.CreatePropertyUseCase
import kotlinx.coroutines.launch

class CreatePropertyActivityViewModel: ViewModel() {


    fun createProperty(context: Context, property: PropertyEntity) {
        viewModelScope.launch {
            CreatePropertyUseCase().createProperty(context, property)
        }
    }
}