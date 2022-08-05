package com.openclassrooms.realestatemanager.presentation.home

import android.view.View
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.utils.mapState
import com.openclassrooms.realestatemanager.utils.scope
import kotlinx.coroutines.flow.MutableStateFlow

class HomeActivityViewModel: ViewModel() {

    val idSelected = MutableStateFlow<String?>(null)

    val fragmentState = MutableStateFlow(HomeFragmentState.LIST)

    val mapButton = fragmentState.mapState(scope) {
        when(it) {
            HomeFragmentState.MAP -> View.GONE
            HomeFragmentState.LIST -> View.VISIBLE
        }
    }

    val listButton = fragmentState.mapState(scope) {
        when(it) {
            HomeFragmentState.MAP -> View.VISIBLE
            HomeFragmentState.LIST -> View.GONE
        }
    }

    fun changeSelectId(id: String) {
        idSelected.value = id
    }
}
