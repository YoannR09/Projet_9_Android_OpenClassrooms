package com.openclassrooms.realestatemanager.presentation.fragments.property

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.presentation.home.HomeActivity

/**
 * A simple [Fragment] subclass.
 * Use the [PropertyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PropertyFragment : Fragment() {

    private lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_property, container, false)
        textView = view.findViewById(R.id.index_test)
        (activity as HomeActivity).viewModel.indexSelected.observe(viewLifecycleOwner) {
            index ->
            run {
                this.textView.text = index.toString()
            }
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PropertyFragment()
    }
}