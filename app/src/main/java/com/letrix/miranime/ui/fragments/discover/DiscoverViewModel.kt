package com.letrix.miranime.ui.fragments.discover

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.letrix.miranime.data.DataState
import com.letrix.miranime.data.models.AnimeList
import com.letrix.miranime.data.network.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel
@Inject constructor(private val repository: ApiRepository) : ViewModel() {

    private val _home = MutableLiveData<DataState<List<AnimeList>>>(DataState.Empty)
    val home: LiveData<DataState<List<AnimeList>>> get() = _home

    val bundle = Bundle()

    fun fetchHome() {
        viewModelScope.launch {
            _home.postValue(DataState.Loading)
            try {
                val homeList = repository.getHome()
                if (homeList != null) _home.postValue(DataState.Success(homeList))
            } catch (e: Exception) {
                _home.postValue(DataState.Error(e))
            }
        }
    }

}