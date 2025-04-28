package com.jer.banyumastourismapp.presentation.destination

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.domain.usecase.tourism.TourismUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DestinationListViewModel @Inject constructor(private val useCase: TourismUseCase): ViewModel() {

    private val _destination = MutableStateFlow<List<Destination>>(emptyList())
    val destination: StateFlow<List<Destination>> = _destination.asStateFlow()



    fun destinationByTitle(title: String) {
        viewModelScope.launch {
            try {
                val newDesti = useCase.getDestinationByTitle(title)
                _destination.value = newDesti
                Log.d("DestinationListViewModel", "Success get destination by title with query: $title")
                Log.d("DestinationListViewModel", "List Desti: ${ newDesti }")
            } catch (e: Exception) {
                Log.d("DestinationListViewModel", e.message.toString())
            }
        }
    }







}