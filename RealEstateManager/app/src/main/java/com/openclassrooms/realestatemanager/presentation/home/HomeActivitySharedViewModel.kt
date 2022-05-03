package com.openclassrooms.realestatemanager.presentation.home

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.domain.models.FilterModel
import com.openclassrooms.realestatemanager.domain.models.PropertyModel
import com.openclassrooms.realestatemanager.utils.combineStateFlows
import com.openclassrooms.realestatemanager.utils.scope
import kotlinx.coroutines.flow.MutableStateFlow

class HomeActivitySharedViewModel: ViewModel() {

    private val allProperties = MutableStateFlow<List<PropertyModel>>(listOf())
    private val filters = MutableStateFlow(FilterModel())
    val properties = combineStateFlows(
        scope = scope,
        allProperties,
        filters
    ) { properties, filters ->
        properties.asSequence()
            .filter {
                filters.type == null ||
                        findByType(it, filters.type)
            }
            .filter {
                filters.price?.size != 2 ||
                        (it.price in filters.price[0]..filters.price[1])
            }.filter {
                filters.size?.size != 2 ||
                        (it.meter in  filters.size[0].. filters.size[1])
            }.filter {
                filters.picturesLength == null ||
                        findByPicturesLength(it, filters.picturesLength)
            }.filter {
                filters.createdDate == null ||
                        it.createDate.toDouble() >= filters.createdDate.toDouble()
            }.filter {
                filters.soldDate == null ||
                        it.soldDate.toDouble() >= filters.soldDate.toDouble()
            }.filter {
                filters.interests == null || filters.interests.isEmpty() ||
                        findInterestPointFromList(it, filters.interests)
            }.toList()
    }

    val sliderPrice = MutableStateFlow(listOf(0, 10000000))
    val sliderSize = MutableStateFlow(listOf(0, 1000))
    val soldDate = MutableStateFlow<String?>(null)
    val createDate = MutableStateFlow<String?>(null)
    val typeSelect = MutableStateFlow("ALL")
    val picturesLength = MutableStateFlow("0")
    val interestList = MutableStateFlow<List<String>?>(null)

    fun applyFilter() {
        filters.value = FilterModel(
            type = typeSelect.value,
            picturesLength = picturesLength.value,
            size = sliderSize.value,
            price = sliderPrice.value,
            soldDate = soldDate.value,
            createdDate = createDate.value,
            interests = interestList.value
        )
    }

    private fun findByPicturesLength(property: PropertyModel, picturesLength: String): Boolean {
        return if(picturesLength == "10+") {
            property.picturesList.size >= 10
        } else {
            property.picturesList.size >= picturesLength.toInt()
        }
    }

    private fun findByType(propertyModel: PropertyModel, type: String?): Boolean {
        return type == "ALL" || propertyModel.type == type
    }

    private fun findInterestPointFromList(property: PropertyModel, interests: List<String>): Boolean {
        var containsAll = true
        interests.forEach{
                interest ->
            if(!property.interestPoints.contains(interest)) {
                containsAll = false
            }
        }
        return containsAll
    }

    fun setProperties(properties: List<PropertyModel>) {
        allProperties.value = properties
    }

}
