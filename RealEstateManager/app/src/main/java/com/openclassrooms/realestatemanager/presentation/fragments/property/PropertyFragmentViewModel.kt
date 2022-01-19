package com.openclassrooms.realestatemanager.presentation.fragments.property

import android.content.Context
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.domain.usecases.property.GetPropertyByIdUseCase
import com.openclassrooms.realestatemanager.presentation.mappers.asPropertyViewModel
import kotlinx.coroutines.launch

class PropertyFragmentViewModel: ViewModel() {

    val property = MutableLiveData<PropertyOnPropertyFragmentViewModel>()

    fun loadPropertyById(context: Context, id: Int) {
        viewModelScope.launch {
            property.value = GetPropertyByIdUseCase().get(context, id).asPropertyViewModel()
        }
    }
}