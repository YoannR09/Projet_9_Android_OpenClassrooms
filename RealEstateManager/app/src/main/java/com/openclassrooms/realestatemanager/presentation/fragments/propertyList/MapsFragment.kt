package com.openclassrooms.realestatemanager.presentation.fragments.propertyList

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.presentation.fragments.property.PropertyDisplayActivity
import com.openclassrooms.realestatemanager.presentation.home.HomeActivity
import com.openclassrooms.realestatemanager.presentation.home.HomeActivitySharedViewModel
import com.openclassrooms.realestatemanager.presentation.mappers.asPropertyOnMapViewModel
import com.openclassrooms.realestatemanager.utils.isLargeScreen
import com.openclassrooms.realestatemanager.utils.observe


class MapsFragment : Fragment() {

    private val homeActivitySharedViewModel by lazy {
        ViewModelProvider(requireActivity())[HomeActivitySharedViewModel::class.java]
    }

    private val viewModel by lazy {
        ViewModelProvider(this,
            MapFragmentViewModelFactory(
                homeActivitySharedViewModel = homeActivitySharedViewModel
            )
        )[MapFragmentViewModel::class.java]
    }

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        viewModel.properties.observe(this) {
          it.forEach {
              p ->
              val mapped = p.asPropertyOnMapViewModel(requireContext())
              googleMap.addMarker(MarkerOptions()
                  .position(LatLng(mapped.lat, mapped.lng))
                  .icon(BitmapDescriptorFactory.fromResource(mapped.marker))
                  .title(mapped.id))
          }
        }
        var latitude = 48.858235
        var longitude = 2.294571
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.858235, 2.294571), 15F))
        googleMap.setOnMarkerClickListener {
            marker ->
            val lastIndex = viewModel.properties.value.indexOfFirst {
                it.id == marker.id
            }
            (activity as HomeActivity).viewModel.changeSelectId(viewModel.properties.value[lastIndex].id)
            if(!requireContext().isLargeScreen) {
                PropertyDisplayActivity.start(
                    context = requireContext(),
                    property = homeActivitySharedViewModel.properties.value[lastIndex]
                )
            }
            true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}