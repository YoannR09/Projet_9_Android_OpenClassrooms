package com.openclassrooms.realestatemanager.presentation.fragments.property

import android.graphics.Color
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
import org.w3c.dom.Text

class PropertyFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[PropertyFragmentViewModel::class.java]
    }

    private val description: TextView get() = requireView().findViewById(R.id.property_description)
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_property, container, false)

        implScreenState()

        viewModel
            .property
            .observe(viewLifecycleOwner) { propertySelected ->
                val dollarId = toggleButton[0].id
                val euroId = toggleButton[1].id
                // TODO
                /*
                Geocoder geocoder = new Geocoder(<your context>);
                List<Address> addresses;
                addresses = geocoder.getFromLocationName(<String address>, 1);
                if(addresses.size() > 0) {
                    double latitude= addresses.get(0).getLatitude();
                    double longitude= addresses.get(0).getLongitude();
                }
                */

                val latEiffelTower = "48.858235";
                val lngEiffelTower = "2.294571";
                val url = "https://maps.google.com/maps/api/staticmap?center="+ latEiffelTower + "," + lngEiffelTower+ "&zoom=15&size=400x400&sensor=false&key=AIzaSyD6Gm3ulw2mdlx06It708hHVvJgbdFsBm4"
                val circularProgressDrawable
                        = CircularProgressDrawable(RealStateManagerApplication.contextApp)
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.centerRadius = 30f
                circularProgressDrawable.start()
                try {
                    Glide.with(RealStateManagerApplication.contextApp)
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
                description.text = propertySelected.description
                creator.text = propertySelected.creator
                createdDate.text = propertySelected.createdDate
                state.text = propertySelected.state
                price.text = propertySelected.price + " $"
                if(propertySelected.state == PropertyState.AVAILABLE.name) {
                    stateInfo.setColorFilter(Color.GREEN)
                } else {
                    stateInfo.setColorFilter(Color.RED)
                }
            }

        (activity as HomeActivity).viewModel
            .idSelected
            .observe(viewLifecycleOwner) { id ->
                if(id != "id") {
                    viewModel.loadPropertyById(id)
                } else {
                    viewModel.screenState.value = ScreenStateNoData
                }
            }
        return view
    }

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