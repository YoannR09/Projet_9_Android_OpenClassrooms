package com.openclassrooms.realestatemanager.presentation.fragments.propertyList

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.presentation.create.CreatePropertyActivity
import com.openclassrooms.realestatemanager.presentation.fragments.property.PropertyFragment
import com.openclassrooms.realestatemanager.presentation.home.HomeActivity
import com.openclassrooms.realestatemanager.utils.observe
import java.util.*

class PropertyListFragment : Fragment() {

    private var adapter: PropertyAdapter? = null
    private lateinit var recyclerView: RecyclerView
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
            recyclerView.layoutManager = LinearLayoutManager(activity)
            adapter = (activity as HomeActivity?)?.let { PropertyAdapter(ArrayList(), listener = object : PropertyAdapterListener {
                override fun onEditButtonClick(index: Int) {
                    val intent = Intent(
                        it,
                        CreatePropertyActivity::class.java)
                    intent.putExtra("created", viewModel.properties.value[index])
                    it.startActivity(intent)
                }

                override fun onItemClick(index: Int) {
                    val lastIndex = viewModel.properties.value.indexOfFirst {
                        it.isSelected
                    }
                    viewModel.selectIndex(index)
                    (activity as HomeActivity).viewModel.changeSelectId(viewModel.properties.value[index].id)
                    if(!(activity as HomeActivity).viewModel.isLargeScreen) {
                        val intent = Intent(
                            it,
                            PropertyFragment::class.java)
                        it.startActivity(intent)
                    }

                    adapter?.notifyItemChanged(index)
                   if(lastIndex >= 0) {
                       adapter?.notifyItemChanged(lastIndex)
                   }

                }
            }) }
            recyclerView.adapter = adapter
            this.emptyList = view.findViewById(R.id.empty_list_text)
            viewModel.properties.observe(this) {
                result ->
                if(result.isEmpty()) {
                    this.emptyList.visibility = View.VISIBLE
                } else {
                    this.emptyList.visibility = View.GONE
                }
                adapter?.updateList(result)
            }
            implScreenState()
            viewModel.loadProperties()
            view
        } catch (e: Exception) {
            e.printStackTrace()
            inflater.inflate(R.layout.fragment_property_list, container, false)
        }
    }

    private fun implScreenState() {
        viewModel
            .screenState
            .observe(viewLifecycleOwner) { state ->
                when(state) {
                    is ScreenStateError -> {
                        val toast = Toast.makeText(activity,
                            "Error, try again", Toast.LENGTH_LONG)
                        toast.show()
                    }
                    ScreenStateLoading -> {}
                    ScreenStateNothing -> {}
                    ScreenStateSuccess -> {

                    }
                    ScreenStateNoData -> {

                    }
                }
            }
    }
}