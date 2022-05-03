package com.openclassrooms.realestatemanager.presentation.fragments.propertyList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.domain.usecases.property.GetPropertyListUseCase
import com.openclassrooms.realestatemanager.presentation.create.uiModels.PropertyTypeUiModel
import com.openclassrooms.realestatemanager.presentation.home.HomeActivitySharedViewModel
import com.openclassrooms.realestatemanager.presentation.mappers.asPropertyListViewModel
import com.openclassrooms.realestatemanager.utils.InterestPoint
import com.openclassrooms.realestatemanager.utils.combineStateFlows
import com.openclassrooms.realestatemanager.utils.scope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class ScreenStatePropertyListFragment

data class ScreenStateError(val error: String) : ScreenStatePropertyListFragment()
object ScreenStateNothing: ScreenStatePropertyListFragment()
object ScreenStateLoading: ScreenStatePropertyListFragment()
object ScreenStateSuccess: ScreenStatePropertyListFragment()
object ScreenStateNoData: ScreenStatePropertyListFragment()

class PropertyListFragmentViewModelFactory(
    private val homeActivitySharedViewModel: HomeActivitySharedViewModel
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = PropertyListFragmentViewModel(homeActivitySharedViewModel) as T
}

class PropertyListFragmentViewModel(
    private val homeActivitySharedViewModel: HomeActivitySharedViewModel
): ViewModel() {

    private val selectedPropertyIndex = MutableStateFlow<Int?>(null)
    val properties = combineStateFlows(viewModelScope, homeActivitySharedViewModel.properties, selectedPropertyIndex) { properties, selectedPropertyIndex ->
        properties.mapIndexed { i, it ->
            it.asPropertyListViewModel(
                isSelected = i == selectedPropertyIndex
            )
        }
    }

    val screenState = MutableStateFlow<ScreenStatePropertyListFragment>(ScreenStateNothing)

    fun loadProperties() {
        scope.launch {
            GetPropertyListUseCase().getList()
                .onSuccess {
                    screenState.value = ScreenStateSuccess
                    homeActivitySharedViewModel.setProperties(it)
                }.onFailure {
                    screenState.value = ScreenStateError(it.message ?: "We have an error")
                }
        }
    }

    fun filterBySize(min: Int, max: Int) {
        properties.value.filter { (it.size in min..max) }
    }

    fun filterByType(type: List<String>) {
        properties.value.filter { type.contains(it.name)}
    }

    fun filterByInterestPoint(type: List<InterestPoint>) {
        //properties.value.filter { type.contains(it)}
    }

    fun selectIndex(index: Int) {
        selectedPropertyIndex.value = index
    }
}