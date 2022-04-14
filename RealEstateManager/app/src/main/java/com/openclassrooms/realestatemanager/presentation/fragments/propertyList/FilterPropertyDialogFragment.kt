package com.openclassrooms.realestatemanager.presentation.fragments.propertyList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.google.android.material.slider.RangeSlider
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.presentation.fragments.property.PropertyOnPropertyFragmentViewModel
import com.openclassrooms.realestatemanager.presentation.home.HomeActivity
import com.openclassrooms.realestatemanager.presentation.home.HomeActivitySharedViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class FilterPropertyDialogFragment(
    private val homeActivitySharedViewModel: HomeActivitySharedViewModel
) : DialogFragment() {

    val applyButton = requireView().findViewById<Button>(R.id.apply_filter)
    val rangeSize = requireView().findViewById<RangeSlider>(R.id.slider_size)
    val rangePrice = requireView().findViewById<RangeSlider>(R.id.slider_price)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_filter_property_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyButton.setOnClickListener {
            applyFilterOnPropertyList()
        }

        rangePrice.values = listOf(
            homeActivitySharedViewModel.sliderPrice.value[0].toFloat(),
            homeActivitySharedViewModel.sliderPrice.value[1].toFloat())
        rangePrice.addOnChangeListener { _, _, _ ->
            homeActivitySharedViewModel.sliderPrice.value = rangePrice.values.map { it.toInt() }
        }

        rangeSize.values = listOf(
            homeActivitySharedViewModel.sliderSize.value[0].toFloat(),
            homeActivitySharedViewModel.sliderSize.value[1].toFloat())
        rangeSize.addOnChangeListener { _, _, _ ->
            homeActivitySharedViewModel.sliderSize.value = rangeSize.values.map { it.toInt() }
        }
    }

    private fun applyFilterOnPropertyList() {
        homeActivitySharedViewModel.applyFilter()
        this.dismiss()
    }

    private fun resetValues() {
        rangePrice.values = listOf(0f, 1000000f)
        rangeSize.values = listOf(0f, 1000f)
    }

    companion object {
        @JvmStatic
        fun newInstance(homeActivitySharedViewModel: HomeActivitySharedViewModel) =
            FilterPropertyDialogFragment(homeActivitySharedViewModel)
    }
}