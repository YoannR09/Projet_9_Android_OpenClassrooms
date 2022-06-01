package com.openclassrooms.realestatemanager.presentation.fragments.propertyList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.presentation.home.HomeActivitySharedViewModel
import com.openclassrooms.realestatemanager.presentation.mappers.asPropertyListViewModel
import com.openclassrooms.realestatemanager.utils.combineStateFlows

class MapFragmentViewModelFactory(
    private val homeActivitySharedViewModel: HomeActivitySharedViewModel
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = MapFragmentViewModel(homeActivitySharedViewModel) as T
}


class MapFragmentViewModel(
    private val homeActivitySharedViewModel: HomeActivitySharedViewModel
): ViewModel() {

    val properties = homeActivitySharedViewModel.properties
}