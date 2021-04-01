package com.letrix.miranime.ui.adapters

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.letrix.miranime.data.models.Anime
import com.letrix.miranime.databinding.ItemAnimeEpisodeBinding

class EpisodeAnimeAdapter : ListAdapter<Anime, EpisodeAnimeAdapter.ItemHolder>(
    object : DiffUtil.ItemCallback<Anime>() {
        override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
            return oldItem.idMal == newItem.idMal
        }

        override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
            return oldItem == newItem
        }
    }) {
    inner class ItemHolder(private val binding: ItemAnimeEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Anime) {
            val spannableString = SpannableStringBuilder("Episode ${item.latestEpisode} | ${item.title}").also {
                it.setSpan(StyleSpan(Typeface.BOLD), it.indexOf("|"), it.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            binding.titleEpisode.text = spannableString
            binding.poster.load(item.thumbnail ?: item.poster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder = ItemHolder(
        ItemAnimeEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ItemHolder, position: Int) =
        holder.bind(getItem(position))
}