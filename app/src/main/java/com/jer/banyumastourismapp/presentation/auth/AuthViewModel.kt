package com.jer.banyumastourismapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.jer.banyumastourismapp.domain.usecase.tourism.TourismUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val useCase: TourismUseCase): ViewModel() {

    private val _signInResult = MutableStateFlow<Result<FirebaseUser?>?>(null)
    val signInResult: StateFlow<Result<FirebaseUser?>?> = _signInResult

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _signInResult.value = useCase.signinWithGoogle(idToken)
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return useCase.getCurrentUser()
    }

}