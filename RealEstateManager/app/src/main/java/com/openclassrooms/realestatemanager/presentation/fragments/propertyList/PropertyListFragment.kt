package com.openclassrooms.realestatemanager.presentation.fragments.propertyList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.presentation.home.HomeActivity
import java.util.*

class PropertyListFragment : Fragment() {

    private var adapter: PropertyAdapter? = null
    var recyclerView: RecyclerView? = null
    private lateinit var emptyList: TextView
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
            this.emptyList = view.findViewById(R.id.empty_list_text)
            viewModel.properties.observe(this.viewLifecycleOwner) {
                result ->
                if(result.isEmpty()) {
                    this.emptyList.visibility = View.VISIBLE
                } else {
                    this.emptyList.visibility = View.GONE
                }
                adapter?.updateList(result)
            }
            context?.let { viewModel.loadProperties(it) }
            view
        } catch (e: Exception) {
            e.printStackTrace()
            inflater.inflate(R.layout.fragment_property_list, container, false)
        }
    }
}