package com.openclassrooms.realestatemanager.presentation.fragments.property

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
import com.openclassrooms.realestatemanager.data.dao.entities.PictureEntity
import com.openclassrooms.realestatemanager.presentation.create.CreatePropertyActivityViewModel
import com.openclassrooms.realestatemanager.presentation.fragments.propertyList.PropertyListFragmentViewModel
import com.openclassrooms.realestatemanager.utils.observe

class PictureListFragment : Fragment() {
    private var adapter: PictureAdapter? = null
    private val recyclerView: RecyclerView get() = requireView().findViewById(R.id.rvPictureList)

    private val emptyList: TextView get() = requireView().findViewById(R.id.empty_picture_list_text)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_picture_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(activity,RecyclerView.HORIZONTAL, false)
        adapter = PictureAdapter(arrayListOf())
        (parentFragment as PropertyFragment).viewModel.property.observe(viewLifecycleOwner) {
            if(it.pictureList.isNotEmpty()) {
                emptyList.visibility = View.GONE
            } else {
                emptyList.visibility = View.VISIBLE
            }
            adapter!!.updateList(it.pictureList)
        }
        recyclerView.adapter = adapter
    }

}