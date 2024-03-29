package com.openclassrooms.realestatemanager.presentation.fragments.property

import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButtonToggleGroup
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.RealStateManagerApplication
import com.openclassrooms.realestatemanager.presentation.home.HomeActivity
import com.openclassrooms.realestatemanager.utils.PropertyState
import com.openclassrooms.realestatemanager.utils.Utils.convertDollarToEuro
import com.openclassrooms.realestatemanager.utils.observe

class PropertyFragment : Fragment() {

    val viewModel by lazy {
        ViewModelProvider(this)[PropertyFragmentViewModel::class.java]
    }

    private val description: TextView get() = requireView().findViewById(R.id.property_description)
    private val pieceCounter: TextView get() = requireView().findViewById(R.id.property_rooms)
    private val creator: TextView get() = requireView().findViewById(R.id.property_creator)
    private val createdDate: TextView get() = requireView().findViewById(R.id.created_date)
    private val loadingView: View get() = requireView().findViewById(R.id.loading_spinner)
    private val propertyView: View get() = requireView().findViewById(R.id.property_card)
    private val noneProperty: LinearLayout get() = requireView().findViewById(R.id.none_property)
    private val stateInfo: AppCompatImageView get() = requireView().findViewById(R.id.property_state_info)
    private val state: TextView get() = requireView().findViewById(R.id.property_state)
    private val price: TextView get() = requireView().findViewById(R.id.price_property)
    private val toggleButton: MaterialButtonToggleGroup get() = requireView().findViewById(R.id.toggle_button)
    private val map: ImageView get() = requireView().findViewById(R.id.image_map_property)
    private val size: TextView get() = requireView().findViewById(R.id.property_size)
    private val addressText: TextView get() = requireView().findViewById(R.id.property_address)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        implScreenState()

        viewModel
            .property
            .observe(viewLifecycleOwner) { propertySelected ->
                val dollarId = toggleButton[0].id
                val euroId = toggleButton[1].id

                addressText.text = propertySelected.city
                val url =
                    "https://maps.google.com/maps/api/staticmap?center=${propertySelected.lat},${propertySelected.lng}&zoom=15&size=400x400&sensor=false&key=AIzaSyD6Gm3ulw2mdlx06It708hHVvJgbdFsBm4"
                val circularProgressDrawable
                        = CircularProgressDrawable(requireContext())
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.centerRadius = 30f
                circularProgressDrawable.start()
                try {
                    Glide.with(requireContext())
                        .load(url)
                        .placeholder(circularProgressDrawable).into(this.map)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                toggleButton.check(dollarId)
                toggleButton.addOnButtonCheckedListener{ group, checkedId, _ ->
                    if (group.checkedButtonId == -1) {
                        group.check(checkedId)
                    }
                    if(checkedId == euroId) {
                        val priceToEuro = convertDollarToEuro(propertySelected.price.toInt())
                        price.text = "$priceToEuro €"
                    } else {
                        price.text = propertySelected.price + " $"
                    }
                }
                pieceCounter.text = propertySelected.piecesCounter.toString()
                description.text = propertySelected.description
                creator.text = propertySelected.creator
                createdDate.text = propertySelected.createdDate
                size.text = propertySelected.size
                state.text = propertySelected.state
                price.text = propertySelected.price + " $"
                if(propertySelected.state == PropertyState.AVAILABLE.name) {
                    stateInfo.setColorFilter(Color.GREEN)
                } else {
                    stateInfo.setColorFilter(Color.RED)
                }
            }

        try {
            (activity as HomeActivity).viewModel
                .idSelected
                .observe(viewLifecycleOwner) { id ->
                    if(id != null) {
                        viewModel.loadPropertyById(id)
                    } else {
                        viewModel.screenState.value = ScreenStateNoData
                    }
                }
        }catch (err: Exception) {
            viewModel.loadPropertyById((activity as PropertyDisplayActivity).property!!.id)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_property, container, false)

    private fun implScreenState() {
        viewModel
            .screenState
            .observe(viewLifecycleOwner) { state ->
                when(state) {
                    is ScreenStateError -> {
                        val toast = Toast.makeText(activity,
                            "Error, try again", Toast.LENGTH_LONG)
                        toast.show()
                    }
                    ScreenStateLoading -> {
                        hideNoneProperty()
                        showLoader()
                    }
                    ScreenStateNothing -> {}
                    ScreenStateSuccess -> {
                        hideNoneProperty()
                        hideLoader()
                    }
                    ScreenStateNoData -> {
                        showNoneProperty()
                    }
                }
            }
    }

    private fun showLoader() {
        propertyView.visibility = GONE
        loadingView.visibility = VISIBLE
    }

    private fun hideLoader() {
        loadingView.visibility = GONE
        propertyView.visibility = VISIBLE
    }

    private fun showNoneProperty() {
        propertyView.visibility = GONE
        loadingView.visibility = GONE
        noneProperty.visibility = VISIBLE
    }

    private fun hideNoneProperty() {
        noneProperty.visibility = GONE
    }
}