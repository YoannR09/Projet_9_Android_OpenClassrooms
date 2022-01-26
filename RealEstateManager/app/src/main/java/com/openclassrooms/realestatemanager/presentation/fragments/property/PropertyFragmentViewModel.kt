package com.openclassrooms.realestatemanager.presentation.fragments.property

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.domain.usecases.property.GetPropertyByIdUseCase
import com.openclassrooms.realestatemanager.presentation.mappers.asPropertyViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


sealed class ScreenStatePropertyFragment

data class ScreenStateError(val error: String) : ScreenStatePropertyFragment()
object ScreenStateNothing: ScreenStatePropertyFragment()
object ScreenStateLoading: ScreenStatePropertyFragment()
object ScreenStateSuccess: ScreenStatePropertyFragment()
object ScreenStateNoData: ScreenStatePropertyFragment()

class PropertyFragmentViewModel: ViewModel() {

    val screenState = MutableLiveData<ScreenStatePropertyFragment>(ScreenStateNothing)
    val property = MutableLiveData<PropertyOnPropertyFragmentViewModel>()

    fun loadPropertyById(id: Int) {
        screenState.value = ScreenStateLoading
        viewModelScope.launch {
            GetPropertyByIdUseCase()
                .get(id)
                .onSuccess {
                    screenState.value = ScreenStateSuccess
                    property.value = it.asPropertyViewModel()
                }.onFailure {
                    screenState.value = ScreenStateError(it.message ?: "We have an error")
                }
        }
    }
}