package com.rosseti.iddog.main.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rosseti.iddog.R
import kotlinx.android.synthetic.main.content_item.view.*

class MainAdapter(
    private val images: List<String>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var onItemClick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(images[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(images[adapterPosition])
            }
        }

        fun bind(image: String) {
            Glide.with(itemView)
                .load(image)
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(itemView.contentImageView)
        }
    }
}