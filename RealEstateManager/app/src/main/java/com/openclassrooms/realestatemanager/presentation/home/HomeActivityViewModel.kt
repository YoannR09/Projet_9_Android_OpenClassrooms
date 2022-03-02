package com.openclassrooms.realestatemanager.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeActivityViewModel: ViewModel() {

    // val properties = liveData {
    //    val data = GetPropertyListUseCase().getList(RealeStateManagerApplication.getContextApp()) // loadUser is a suspend function.
    //    emit(data)
    //}

    var isLargeScreen: Boolean = false

    val idSelected = MutableLiveData("id")

    fun changeSelectId(id: String) {
        idSelected.value = id
    }}

