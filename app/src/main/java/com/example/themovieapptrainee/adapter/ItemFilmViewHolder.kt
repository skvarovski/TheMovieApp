package com.example.themovieapptrainee.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.themovieapptrainee.databinding.ItemFilmBinding
import com.example.themovieapptrainee.model.MovieEntity

class ItemFilmViewHolder(
    private val binding: ItemFilmBinding,
    private val context: Context
) : ViewHolder(binding.root) {
    fun bind(item: MovieEntity) {
        with(binding) {
            filmName.text = item.title
            filmYear.text = item.year
            filmRating.text = item.rating
            filmDescription.text = item.description
            Glide.with(context).load(item.imageUrl).into(filmImage) }
    }
}
