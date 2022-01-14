package com.openclassrooms.realestatemanager.presentation.fragments.property

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.presentation.home.HomeActivity

/**
 * A simple [Fragment] subclass.
 * Use the [PropertyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PropertyFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[PropertyFragmentViewModel::class.java]
    }

    private lateinit var textView: TextView
    private lateinit var loadingView: View
    private lateinit var propertyView: View
    private lateinit var noneProperty: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_property, container, false)
        textView = view.findViewById(R.id.index_test)
        propertyView = view.findViewById(R.id.property_card)
        loadingView = view.findViewById(R.id.loading_spinner)
        noneProperty = view.findViewById(R.id.none_property)
        viewModel.property.observe(viewLifecycleOwner) { propertySelected ->
                this.textView.text = propertySelected.name
                hideLoader()
        }
        (activity as HomeActivity).viewModel.idSelected.observe(viewLifecycleOwner) {
            id ->
                if(id !== "none") {
                    hideNoneProperty()
                    showLoader()
                    this.viewModel.loadPropertyById(id)
                } else {
                    showNoneProperty()
                }
        }
        return view
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

    companion object {
        @JvmStatic
        fun newInstance() =
            PropertyFragment()
    }
}