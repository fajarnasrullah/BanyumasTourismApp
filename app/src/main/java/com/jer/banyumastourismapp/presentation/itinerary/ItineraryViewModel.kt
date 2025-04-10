package com.jer.banyumastourismapp.presentation.itinerary

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.jer.banyumastourismapp.domain.model.Itinerary
import com.jer.banyumastourismapp.domain.model.ItineraryWithPlanCards
import com.jer.banyumastourismapp.domain.model.Plan
import com.jer.banyumastourismapp.domain.model.PlanCardData
import com.jer.banyumastourismapp.domain.model.User
import com.jer.banyumastourismapp.domain.usecase.tourism.TourismUseCase
import com.jer.banyumastourismapp.presentation.itinerary.component.ItineraryEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItineraryViewModel @Inject constructor(private val useCase: TourismUseCase): ViewModel()  {

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()

    private val _userData = MutableStateFlow<User?>(null)
    val userData: StateFlow<User?> = _userData

    private val _itinerary = MutableStateFlow<Itinerary?>(null)
    val itinerary: StateFlow<Itinerary?> = _itinerary

    private val _itineraryWithPlanCards = MutableStateFlow<ItineraryWithPlanCards?>(null)
    val itineraryWithPlanCards: StateFlow<ItineraryWithPlanCards?> = _itineraryWithPlanCards

//    private val _planCard = MutableStateFlow<PlanCardData?>(null)
//    val planCard: StateFlow<PlanCardData?> = _planCard

    private val _eventFlow = MutableSharedFlow<ItineraryEvent>()
    val eventFlow: SharedFlow<ItineraryEvent> = _eventFlow



//    var message =  mutableStateOf("")
//    var status =  mutableStateOf(false)


    suspend fun getItinerary(uid: String) {
        viewModelScope.launch {
//            val itinerary = useCase.getItinerary(uid)
            val itinerary = useCase.getItineraryWithPlanCards(uid)
//            _itinerary.value = itinerary
            _itineraryWithPlanCards.value = itinerary
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


    fun insertItinerary(itinerary: Itinerary) {
        viewModelScope.launch {
            try {
                val itineraryId = useCase.insertItinerary(itinerary)

                repeat(itinerary.daysCount) {index ->
                    val planCard = PlanCardData(itineraryId = itineraryId.toInt(), title = "Day ${index + 1}")
                    useCase.insertPlanCard(planCard)
                }
                _eventFlow.emit(ItineraryEvent.Success)


            } catch (e: Exception) {
                _eventFlow.emit(ItineraryEvent.Error(e.message ?: "An unexpected error occurred"))
                Log.e("ItineraryViewModel", "Error inserting itinerary", e)
            }
        }
    }



    fun insertPlan(plan: Plan) {
        viewModelScope.launch {
            try {
                useCase.insertPlan(plan)
                _eventFlow.emit(ItineraryEvent.Success)
            } catch (e: Exception) {
                _eventFlow.emit(ItineraryEvent.Error(e.message ?: "An unexpected error occurred"))
                Log.e("ItineraryViewModel", "Error inserting plan", e)
            }
        }
    }

    fun deleteItinerary(itinerary: Itinerary) {
        viewModelScope.launch {
            try {
                useCase.deleteItinerary(itinerary)
                _eventFlow.emit(ItineraryEvent.Success)
            } catch (e: Exception) {
                _eventFlow.emit(ItineraryEvent.Error(e.message ?: "An unexpected error occurred"))
                Log.e("ItineraryViewModel", "Error delete itinerary", e)
            }
        }
    }

    fun deletePlanCard(planCard: PlanCardData) {
        viewModelScope.launch {
            try {
                useCase.deletePlanCard(planCard)
                _eventFlow.emit(ItineraryEvent.Success)
            } catch (e: Exception) {
                _eventFlow.emit(ItineraryEvent.Error(e.message ?: "An unexpected error occurred"))
                Log.e("ItineraryViewModel", "Error delete plan card", e)
            }
        }
    }


//    fun onEvent(event: ItineraryEvent) {
//        when (event) {
//            is ItineraryEvent.InsertItinerary -> {
//                viewModelScope.launch {
//                    if (_itinerary == null) {
//                        insertItinerary(event.itinerary)
//                    } else {
//                        deleteItinerary(event.itinerary)
//                    }
//                }
//            }
//            is ItineraryEvent.RemoveSideEffect -> {
//                sideEffect = null
//            }
//        }
//    }
//
//    private suspend fun insertItinerary(itinerary: Itinerary) {
//        useCase.insertItinerary(itinerary)
//        sideEffect = "Create Itinerary Successfully"
//    }
//
//    private suspend fun deleteItinerary(itinerary: Itinerary) {
//        useCase.deleteItinerary(itinerary)
//        sideEffect = "Delete Itinerary Successfully"
//    }

//     fun insertItinerary(itinerary: Itinerary) {
//        viewModelScope.launch {
//            try {
//                useCase.insertItinerary(itinerary)
//                message.value = "Create Itinerary Successfully"
//                status.value = true
//            } catch (e: Exception) {
//                message.value = "Create Itinerary Failed"
//                status.value = false
//                Log.e("ItineraryViewModel", "Error inserting itinerary", e)
//            }
//        }
//
//    }
//
//    fun deleteItinerary(itinerary: Itinerary) {
//        viewModelScope.launch {
//            try {
//                useCase.deleteItinerary(itinerary)
//                message.value = "Delete Itinerary Successfully"
//            } catch (e: Exception) {
//                message.value = "Delete Itinerary Failed"
//                Log.e("ItineraryViewModel", "Error delete itinerary", e)
//            }
//        }
//    }



}