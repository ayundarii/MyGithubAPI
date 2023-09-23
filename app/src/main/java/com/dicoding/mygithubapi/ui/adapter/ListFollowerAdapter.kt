package com.dicoding.mygithubapi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mygithubapi.R
import com.dicoding.mygithubapi.data.remote.response.FollowItem

class ListFollowerAdapter(private val  listUser: ArrayList<FollowItem>) : RecyclerView.Adapter<ListFollowerAdapter.ListFollowHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFollowHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_layout, parent, false)
        return ListFollowHolder(view)
    }

    override fun onBindViewHolder(holder: ListFollowHolder, position: Int) {
        Glide.with(holder.imgPhoto)
            .load(listUser[position].avatarUrl)
            .into(holder.imgPhoto)
        holder.tvUsername.text = listUser[position].login
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[position]) }
    }

    override fun getItemCount(): Int = listUser.size


    class ListFollowHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvUsername: TextView = itemView.findViewById(R.id.tv_item_username)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FollowItem)
    }
}