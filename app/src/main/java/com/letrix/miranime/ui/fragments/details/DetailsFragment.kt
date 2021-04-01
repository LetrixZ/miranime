package com.letrix.miranime.ui.fragments.details

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.letrix.miranime.R
import com.letrix.miranime.data.DataState
import com.letrix.miranime.data.models.Anime
import com.letrix.miranime.databinding.FragmentDetails2Binding
import com.letrix.miranime.databinding.FragmentDetailsBinding
import com.letrix.miranime.utils.Utils.parseState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailsFragmentArgs>()
    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchAnime(args.idMal)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view)

        setObservers()

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setObservers() {
        viewModel.anime.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Empty -> Timber.d("Empty")
                is DataState.Error -> Timber.d("Error: ${it.exception}")
                is DataState.Loading -> Timber.d("Loading")
                is DataState.Success -> {
                    Timber.d("Success ${it.data}")
                    setInfo(it.data)
                }
            }
        })
    }

    private fun setInfo(data: Anime) {
        binding.poster.load(data.poster)
        binding.title.text = data.title
        binding.genres.text = data.genres?.joinToString(" | ")
        binding.synonyms.text = data.synonyms?.joinToString("\n")
        binding.synopsis.text = data.synopsis
        binding.state.text = data.state?.let { parseState(it) }
        when (data.state) {
            1 -> {
                binding.stateCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_500))
            }
            2 -> {
                binding.stateCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red_500))
            }
            3 -> {
                binding.stateCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.yellow_500))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}