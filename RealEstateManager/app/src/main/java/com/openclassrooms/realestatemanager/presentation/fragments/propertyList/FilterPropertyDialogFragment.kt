package com.openclassrooms.realestatemanager.presentation.fragments.propertyList

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.slider.RangeSlider
import com.google.android.material.textfield.TextInputLayout
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.presentation.create.CreatePropertyActivity
import com.openclassrooms.realestatemanager.presentation.create.uiModels.PropertyInterestPointUiModel
import com.openclassrooms.realestatemanager.presentation.create.uiModels.PropertyTypeUiModel
import com.openclassrooms.realestatemanager.presentation.fragments.property.PropertyOnPropertyFragmentViewModel
import com.openclassrooms.realestatemanager.presentation.home.HomeActivity
import com.openclassrooms.realestatemanager.presentation.home.HomeActivitySharedViewModel
import kotlinx.coroutines.flow.MutableStateFlow

enum class PropertyType (
    val title: String
){
    APARTMENT("APARTMENT"),
    LOFT("LOFT"),
    MANOR("MANOR"),
    HOUSE("HOUSE"),
    STUDIO("STUDIO"),
    ALL("ALL")
}

class FilterPropertyDialogFragment : DialogFragment() {

    private val selectDateCreated: Button get() = requireView().findViewById(R.id.select_create_date)
    private val selectDateSolded: Button get() = requireView().findViewById(R.id.select_sold_date)
    private val applyButton: Button get() = requireView().findViewById(R.id.apply_filter)
    private val rangeSize: RangeSlider get() = requireView().findViewById(R.id.slider_size)
    private val rangePrice: RangeSlider get() = requireView().findViewById(R.id.slider_price)
    private val selectType: TextInputLayout get() = requireView().findViewById(R.id.menu_type)
    private val selectPicturesLength: TextInputLayout get() = requireView().findViewById(R.id.menu_pictures_length)
    private val inputSelectType: AutoCompleteTextView get() = requireView().findViewById(R.id.input_type_filter)
    private val inputSelectPicturesLength: AutoCompleteTextView get() = requireView().findViewById(R.id.input_pictures_length_filter)
    private val chipsInterest: ChipGroup get() = requireView().findViewById(R.id.chips_list_interest_filter)

    private val chipsSelected: ArrayList<String> = ArrayList()

    private val homeActivitySharedViewModel by lazy {
        ViewModelProvider(requireActivity())[HomeActivitySharedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_filter_property_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputSelectType.addTextChangedListener {
            homeActivitySharedViewModel.typeSelect.value = it.toString()
        }

        inputSelectPicturesLength.addTextChangedListener {
            homeActivitySharedViewModel.picturesLength.value = it.toString()
        }

        val counter = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10+")
        val adapterCounter = ArrayAdapter(requireContext(), R.layout.type_item, counter)
        (selectPicturesLength.editText as? AutoCompleteTextView)?.setAdapter(adapterCounter)

        inputSelectPicturesLength.setText(homeActivitySharedViewModel.picturesLength.value, false)

        val items =  PropertyType.values().map { it.title }
        val adapter = ArrayAdapter(requireContext(), R.layout.type_item, items)
        (selectType.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        selectDateSolded.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Property sold since the date selected")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
            fragmentManager?.let { it1 -> datePicker.show(it1, "TAG") }
            datePicker.addOnPositiveButtonClickListener {
                homeActivitySharedViewModel.soldDate.value = it.toString()
            }
        }

        selectDateCreated.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Property create since the date selected")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
            fragmentManager?.let { it1 -> datePicker.show(it1, "TAG") }
            datePicker.addOnPositiveButtonClickListener {
                homeActivitySharedViewModel.createDate.value = it.toString()
            }
        }

        setInterestChips(PropertyInterestPointUiModel.values().map { it.title })

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

    private fun setInterestChips(interestList: List<String?>) {
        for (category in interestList) {
            val mChip =
                this.layoutInflater.inflate(R.layout.item_chip_interest, null, false) as Chip
            mChip.text = category
            val paddingDp = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 10f,
                resources.displayMetrics
            ).toInt()
            mChip.setPadding(paddingDp, 0, paddingDp, 0 )
            mChip.setOnCheckedChangeListener { compoundButton, b ->
                if(b) {
                    chipsSelected.add(compoundButton.text.toString())
                } else {
                    chipsSelected.remove(compoundButton.text.toString())
                }
                homeActivitySharedViewModel.interestList.value = chipsSelected
            }
            chipsInterest.addView(mChip)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FilterPropertyDialogFragment()
    }
}