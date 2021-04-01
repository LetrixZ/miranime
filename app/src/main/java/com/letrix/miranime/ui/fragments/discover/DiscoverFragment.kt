package com.letrix.miranime.ui.fragments.discover

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.letrix.anime.utils.ScrollStateHolder
import com.letrix.miranime.R
import com.letrix.miranime.data.DataState
import com.letrix.miranime.data.models.AnimeList
import com.letrix.miranime.databinding.FragmentDiscoverBinding
import com.letrix.miranime.ui.adapters.AnimeAdapter
import com.letrix.miranime.ui.adapters.EpisodeAnimeAdapter
import com.letrix.miranime.ui.adapters.ItemListAdapter
import com.letrix.miranime.ui.adapters.TopAnimeAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber.d

@AndroidEntryPoint
class DiscoverFragment : Fragment(R.layout.fragment_discover) {

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!

    private lateinit var scrollStateHolder: ScrollStateHolder

    private val viewModel by activityViewModels<DiscoverViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchHome()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDiscoverBinding.bind(view)
        scrollStateHolder = ScrollStateHolder(viewModel.bundle)
        setObservers()
    }

    private fun setObservers() {
        viewModel.home.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Empty -> d("Empty")
                is DataState.Error -> d("Error: ${it.exception}")
                is DataState.Loading -> d("Loading")
                is DataState.Success -> {
                    d("Success ${it.data.size}")
                    setRecycler(it.data)
                }
            }
        })
    }

    private fun setRecycler(data: List<AnimeList>) {
        val concatAdapter = ConcatAdapter()
        val topAdapter = ItemListAdapter(
            adapter = TopAnimeAdapter(::openDetails).also { it.submitList(data[0].list) },
            title = data[0].title,
            scrollStateHolder
        )
        concatAdapter.addAdapter(topAdapter)
        val latestReleasesAdapter = ItemListAdapter(
            adapter = EpisodeAnimeAdapter().also { it.submitList(data[1].list) },
            title = data[1].title,
            scrollStateHolder
        )
        concatAdapter.addAdapter(latestReleasesAdapter)
        data.takeLast(2).forEach { list ->
            concatAdapter.addAdapter(
                ItemListAdapter(
                    AnimeAdapter(::openDetails).also { it.submitList(list.list) },
                    title = list.title,
                    scrollStateHolder
                )
            )
        }
        binding.discoverRecyclerView.adapter = concatAdapter
    }

    private fun openDetails(idMal: Int) {
        findNavController().navigate(DiscoverFragmentDirections.actionDiscoverFragmentToDetailFragment(idMal))
    }

    override fun onPause() {
        super.onPause()
        if (this::scrollStateHolder.isInitialized) scrollStateHolder.onSaveInstanceState(viewModel.bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}