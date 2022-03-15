package com.openclassrooms.realestatemanager.presentation.fragments.property

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.domain.usecases.property.GetPropertyByIdUseCase
import com.openclassrooms.realestatemanager.presentation.mappers.asPropertyViewModel
import com.openclassrooms.realestatemanager.utils.scope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


sealed class ScreenStatePropertyFragment

data class ScreenStateError(val error: String) : ScreenStatePropertyFragment()
object ScreenStateNothing: ScreenStatePropertyFragment()
object ScreenStateLoading: ScreenStatePropertyFragment()
object ScreenStateSuccess: ScreenStatePropertyFragment()
object ScreenStateNoData: ScreenStatePropertyFragment()

class PropertyFragmentViewModel: ViewModel() {

    val screenState = MutableStateFlow<ScreenStatePropertyFragment>(ScreenStateNothing)
    val property = MutableLiveData<PropertyOnPropertyFragmentViewModel>()

    fun loadPropertyById(id: String) {
        screenState.value = ScreenStateLoading
        scope.launch {
            GetPropertyByIdUseCase()
                .get(id)
                .onFailure {
                    screenState.value = ScreenStateError(it.message ?: "We have an error")
                }
                .onSuccess {
                    screenState.value = ScreenStateSuccess
                    property.value = it.asPropertyViewModel()
                }
        }
    }
}