package com.openclassrooms.realestatemanager.presentation.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.openclassrooms.realestatemanager.R

class PurchaseInfoStepFragment : Fragment() {

    private val inputPrice: TextInputEditText get() = requireView().findViewById(R.id.input_price)
    private val inputSize: TextInputEditText get() = requireView().findViewById(R.id.input_size)
    private val inputPieces: TextInputEditText get() = requireView().findViewById(R.id.input_pieces)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_purchase_info_step, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputPieces.addTextChangedListener {
            val input: Int = if(it.toString() == "") {
                0
            } else {
                it.toString().toInt()
            }
            (activity as CreatePropertyActivity).viewModel.pieces.value = input
        }
        inputPrice.addTextChangedListener {
            val input: Int = if(it.toString() == "") {
                0
            } else {
                it.toString().toInt()
            }
            (activity as CreatePropertyActivity).viewModel.price.value = input
        }
        inputSize.addTextChangedListener {
            val input: Int = if(it.toString() == "") {
                0
            } else {
                it.toString().toInt()
            }
            (activity as CreatePropertyActivity).viewModel.size.value = input
        }
    }
}