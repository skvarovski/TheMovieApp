package com.example.themovieapptrainee.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.themovieapptrainee.databinding.ItemFilmNewBinding
import com.example.themovieapptrainee.model.TheMovieEntity

class ItemFilmViewHolder(
    private val binding: ItemFilmNewBinding,
    private val context: Context,
    private val listener: FilmAdapter.CallbackListener? = null,
) : ViewHolder(binding.root) {
    fun bind(item: TheMovieEntity) {
        binding.root.setOnClickListener {
            listener?.onClickItem(item)
        }
        with(binding) {
            itemTitle.text = item.title
            itemDescription.text = item.description
            Glide
                .with(context)
                .load(item.imageUrl)
                .into(itemImage)
        }
    }
}
