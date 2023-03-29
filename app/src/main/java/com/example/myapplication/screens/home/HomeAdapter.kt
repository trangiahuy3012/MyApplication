package com.example.myapplication.screens.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.myapplication.data.Movie

interface OnHomeItemClick{
    fun onClickItem(item: View, restaurant: Movie)

    fun onLongClick(item: View)
}
class HomeAdapter(private val callback: OnHomeItemClick): ListAdapter<Movie, HomeViewHolder>(RestaurantDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val idol = getItem(position)
        holder.bindData(idol, callback)
    }

    class RestaurantDiffUtil: DiffUtil.ItemCallback<Movie>()   {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.title == newItem.title
        }

    }
}