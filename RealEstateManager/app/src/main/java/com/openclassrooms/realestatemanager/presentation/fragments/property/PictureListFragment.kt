package com.openclassrooms.realestatemanager.presentation.fragments.property

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R

/**
 * A simple [Fragment] subclass.
 * Use the [PictureListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PictureListFragment : Fragment() {
    private var adapter: PictureAdapter? = null
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return try {
            val view: View = inflater.inflate(R.layout.fragment_picture_list, container, false)
            recyclerView = view.findViewById(R.id.rvPictureList)
            recyclerView.layoutManager = LinearLayoutManager(activity,RecyclerView.HORIZONTAL, false)
            adapter = PictureAdapter(ArrayList())
            recyclerView.adapter = adapter

            view
        } catch (e: Exception) {
            e.printStackTrace()
            inflater.inflate(R.layout.fragment_picture_list, container, false)
        }
    }

}