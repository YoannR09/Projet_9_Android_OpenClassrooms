package com.openclassrooms.realestatemanager.presentation.fragments.propertyList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity

class PropertyAdapter(data: List<PropertyEntity>?, lifecycleOwner: LifecycleOwner?, private var owner: ViewModelStoreOwner?) : RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>() {

    private var mData: List<PropertyEntity>? = data
    private var lifecycleRegistry: LifecycleOwner? = lifecycleOwner
    private var context: Context? = null

    fun updateList(viewModelList: List<PropertyEntity>?) {
        mData = listOf()
        mData = viewModelList!!
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.property_item_view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        context = parent.context
        return PropertyViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        holder.bind(mData!![position])
    }

    // total number of rows
    override fun getItemCount(): Int {
        return mData!!.size
    }


    // stores and recycles views as they are scrolled off screen
    class PropertyViewHolder internal constructor(
            itemView: View)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener, LifecycleOwner {

        fun bind(propertyListFragmentViewModel: PropertyEntity) {

        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

        }

        override fun getLifecycle(): Lifecycle {
            TODO("Not yet implemented")
        }

    }
}