package com.openclassrooms.realestatemanager.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class HomeActivityViewModel: ViewModel() {

    // val properties = liveData {
    //    val data = GetPropertyListUseCase().getList(RealeStateManagerApplication.getContextApp()) // loadUser is a suspend function.
    //    emit(data)
    //}

    var isLargeScreen: Boolean = false

    val idSelected = MutableStateFlow("id")

    fun changeSelectId(id: String) {
        idSelected.value = id
    }}

