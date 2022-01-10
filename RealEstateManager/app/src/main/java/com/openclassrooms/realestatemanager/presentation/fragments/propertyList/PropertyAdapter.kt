package com.openclassrooms.realestatemanager.presentation.fragments.propertyList

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.RealeStateManagerApplication
import com.openclassrooms.realestatemanager.presentation.home.HomeActivityViewModel
import java.lang.Exception


class PropertyAdapter(data: List<PropertyOnPropertyListFragmentViewModel>?, var viewModel: HomeActivityViewModel, var lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>() {

    private var mData: List<PropertyOnPropertyListFragmentViewModel>? = data
    private var context: Context? = null

    fun updateList(viewModelList: List<PropertyOnPropertyListFragmentViewModel>?) {
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
        return PropertyViewHolder(view, viewModel = viewModel, lifecycleOwner = lifecycleOwner)
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
        itemView: View, var viewModel: HomeActivityViewModel,
        val lifecycleOwner: LifecycleOwner
    )
        : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var title: TextView
        var city: TextView
        var imageView: ImageView
        var background: LinearLayout

        fun bind(property: PropertyOnPropertyListFragmentViewModel) {
            viewModel.indexSelected.observe(lifecycleOwner) {
                index: Number ->
                run {
                    if(index == adapterPosition) {
                        this.background.setBackgroundColor(Color.parseColor("#3F51B5"))
                    } else {
                        this.background.setBackgroundColor(Color.WHITE)
                    }
                }
            }

            this.title.text = property.name
            this.city.text = property.city
            val circularProgressDrawable
                = CircularProgressDrawable(RealeStateManagerApplication.getContextApp())
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            val url = property.mainPictureUrl
            try {
                Glide.with(RealeStateManagerApplication.getContextApp())
                    .load(url)
                    .placeholder(circularProgressDrawable).into(this.imageView)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        init {
            itemView.setOnClickListener(this)
            this.background = itemView.findViewById(R.id.item_background)
            this.title = itemView.findViewById(R.id.property_list_title)
            this.city = itemView.findViewById(R.id.property_list_city)
            this.imageView = itemView.findViewById(R.id.image_main_property)
        }

        override fun onClick(v: View?) {
            viewModel.changeSelectIndex(adapterPosition)
        }
    }
}