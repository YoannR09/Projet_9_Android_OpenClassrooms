package com.openclassrooms.realestatemanager.presentation.fragments.propertyList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PropertyListFragmentViewModel: ViewModel() {

    val properties = MutableLiveData<List<PropertyOnPropertyListFragmentViewModel>>(listOf())

    fun loadProperties() {
        val array = ArrayList<PropertyOnPropertyListFragmentViewModel>()
        array.add(PropertyOnPropertyListFragmentViewModel(city = "city1", name = "name1", price = "50000", mainPictureUrl = "url"))
        array.add(PropertyOnPropertyListFragmentViewModel(city = "city2", name = "name2", price = "50000", mainPictureUrl = "url"))
        array.add(PropertyOnPropertyListFragmentViewModel(city = "city3", name = "name3", price = "50000", mainPictureUrl = "url"))
        array.add(PropertyOnPropertyListFragmentViewModel(city = "city4", name = "name4", price = "50000", mainPictureUrl = "url"))
        properties.value = array
    }




}