package com.openclassrooms.realestatemanager.presentation.create

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.openclassrooms.realestatemanager.R

import android.util.TypedValue

import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.openclassrooms.realestatemanager.presentation.create.uiModels.PropertyLocationTypeUiModel
import com.openclassrooms.realestatemanager.presentation.create.uiModels.toUIModel
import com.openclassrooms.realestatemanager.utils.InterestPoint
import com.openclassrooms.realestatemanager.utils.observe

class GeneralInfoStepFragment : Fragment() {

    private val inputAddress: TextInputEditText get() = requireView().findViewById(R.id.input_address)
    private val inputDescription: TextInputEditText get() = requireView().findViewById(R.id.input_description)
    private val selectType: TextInputLayout get() = requireView().findViewById(R.id.menu)
    private val inputSelectType: AutoCompleteTextView get() = requireView().findViewById(R.id.input_type)
    private val chipsInterest: ChipGroup get() = requireView().findViewById(R.id.chips_list_interest)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_general_info_step, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = (activity as CreatePropertyActivity).viewModel

        viewModel.address.observe(this) {
            inputAddress.setText(it)
        }
        inputAddress.addTextChangedListener {
            inputAddress.setSelection(inputAddress.length()) // fix cursos bug
            (activity as CreatePropertyActivity).viewModel.address.value = it.toString()
        }

        viewModel.description.observe(this) {
            inputDescription.setText(it)
        }
        inputDescription.addTextChangedListener {
            inputDescription.setSelection(inputDescription.length()) // fix cursos bug
            (activity as CreatePropertyActivity).viewModel.description.value = it.toString()
        }

        viewModel.type.observe(this) {
            inputSelectType.setText(it, false)
        }

        inputSelectType.addTextChangedListener {
            (activity as CreatePropertyActivity).viewModel.type.value = it.toString()
        }

        val items = (activity as CreatePropertyActivity).viewModel.types.map { it.title }
        val adapter = ArrayAdapter(requireContext(), R.layout.type_item, items)
        (selectType.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        (activity as CreatePropertyActivity).viewModel.interestPoint.observe(this){
            setInterestChips((activity as CreatePropertyActivity).viewModel.interest, it)
        }
    }

    private fun setInterestChips(interestList: Array<PropertyLocationTypeUiModel>, selectedInterests: List<InterestPoint>) {
        val selectedTitles = selectedInterests.map { it.toUIModel().title }
        chipsInterest.removeAllViews()
        for (category in interestList) {
            val mChip = layoutInflater.inflate(R.layout.item_chip_interest, null, false) as Chip
            mChip.text = category.title
            val paddingDp = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 10f,
                resources.displayMetrics
            ).toInt()
            mChip.isSelected = selectedTitles.contains(mChip.text)
            mChip.setPadding(paddingDp, 0, paddingDp, 0 )
            mChip.setOnCheckedChangeListener { compoundButton, b ->
                (activity as CreatePropertyActivity).viewModel.toggleInterestWithName(compoundButton.text.toString())
            }
            chipsInterest.addView(mChip)
        }
    }
}