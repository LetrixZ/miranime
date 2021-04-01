package com.letrix.miranime.ui.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.letrix.anime.utils.ScrollStateHolder
import com.letrix.miranime.R
import com.letrix.miranime.databinding.ItemListBinding
import com.letrix.miranime.utils.Utils

class ItemListAdapter(
    private val adapter: RecyclerView.Adapter<*>,
    private val title: String,
    private val scrollStateHolder: ScrollStateHolder
) : RecyclerView.Adapter<ItemListAdapter.ItemHolder>() {
    inner class ItemHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root), ScrollStateHolder.ScrollStateKeyProvider {
        fun onCreate() {
            scrollStateHolder.setupRecyclerView(binding.itemRecyclerView, this)
        }

        fun onBind() {
            binding.itemRecyclerView.adapter = adapter
            binding.listTitle.text = Utils.parseTitle((title))
            scrollStateHolder.restoreScrollState(binding.itemRecyclerView, this)
            when (title) {
                "top_anime" -> {
                    val constraintSet = ConstraintSet()
                    constraintSet.clone(binding.root)
                    constraintSet.connect(R.id.list_title, ConstraintSet.TOP, R.id.item_recycler_view, ConstraintSet.TOP)
                    constraintSet.connect(R.id.item_recycler_view, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                    constraintSet.connect(R.id.item_recycler_view, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
                    constraintSet.applyTo(binding.root)
                    binding.titleBackgroundShadow.isVisible = true
                    setSnapHelper()
                }
                "latest_episodes" -> setSnapHelper()
            }
        }

        private fun setSnapHelper() {
            val snapHelper = GravitySnapHelper(Gravity.CENTER)
            snapHelper.attachToRecyclerView(binding.itemRecyclerView)
        }

        fun onRecycled() {
            scrollStateHolder.saveScrollState(binding.itemRecyclerView, this)
        }

        override fun getScrollStateKey(): String = title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = ItemHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        itemHolder.onCreate()
        return itemHolder
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) = holder.onBind()

    override fun getItemCount(): Int = 1

    override fun onViewRecycled(holder: ItemHolder) {
        super.onViewRecycled(holder)
        holder.onRecycled()
    }

    /*private fun getTitle(list: String, context: Context): String =
        when (list.split('-')[0]) {
            "tracking" -> context.getString(R.string.tracking)
//            "ongoings" -> context.getString(R.string.season_year, list.split('-')[1], list.split('-')[2])
            "ongoings" -> context.getString(R.string.ongoings)
            "latest_episodes" -> context.getString(R.string.featured)
            "top_anime" -> context.getString(R.string.top_anime)
            "finished" -> context.getString(R.string.finisheds)
            "latest_anime", "latest_additions" -> context.getString(R.string.latest_added)
            "latest_series" -> context.getString(R.string.latest_series)
            "latest_movies" -> context.getString(R.string.latest_movies)
            "latest_ovas" -> context.getString(R.string.latest_ovas)
            else -> list
        }*/
}