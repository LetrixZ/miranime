package com.letrix.miranime.ui.fragments.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.letrix.miranime.data.DataState
import com.letrix.miranime.data.models.Anime
import com.letrix.miranime.data.network.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel() {

    private val _anime = MutableLiveData<DataState<Anime>>(DataState.Empty)
    val anime: LiveData<DataState<Anime>> get() = _anime

    fun fetchAnime(idMal: Int) {
        viewModelScope.launch {
            _anime.postValue(DataState.Loading)
            try {
                val anime = repository.get(idMal)
                if (anime != null) _anime.postValue(DataState.Success(anime))
            } catch (e: Exception) {
                _anime.postValue(DataState.Error(e))
            }
        }
    }

}