package com.openclassrooms.realestatemanager.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.RealeStateManagerApplication
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import com.openclassrooms.realestatemanager.domain.usecases.property.GetPropertyListUseCase
import kotlinx.coroutines.launch

class HomeActivityViewModel: ViewModel() {

    val properties = liveData {
        val data = GetPropertyListUseCase().getList(RealeStateManagerApplication.getContextApp()) // loadUser is a suspend function.
        emit(data)
    }
}