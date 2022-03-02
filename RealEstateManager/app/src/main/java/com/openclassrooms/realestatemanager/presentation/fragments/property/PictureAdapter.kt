package com.openclassrooms.realestatemanager.presentation.fragments.property

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.RealStateManagerApplication
import com.openclassrooms.realestatemanager.data.dao.entities.PictureEntity

class PictureAdapter (data: List<PictureEntity>?
) : RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {
    private var mData: List<PictureEntity>? = data
    private var context: Context? = null

    fun updateList(viewModelList: List<PictureEntity>?) {
        mData = listOf()
        mData = viewModelList!!
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.picture_item_view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        context = parent.context
        return PictureViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.bind(mData!![position])
    }

    // total number of rows
    override fun getItemCount(): Int {
        return mData!!.size
    }


    // stores and recycles views as they are scrolled off screen
    class PictureViewHolder internal constructor(
        itemView: View
    )
        : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var imageView: ImageView

        fun bind(picture: PictureEntity) {
            val circularProgressDrawable
                    = CircularProgressDrawable(RealStateManagerApplication.contextApp)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            val url = picture.url
            try {
                Glide.with(RealStateManagerApplication.contextApp)
                    .load(url)
                    .placeholder(circularProgressDrawable).into(this.imageView)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        init {
            itemView.setOnClickListener(this)
            this.imageView = itemView.findViewById(R.id.image_main_property)
        }

        override fun onClick(v: View?) {
          // ZOOM
        }
    }

}