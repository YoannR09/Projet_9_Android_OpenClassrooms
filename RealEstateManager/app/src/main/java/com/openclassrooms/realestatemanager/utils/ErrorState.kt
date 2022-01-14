package com.openclassrooms.realestatemanager.utils

import androidx.lifecycle.MutableLiveData

class ErrorState {

    companion object {
        val errorState = MutableLiveData<String>()
    }
}