package com.openclassrooms.realestatemanager.presentation.fragments.property

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PropertyFragmentViewModel: ViewModel() {

    val property = MutableLiveData<PropertyOnPropertyFragmentViewModel>()

    fun loadPropertyById(id: String) {
        Handler().postDelayed({
            property.value = PropertyOnPropertyFragmentViewModel(
                id = id,
                name = "name $id",
                city = "Paris $id",
                price = "My price is 55463"
            )
        }, 1000)

    }
}