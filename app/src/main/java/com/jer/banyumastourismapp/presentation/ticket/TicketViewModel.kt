package com.jer.banyumastourismapp.presentation.ticket

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.jer.banyumastourismapp.data.local.DaoTicket
import com.jer.banyumastourismapp.domain.model.Ticket
import com.jer.banyumastourismapp.domain.model.User
import com.jer.banyumastourismapp.domain.usecase.tourism.TourismUseCase
import com.jer.banyumastourismapp.presentation.itinerary.component.EventForAll
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(val useCase: TourismUseCase): ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()

    private val _userData = MutableStateFlow<User?>(null)
    val userData: StateFlow<User?> = _userData

    private val _ticket = MutableStateFlow<Ticket?>(null)
    val ticket: StateFlow<Ticket?> = _ticket

    private val _ticketHistory = MutableStateFlow<List<Ticket>?>(null)
    val ticketHistory: StateFlow<List<Ticket>?> = _ticketHistory.asStateFlow()

    private val _eventFlow = MutableSharedFlow<EventForAll>()
    val eventFlow: SharedFlow<EventForAll> = _eventFlow

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading




    fun insertTicket(ticket: Ticket) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                useCase.insertTicket(ticket)
                _eventFlow.emit(EventForAll.Success)
                _isLoading.value = false
                Log.d("TicketViewModel", "Success to Insert New Ticket")
            } catch (e: Exception) {
                _isLoading.value = false
                _eventFlow.emit(EventForAll.Error(e.message ?: "An unexpected error occurred"))
                Log.e("TicketViewModel", "Error Insert Ticket", e)
            }
        }
    }

    fun deleteTicket(ticket: Ticket) {
        viewModelScope.launch {
            try {
                useCase.deleteTicket(ticket)
                Log.d("TicketViewModel", "Success to Delete Ticket")
            } catch (e: Exception) {
                Log.e("TicketViewModel", "Error Delete Ticket", e)
            }
        }
    }

    fun getTicket(uid: String) {
        viewModelScope.launch {
            try {
                val newTicket = useCase.getTicket(uid)
                _ticket.value = newTicket
                Log.d("TicketViewModel", "Success to Get Ticket")

            } catch (e: Exception) {
                Log.e("TicketViewModel", "Error to Get Ticket", e)
            }
        }
    }

    fun loadTicket() {
        viewModelScope.launch {
            val firebaseUser = auth.currentUser
            firebaseUser?.let {
                getTicket(it.uid)
            }
        }
    }

    fun getTicketHistory(uid: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _isLoading.value = false
                val history = useCase.getTicketHistory(uid)
                _ticketHistory.value = history
                Log.d("TicketViewModel", "Success to Get Ticket History")
            } catch (e: Exception) {
                _isLoading.value = true
                Log.e("TicketViewModel", "Error to Get Ticket History", e)
            }
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
                            Log.d("TicketViewModel", "Get user data with uid: ${it.uid}")
                        }
                    }
                }
                .addOnFailureListener {
                    Log.e("TicketViewModel", "Error getting user data", it)
                }
        }
    }


}