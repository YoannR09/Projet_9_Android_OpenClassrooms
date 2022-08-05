package com.openclassrooms.realestatemanager.presentation.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.utils.observe


class ConfirmStepFragment : Fragment() {

    private val confirmAddress: TextView get() = requireView().findViewById(R.id.confirm_address)
    private val confirmDescription: TextView get() = requireView().findViewById(R.id.confirm_description)
    private val confirmType: TextView get() = requireView().findViewById(R.id.confirm_type)
    private val confirmInterest: TextView get() = requireView().findViewById(R.id.confirm_interest)
    private val confirmPieces: TextView get() = requireView().findViewById(R.id.confirm_pieces)
    private val confirmPrice: TextView get() = requireView().findViewById(R.id.confirm_price)
    private val confirmSize: TextView get() = requireView().findViewById(R.id.confirm_size)
    private val picturesLength: TextView get() = requireView().findViewById(R.id.picturesLength)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_confirm_step, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        completeObsGeneral()
        completeObsPurchase()
        completeObsPictures()
    }

    private fun completeObsPurchase() {
        (activity as CreatePropertyActivity).viewModel.pieces.observe(this) {
            confirmPieces.text = it.toString()
        }
        (activity as CreatePropertyActivity).viewModel.price.observe(this) {
            confirmPrice.text = "$it $"
        }
        (activity as CreatePropertyActivity).viewModel.size.observe(this) {
            confirmSize.text = "$it m²"
        }
        (activity as CreatePropertyActivity).viewModel.interestPoint.observe(this) {
            confirmInterest.text = it.toString()
        }
    }

    private fun completeObsGeneral() {
        (activity as CreatePropertyActivity).viewModel.address.observe(this) {
            confirmAddress.text = it
        }
        (activity as CreatePropertyActivity).viewModel.type.observe(this) {
            confirmType.text = it
        }
        (activity as CreatePropertyActivity).viewModel.description.observe(this) {
            confirmDescription.text = it
        }
    }

    private fun completeObsPictures() {
        (activity as CreatePropertyActivity).viewModel.pictureList.observe(this) {
            picturesLength.text = it.size.toString()
        }
    }

}