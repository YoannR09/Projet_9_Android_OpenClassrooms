package com.openclassrooms.realestatemanager.presentation.home

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.domain.models.PropertyModel
import com.openclassrooms.realestatemanager.utils.InterestPoint
import kotlinx.coroutines.flow.MutableStateFlow

class HomeActivitySharedViewModel: ViewModel() {

    val properties = MutableStateFlow<List<PropertyModel>>(listOf())
    val noFilteredProperties = MutableStateFlow<List<PropertyModel>>(listOf())
    val filteredProperties = MutableStateFlow<List<PropertyModel>>(listOf())

    val sliderPrice = MutableStateFlow(listOf(0, 10000000))
    val sliderSize = MutableStateFlow(listOf(0, 1000))

    private fun filterBySize(min: Int, max: Int): List<PropertyModel> {
        return filteredProperties.value.filter { (it.meter in min..max) }
    }

    fun filterByType(type: List<String>) {
        filteredProperties.value.filter { type.contains(it.type)}
    }

    fun filterByInterestPoint(type: List<InterestPoint>) {
        //properties.value.filter { type.contains(it)}
    }

    private fun filterByPrice(min: Int, max: Int): List<PropertyModel> {
        return filteredProperties.value.filter { (it.price in min..max) }
    }

    fun applyFilter() {
        filteredProperties.value = noFilteredProperties.value
        filteredProperties.value = filterByPrice(sliderPrice.value[0], sliderPrice.value[1])
        filteredProperties.value = filterBySize(sliderSize.value[0], sliderSize.value[1])
        properties.value = filteredProperties.value
    }

}
