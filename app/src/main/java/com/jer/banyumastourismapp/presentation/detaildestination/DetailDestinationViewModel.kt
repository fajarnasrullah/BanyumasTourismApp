package com.jer.banyumastourismapp.presentation.detaildestination

import androidx.lifecycle.ViewModel
import com.jer.banyumastourismapp.domain.usecase.tourism.TourismUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailDestinationViewModel @Inject constructor(val useCase: TourismUseCase):ViewModel() {

}