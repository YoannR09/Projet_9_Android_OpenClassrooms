package com.openclassrooms.realestatemanager.presentation.home

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.domain.models.PropertyModel
import kotlinx.coroutines.flow.MutableStateFlow

class HomeActivitySharedViewModel: ViewModel() {

    val properties = MutableStateFlow<List<PropertyModel>>(listOf())

}
