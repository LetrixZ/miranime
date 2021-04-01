package com.letrix.miranime.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.letrix.miranime.data.models.Anime
import com.letrix.miranime.databinding.ItemAnimeBinding
import kotlin.reflect.KFunction1

class AnimeAdapter(val openDetails: (Int) -> Unit) : ListAdapter<Anime, AnimeAdapter.ItemHolder>(
    object : DiffUtil.ItemCallback<Anime>() {
        override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
            return oldItem.idMal == newItem.idMal
        }

        override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
            return oldItem == newItem
        }
    }) {
    inner class ItemHolder(private val binding: ItemAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Anime) {
            binding.title.text = item.title
            binding.poster.load(item.poster)
            binding.cardItem.setOnClickListener {
                item.idMal?.let { it1 -> openDetails(it1) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder = ItemHolder(
        ItemAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ItemHolder, position: Int) =
        holder.bind(getItem(position))
}