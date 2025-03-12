package com.jer.banyumastourismapp.presentation.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.jer.banyumastourismapp.domain.model.User
import com.jer.banyumastourismapp.domain.usecase.tourism.TourismUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val useCase: TourismUseCase) : ViewModel() {


    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()

    private val _userData = MutableStateFlow<User?>(null)
    val userData: StateFlow<User?> = _userData

    fun getUserData() {
        val user = auth.currentUser
        user?.let {
            database.getReference("users").child(it.uid).get()
                .addOnSuccessListener { snapshot ->
                    val userData = snapshot.getValue(User::class.java)
                    _userData.value = userData
                }
                .addOnFailureListener {
                    Log.e("ProfileViewModel", "Error getting user data", it)
                }
        }
    }

    fun editProfile(name: String, desc: String) {
        useCase.updateUserData(name, desc)
    }


}