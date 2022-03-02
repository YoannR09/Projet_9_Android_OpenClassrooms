package com.openclassrooms.realestatemanager.presentation.create

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.openclassrooms.realestatemanager.data.dao.entities.PictureEntity
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import com.openclassrooms.realestatemanager.domain.usecases.property.CreatePropertyUseCase
import com.openclassrooms.realestatemanager.presentation.create.uiModels.PropertyInterestPointUiModel
import com.openclassrooms.realestatemanager.presentation.create.uiModels.PropertyTypeUiModel
import com.openclassrooms.realestatemanager.utils.PropertyState
import com.openclassrooms.realestatemanager.utils.combineStateFlows
import com.openclassrooms.realestatemanager.utils.flatMapState
import com.openclassrooms.realestatemanager.utils.mapState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class CreatePropertyActivityViewModel: ViewModel() {

    val types get() = PropertyTypeUiModel.values()

    val interest get() = PropertyInterestPointUiModel.values()

    val currentStep = MutableStateFlow(0)

    val nextButtonIsEnabled by lazy {
        currentStep.flatMapState(viewModelScope) {
            when(it) {
                0 -> generalInfoCheckForm
                1 -> purchaseInfoCheckForm
                2 -> MutableStateFlow(true)
                else -> MutableStateFlow(false)
            }
        }
    }

    val stepperTitle = currentStep.mapState(viewModelScope) {
        when(it) {
            0 -> "Enter your general information"
            1 -> "Enter your purchase information"
            2 -> "Select your picture"
            3 -> "Confirm your property"
            else -> ""
        }
    }

    val stepperPercent = currentStep.mapState(viewModelScope) {
        when(it) {
            0 -> 0
            1 -> 33
            2 -> 66
            else -> 100
        }
    }

    val previousStep = currentStep.mapState(viewModelScope) {
        if(it == 0) {
           View.GONE
        } else {
            View.VISIBLE
        }
    }

    val confirmButton = currentStep.mapState(viewModelScope) {
        if(it == 3) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    val nextStep = currentStep.mapState(viewModelScope) {
        if(it == 3) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    //region Information
    val type = MutableStateFlow("")
    val description = MutableStateFlow("")
    val address = MutableStateFlow("")
    val interestPoint = MutableStateFlow(ArrayList<String>())
    //endregion

    //region Purchase
    val size = MutableStateFlow(0)
    val pieces = MutableStateFlow(0)
    val price = MutableStateFlow(0)
    //endregion

    val pictureList = MutableStateFlow(listOf<List<PictureEntity>>())

    val generalInfoCheckForm = combineStateFlows(viewModelScope, address, description, type) { address, description, type ->
        when {
            address.isEmpty() -> false
            description.isEmpty() -> false
            type.isEmpty() -> false
            else -> true
        }
    }

    val purchaseInfoCheckForm = combineStateFlows(viewModelScope, size, pieces, price) {size, pieces, price ->
        when {
            size == 0 -> false
            pieces == 0 -> false
            price == 0 -> false
            else -> true
        }
    }

    fun createProperty(startActivity: () -> Unit) {
        try {
            val newProperty = PropertyEntity(
                name = "test",
                type = type.value,
                address = address.value,
                description = description.value,
                price = price.value,
                meter = size.value,
                pieces = pieces.value,
                state = PropertyState.AVAILABLE.name,
                createDate = Date().time.toString(),
                interestPoints = interestPoint.value,
                picturesList = ArrayList(),
                soldDate = "",
                agentId = FirebaseAuth.getInstance().currentUser!!.email!!
            )
            viewModelScope.launch {
                CreatePropertyUseCase().createProperty(newProperty)
                startActivity()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}