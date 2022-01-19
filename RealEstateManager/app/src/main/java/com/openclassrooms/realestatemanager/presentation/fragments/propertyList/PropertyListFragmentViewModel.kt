package com.openclassrooms.realestatemanager.presentation.fragments.propertyList

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.domain.usecases.property.GetPropertyListUseCase
import kotlinx.coroutines.launch

class PropertyListFragmentViewModel: ViewModel() {

    val properties = MutableLiveData<List<PropertyOnPropertyListFragmentViewModel>>(listOf())

    fun loadProperties(context: Context) {
        viewModelScope.launch {
            properties.value = GetPropertyListUseCase().getList(context)
        }
    }




}