package com.openclassrooms.realestatemanager.presentation.fragments.propertyList

import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.common.io.Resources.getResource
import com.google.firebase.storage.FirebaseStorage
import com.openclassrooms.realestatemanager.GlideApp
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.RealStateManagerApplication
import com.openclassrooms.realestatemanager.utils.PropertyState

interface PropertyAdapterListener {
    fun onEditButtonClick(index: Int)
    fun onItemClick(index: Int)
}


class PropertyAdapter(
    data: List<PropertyOnPropertyListFragmentViewModel>?,
    private val listener: PropertyAdapterListener
) : RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>() {

    private var mData: List<PropertyOnPropertyListFragmentViewModel>? = data

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
        return PropertyViewHolder(view, listener = listener)
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
        itemView: View,
        val listener: PropertyAdapterListener
    )
        : RecyclerView.ViewHolder(itemView) {

        private val title: TextView get() = itemView.findViewById(R.id.property_list_title)
        private var city: TextView // TODO
        private var price: TextView
        private var imageView: ImageView
        private val background: LinearLayout get() = itemView.findViewById(R.id.item_background)
        private val state: AppCompatImageView get() = itemView.findViewById(R.id.img_project_state)
        private var editButton: Button
        var id: String = "id"

        fun bind(property: PropertyOnPropertyListFragmentViewModel) {
            this.id = property.id
            if(property.isSelected) {
                selectedItem()
            } else {
                unselectedItem()
            }
            this.title.text = property.name
            this.city.text = property.city
            this.price.text = "${property.price} $"
            try {
                val storage = FirebaseStorage.getInstance().reference.child(property.mainPictureUrl)
                GlideApp.with(itemView.context)
                    .load(storage)
                    .error(R.drawable.no_image_found)
                    .into(imageView)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if(property.state) {
                state.setColorFilter(Color.GREEN)
            } else {
                state.setColorFilter(Color.RED)
            }
            editButton.setOnClickListener {
                listener.onEditButtonClick(adapterPosition)
            }
        }

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
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
    }
}