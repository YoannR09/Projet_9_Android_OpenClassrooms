package com.openclassrooms.realestatemanager.presentation.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class HomeActivityViewModel: ViewModel() {

    var isLargeScreen: Boolean = false

    val idSelected = MutableStateFlow("id")

    fun changeSelectId(id: String) {
        idSelected.value = id
    }
}
