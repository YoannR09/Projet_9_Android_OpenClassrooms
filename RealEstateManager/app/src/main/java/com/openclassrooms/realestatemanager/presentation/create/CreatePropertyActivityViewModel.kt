package com.openclassrooms.realestatemanager.presentation.create

import android.view.View
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.openclassrooms.realestatemanager.data.dao.entities.PictureEntity
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import com.openclassrooms.realestatemanager.domain.usecases.property.CreatePropertyUseCase
import com.openclassrooms.realestatemanager.presentation.create.uiModels.PropertyInterestPointUiModel
import com.openclassrooms.realestatemanager.presentation.create.uiModels.PropertyTypeUiModel
import com.openclassrooms.realestatemanager.utils.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnFailureListener

import android.util.DisplayMetrics

import android.graphics.BitmapFactory

import android.graphics.Bitmap

import com.google.firebase.storage.FirebaseStorage

import com.google.firebase.storage.StorageReference




sealed class ScreenStateCreateProperty

data class ScreenStateError(val error: String) : ScreenStateCreateProperty()
object ScreenStateNothing: ScreenStateCreateProperty()
object ScreenStateLoading: ScreenStateCreateProperty()
data class ScreenStateSuccess(val success: String): ScreenStateCreateProperty()
object ScreenStateNoData: ScreenStateCreateProperty()


class CreatePropertyActivityViewModel: ViewModel() {

    val types get() = PropertyTypeUiModel.values()

    val interest get() = PropertyInterestPointUiModel.values()

    val screenState = MutableStateFlow<ScreenStateCreateProperty>(ScreenStateNothing)

    val currentStep = MutableStateFlow(0)

    val nextButtonIsEnabled by lazy {
        currentStep.flatMapState(scope) {
            when(it) {
                0 -> generalInfoCheckForm
                1 -> purchaseInfoCheckForm
                2 -> MutableStateFlow(true)
                else -> MutableStateFlow(false)
            }
        }
    }

    val stepperTitle = currentStep.mapState(scope) {
        when(it) {
            0 -> "Enter your general information"
            1 -> "Enter your purchase information"
            2 -> "Select your picture"
            3 -> "Confirm your property"
            else -> ""
        }
    }

    val stepperPercent = currentStep.mapState(scope) {
        when(it) {
            0 -> 0
            1 -> 33
            2 -> 66
            else -> 100
        }
    }

    val previousStep = currentStep.mapState(scope) {
        if(it == 0) {
           View.GONE
        } else {
            View.VISIBLE
        }
    }

    val confirmButton = currentStep.mapState(scope) {
        if(it == 3) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    val nextStepIsVisible = currentStep.mapState(scope) {
       it != 3
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

    val pictureList = MutableStateFlow(listOf<PictureEntity>())

    val generalInfoCheckForm = combineStateFlows(scope, address, description, type) { address, description, type ->
        when {
            address.isEmpty() -> false
            description.isEmpty() -> false
            type.isEmpty() -> false
            else -> true
        }
    }

    val purchaseInfoCheckForm = combineStateFlows(scope, size, pieces, price) {size, pieces, price ->
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
                type = type.value,
                address = address.value,
                description = description.value,
                price = price.value,
                meter = size.value,
                pieces = pieces.value,
                state = PropertyState.AVAILABLE.name,
                createDate = Date().time.toString(),
                interestPoints = interestPoint.value,
                picturesList = pictureList.value,
                soldDate = "",
                agentId = FirebaseAuth.getInstance().currentUser!!.email!!
            )
            scope.launch {
                CreatePropertyUseCase().createProperty(newProperty)
                startActivity()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getPictureFromFirestore() {

    }
}