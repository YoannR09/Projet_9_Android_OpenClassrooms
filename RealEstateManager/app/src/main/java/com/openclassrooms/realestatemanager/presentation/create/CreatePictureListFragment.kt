package com.openclassrooms.realestatemanager.presentation.create

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
import com.openclassrooms.realestatemanager.presentation.fragments.property.PictureAdapter
import com.openclassrooms.realestatemanager.presentation.fragments.propertyList.PropertyListFragmentViewModel
import com.openclassrooms.realestatemanager.utils.observe

class CreatePictureListFragment : Fragment() {
    private var adapter: PictureAdapter? = null
    private lateinit var recyclerView: RecyclerView

    private val emptyList: TextView get() = requireView().findViewById(R.id.empty_picture_list_text)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return try {
            val view: View = inflater.inflate(R.layout.fragment_picture_list, container, false)
            recyclerView = view.findViewById(R.id.rvPictureList)
            recyclerView.layoutManager = LinearLayoutManager(activity,RecyclerView.HORIZONTAL, false)
            adapter = PictureAdapter(arrayListOf())
            (requireActivity() as CreatePropertyActivity).viewModel.pictureList.observe(this) {
                if(it.isEmpty()) {
                    emptyList.visibility = View.VISIBLE
                } else {
                    emptyList.visibility = View.GONE
                }
                adapter!!.updateList(it)
            }
            recyclerView.adapter = adapter
            view
        } catch (e: Exception) {
            e.printStackTrace()
            inflater.inflate(R.layout.fragment_picture_list, container, false)
        }
    }

}