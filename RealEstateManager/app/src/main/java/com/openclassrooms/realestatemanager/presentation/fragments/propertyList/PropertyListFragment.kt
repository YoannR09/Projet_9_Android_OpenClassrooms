package com.openclassrooms.realestatemanager.presentation.fragments.propertyList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.presentation.home.HomeActivity
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [PropertyListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PropertyListFragment : Fragment() {

    private var adapter: PropertyAdapter? = null
    var recyclerView: RecyclerView? = null
    private val viewModel by lazy {
        ViewModelProvider(this)[PropertyListFragmentViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return try {
            val view: View = inflater.inflate(R.layout.fragment_property_list, container, false)
            recyclerView = view.findViewById(R.id.rvPropertyList)
            recyclerView?.layoutManager = LinearLayoutManager(activity)
            adapter = (activity as HomeActivity?)?.let { PropertyAdapter(ArrayList(), it.viewModel, this) }
            recyclerView?.adapter = adapter
            viewModel.properties.observe(this.viewLifecycleOwner) {
                result -> adapter?.updateList(result)
            }
            viewModel.loadProperties()
            view
        } catch (e: Exception) {
            e.printStackTrace()
            inflater.inflate(R.layout.fragment_property_list, container, false)
        }
    }

    companion object {
        /**
         * @return A new instance of fragment PropertyListFragment.
         */
        @JvmStatic
        fun newInstance() =
                PropertyListFragment()
    }
}