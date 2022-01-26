package com.openclassrooms.realestatemanager.presentation.fragments.propertyList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.domain.usecases.property.GetPropertyListUseCase
import kotlinx.coroutines.launch

sealed class ScreenStatePropertyListFragment

data class ScreenStateError(val error: String) : ScreenStatePropertyListFragment()
object ScreenStateNothing: ScreenStatePropertyListFragment()
object ScreenStateLoading: ScreenStatePropertyListFragment()
object ScreenStateSuccess: ScreenStatePropertyListFragment()
object ScreenStateNoData: ScreenStatePropertyListFragment()

class PropertyListFragmentViewModel: ViewModel() {

    val properties = MutableLiveData<List<PropertyOnPropertyListFragmentViewModel>>(listOf())
    val screenState = MutableLiveData<ScreenStatePropertyListFragment>(ScreenStateNothing)

    fun loadProperties() {
        viewModelScope.launch {
            GetPropertyListUseCase().getList()
                .onSuccess {
                    screenState.value = ScreenStateSuccess
                    properties.value = it
                }.onFailure {
                    screenState.value = ScreenStateError(it.message ?: "We have an error")
                }
        }
    }




}