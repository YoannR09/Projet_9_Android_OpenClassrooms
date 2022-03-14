package com.openclassrooms.realestatemanager.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

var _viewModelScope : CoroutineScope?= null
val ViewModel.scope get() = _viewModelScope ?: viewModelScope