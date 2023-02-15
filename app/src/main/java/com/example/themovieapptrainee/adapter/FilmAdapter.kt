package com.example.themovieapptrainee.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.themovieapptrainee.databinding.ItemFilmBinding
import com.example.themovieapptrainee.model.MovieEntity
import com.example.themovieapptrainee.ui.main.MainFragment

class FilmAdapter : ListAdapter<MovieEntity, ItemFilmViewHolder>(ItemsDiffItemCallback) {

    var listener: CallbackListener? = null
    var items: List<MovieEntity>
        get() = currentList
        set(value) = submitList(value)

    interface CallbackListener {
        fun onClickItem(item: MovieEntity)
    }

    fun initListener(fragment: MainFragment) {
        this.listener = fragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemFilmViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFilmBinding.inflate(layoutInflater, parent, false)
        return ItemFilmViewHolder(binding, parent.context, listener)
    }

    override fun onBindViewHolder(holder: ItemFilmViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object {

        private object ItemsDiffItemCallback : DiffUtil.ItemCallback<MovieEntity>() {

            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun getChangePayload(oldItem: MovieEntity, newItem: MovieEntity) = Any()
        }
    }
}
