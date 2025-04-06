package com.jer.banyumastourismapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jer.banyumastourismapp.domain.usecase.tourism.TourismUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val useCase: TourismUseCase) : ViewModel()  {
    val destinations = useCase.getDestinations().cachedIn(viewModelScope)
}