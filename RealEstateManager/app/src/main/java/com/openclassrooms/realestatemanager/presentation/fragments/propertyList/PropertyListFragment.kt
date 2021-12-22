package com.openclassrooms.realestatemanager.presentation.fragments.propertyList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [PropertyListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PropertyListFragment : Fragment() {

    var adapter: PropertyAdapter? = null
    var recyclerView: RecyclerView? = null
    private lateinit var viewModel: PropertyListFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return try {
            val view: View = inflater.inflate(R.layout.fragment_property_list, container, false)
            // viewModel = ViewModelProvider(this).get(Frag::class.java)
            recyclerView = view.findViewById(R.id.rvPropertyList)
            recyclerView?.layoutManager = LinearLayoutManager(activity)
            adapter = PropertyAdapter(ArrayList(), this, this)
            recyclerView?.adapter = adapter
            //viewModel.getCurrentRestaurants().observe(activity) { restaurants -> adapter.updateList(restaurants) }
            view
        } catch (e: Exception) {
            inflater.inflate(R.layout.fragment_property_list, container, false)
        }
    }

    companion object {
        /**
         * @return A new instance of fragment PropertyListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                PropertyListFragment()
    }
}