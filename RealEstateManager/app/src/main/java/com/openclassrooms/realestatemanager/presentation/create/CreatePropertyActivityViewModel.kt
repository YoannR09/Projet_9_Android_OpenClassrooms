package com.openclassrooms.realestatemanager.presentation.create

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.RealStateManagerApplication
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import com.openclassrooms.realestatemanager.domain.models.PropertyModel
import com.openclassrooms.realestatemanager.domain.usecases.property.CreatePropertyUseCase
import com.openclassrooms.realestatemanager.domain.usecases.property.UpdatePropertyUseCase
import com.openclassrooms.realestatemanager.presentation.create.uiModels.PropertyLocationTypeUiModel
import com.openclassrooms.realestatemanager.presentation.create.uiModels.PropertyTypeUiModel
import com.openclassrooms.realestatemanager.presentation.create.uiModels.toModel
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

    val id = MutableStateFlow(property?.id)

    val types get() = PropertyTypeUiModel.values()

    val interest get() = PropertyLocationTypeUiModel.values()

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

    //region State
    val soldDate = MutableStateFlow(property?.soldDate ?: "0")
    val state = MutableStateFlow(property?.state?.let(PropertyState::valueOf) ?: PropertyState.AVAILABLE)

    val pictureList = MutableStateFlow(property?.picturesList ?: listOf())

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

    fun createProperty(context: Context, startActivity: () -> Unit) {
        try {
            val geoCoder = Geocoder(context)
            val addressLatLng: List<Address> = try {
                geoCoder.getFromLocationName(address.value, 1)
            }catch (e: Exception) {
                listOf()
            }
            var latitude = 48.858235
            var longitude = 2.294571
            if(addressLatLng.isNotEmpty()) {
                latitude = addressLatLng[0].latitude;
                longitude = addressLatLng[0].longitude;
            }
            val newProperty = PropertyEntity(
                id = id.value
                    ?: (Date().time.toString() + "-" + FirebaseAuth.getInstance().currentUser!!.email!!),
                type = type.value,
                address = address.value,
                description = description.value,
                price = price.value,
                meter = size.value,
                pieces = pieces.value,
                state = state.value.name,
                createDate = Date().time.toString(),
                interestPoints = arrayListOf(*interestPoint.value.map { it.name }.toTypedArray()),
                picturesList = pictureList.value.map { it.asEntity() },
                soldDate = soldDate.value,
                agentId = FirebaseAuth.getInstance().currentUser!!.email!!,
                lat = latitude,
                lng = longitude
            )
            scope.launch {
                if(property != null) {
                    UpdatePropertyUseCase().updateProperty(newProperty)
                } else {
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
                }
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

    fun toggleInterestWithName(name: String) {
        val interest = PropertyLocationTypeUiModel.values().first { it.title == name }
        val list = interestPoint.value.toMutableList()
        val model = interest.toModel()

        if(!interestPoint.value.contains(model)) {
            list.add(model)
        } else {
            list.remove(model)
        }
        interestPoint.value = list
    }
}