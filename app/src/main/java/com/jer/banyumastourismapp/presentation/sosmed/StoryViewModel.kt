package com.jer.banyumastourismapp.presentation.sosmed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.jer.banyumastourismapp.domain.model.Story
import com.jer.banyumastourismapp.domain.model.User
import com.jer.banyumastourismapp.domain.usecase.tourism.TourismUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(private val useCase: TourismUseCase): ViewModel() {

    private val _story = MutableStateFlow<List<Story>>(emptyList())
    val story: StateFlow<List<Story>> = _story.asStateFlow()

    private val _textAlert = MutableStateFlow<String?>(null)
    val textAlert : StateFlow<String?> = _textAlert

    private val _userData = MutableStateFlow<User?>(null)
    val userData : StateFlow<User?> = _userData

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()


    fun insertStory(story: Story) {
        viewModelScope.launch {
            try {
                useCase.insertStory(story)
                _textAlert.value = "Post Story Successfully"
                Log.d("StoryViewModel", "Success to Insert Story: $story")
            } catch (e: Exception) {
                _textAlert.value = "Post Story is Failed"
                Log.e("StoryViewModel", "Failed to Insert Story: ${e.message}")

            }

        }
    }


    fun getStory() {
        viewModelScope.launch {
            try {
                _story.value = useCase.getStory()
                Log.d("StoryViewModel", "Success to GetStory: ${_story.value}")
            } catch (e: Exception) {
                Log.e("StoryViewModel", "Failed to GetStory: ${e.message}")
            }
        }
    }

    fun loadStory() {
        viewModelScope.launch {
            getStory()
        }
    }

    fun getUserData() {
        val user = auth.currentUser
        user?.let {
            database.getReference("users").child(it.uid).get()
                .addOnSuccessListener { snapshot ->
                    val userData = snapshot.getValue(User::class.java)
                    viewModelScope.launch {
                        userData?.let {
                            _userData.value = it
                            useCase.insertUser(it)
                            Log.d("ItineraryViewModel", "Get user data with uid: ${it.uid}")
                        }
                    }
                }
                .addOnFailureListener {
                    Log.e("ItineraryViewModel", "Error getting user data", it)
                }
        }
    }


}