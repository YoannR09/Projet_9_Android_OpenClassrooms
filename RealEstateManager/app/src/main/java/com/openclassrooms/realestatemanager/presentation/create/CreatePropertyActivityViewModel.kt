package com.openclassrooms.realestatemanager.presentation.create

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.RealStateManagerApplication
import com.openclassrooms.realestatemanager.data.dao.entities.PictureEntity
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import com.openclassrooms.realestatemanager.domain.models.PictureModel
import com.openclassrooms.realestatemanager.domain.models.PropertyModel
import com.openclassrooms.realestatemanager.domain.usecases.property.CreatePropertyUseCase
import com.openclassrooms.realestatemanager.domain.usecases.property.UpdateStatePropertyUseCase
import com.openclassrooms.realestatemanager.presentation.create.uiModels.PropertyInterestPointUiModel
import com.openclassrooms.realestatemanager.presentation.create.uiModels.PropertyTypeUiModel
import com.openclassrooms.realestatemanager.presentation.mappers.asEntity
import com.openclassrooms.realestatemanager.utils.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*


sealed class ScreenStateCreateProperty

data class ScreenStateError(val error: String) : ScreenStateCreateProperty()
object ScreenStateNothing: ScreenStateCreateProperty()
object ScreenStateLoading: ScreenStateCreateProperty()
data class ScreenStateSuccess(val success: String): ScreenStateCreateProperty()
object ScreenStateNoData: ScreenStateCreateProperty()

class CreatePropertyActivityViewModelFactory(
    private val property: PropertyModel?
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = CreatePropertyActivityViewModel(property) as T
}

class CreatePropertyActivityViewModel(
    val property: PropertyModel?
): ViewModel() {

    lateinit var id: String;

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
    val type = MutableStateFlow(property?.type ?: "")
    val description = MutableStateFlow(property?.description ?: "")
    val address = MutableStateFlow(property?.address ?: "")
    val interestPoint = MutableStateFlow(property?.interestPoints ?: listOf())
    //endregion

    //region Purchase
    val size = MutableStateFlow(property?.meter ?: 0)
    val pieces = MutableStateFlow(property?.pieces ?: 0)
    val price = MutableStateFlow(property?.price ?: 0)
    //endregion

    val pictureList = MutableStateFlow(property?.picturesList ?: listOf<PictureModel>())

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
                id = Date().time.toString() + "-" + FirebaseAuth.getInstance().currentUser!!.email!!,
                type = type.value,
                address = address.value,
                description = description.value,
                price = price.value,
                meter = size.value,
                pieces = pieces.value,
                state = PropertyState.AVAILABLE.name,
                createDate = Date().time.toString(),
                interestPoints = arrayListOf(*interestPoint.value.toTypedArray()),
                picturesList = pictureList.value.map { it.asEntity() },
                soldDate = "",
                agentId = FirebaseAuth.getInstance().currentUser!!.email!!
            )
            scope.launch {
                CreatePropertyUseCase().createProperty(newProperty)
                createNotificationChannel()
                val builder: NotificationCompat.Builder =
                    NotificationCompat.Builder(RealStateManagerApplication.context, "channel_id")
                        .setContentTitle("Property created !")
                        .setSmallIcon(R.drawable.ic_baseline_map_24)
                        .setContentText(" Your property as succesfully created.")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                val notificationManager = NotificationManagerCompat.from(
                    RealStateManagerApplication.context
                )
                notificationManager.notify(44, builder.build())
                startActivity()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "channel"
            val description = "channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("channel_id", name, importance)
            channel.description = description
            val notificationManager = RealStateManagerApplication.context.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun updateState(state: String, date: String?) {
        scope.launch {
            val dateToPass = date?: Date().time.toString()
            /*
                if(state === PropertyState.SELL.name) {
                    date = Date().time.toString()
                }
             */
            UpdateStatePropertyUseCase().updateStateProperty(state, dateToPass, property!!.id)
        }
    }
}