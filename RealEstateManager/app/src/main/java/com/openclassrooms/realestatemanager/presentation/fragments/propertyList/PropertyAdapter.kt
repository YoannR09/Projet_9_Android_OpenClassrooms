package com.openclassrooms.realestatemanager.presentation.fragments.propertyList

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.RealStateManagerApplication
import com.openclassrooms.realestatemanager.presentation.home.HomeActivityViewModel
import com.openclassrooms.realestatemanager.utils.observe
import androidx.core.content.ContextCompat.startActivity

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.openclassrooms.realestatemanager.presentation.create.CreatePropertyActivity


class PropertyAdapter(
    data: List<PropertyOnPropertyListFragmentViewModel>?,
    private var viewModel: HomeActivityViewModel,
    private var lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>() {

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

        private var title: TextView
        private var city: TextView
        private var price: TextView
        private var imageView: ImageView
        private var background: LinearLayout
        private var editButton: Button
        var id: String = "id"

        fun bind(property: PropertyOnPropertyListFragmentViewModel) {
            this.id = property.id
            viewModel.idSelected.observe(lifecycleOwner) {
                    id: String ->
                        if(id == this.id) {
                            selectedItem()
                        } else {
                            unselectedItem()
                        }
            }
            this.title.text = property.name
            this.city.text = property.city
            this.price.text = property.price
            val circularProgressDrawable
                    = CircularProgressDrawable(RealStateManagerApplication.contextApp)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            val url = property.mainPictureUrl
            try {
                Glide.with(RealStateManagerApplication.contextApp)
                    .load(url)
                    .placeholder(circularProgressDrawable).into(this.imageView)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            editButton.setOnClickListener {
                val intent = Intent(
                    itemView.context,
                    CreatePropertyActivity::class.java)
                intent.putExtra("created", property)
                itemView.context.startActivity(intent)
            }
        }

        init {
            itemView.setOnClickListener(this)
            this.background = itemView.findViewById(R.id.item_background)
            this.title = itemView.findViewById(R.id.property_list_title)
            this.city = itemView.findViewById(R.id.property_list_city)
            this.imageView = itemView.findViewById(R.id.image_main_property)
            this.price = itemView.findViewById(R.id.property_price)
            this.editButton = itemView.findViewById(R.id.button_edit)
        }

        private fun selectedItem() {
            this.background.setBackgroundColor(Color.parseColor("#00695C"))
        }

        private fun unselectedItem() {
            this.background.setBackgroundColor(Color.TRANSPARENT)
        }

        override fun onClick(v: View?) {
            if(viewModel.isLargeScreen) {
                viewModel.changeSelectId(this.id)
            } else {
                println("data " + adapterPosition + "  "+ this.id)
            }
        }
    }
}