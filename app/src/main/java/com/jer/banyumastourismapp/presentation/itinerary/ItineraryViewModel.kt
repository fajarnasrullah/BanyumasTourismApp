package com.jer.banyumastourismapp.presentation.itinerary

import android.content.Context
import android.graphics.Paint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.graphics.pdf.PdfDocument
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.jer.banyumastourismapp.domain.model.Itinerary
import com.jer.banyumastourismapp.domain.model.ItineraryWithPlanCards
import com.jer.banyumastourismapp.domain.model.Plan
import com.jer.banyumastourismapp.domain.model.PlanCardData
import com.jer.banyumastourismapp.domain.model.User
import com.jer.banyumastourismapp.domain.usecase.tourism.TourismUseCase
import com.jer.banyumastourismapp.presentation.itinerary.component.EventForAll
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
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

    private val _eventFlow = MutableSharedFlow<EventForAll>()
    val eventFlow: SharedFlow<EventForAll> = _eventFlow




//    var message =  mutableStateOf("")
//    var status =  mutableStateOf(false)

    fun loadItinerary() {
        viewModelScope.launch {
            val firebaseUser = auth.currentUser
            firebaseUser?.let {
                getItinerary(it.uid)
            }
        }
    }

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
                _eventFlow.emit(EventForAll.Success)


            } catch (e: Exception) {
                _eventFlow.emit(EventForAll.Error(e.message ?: "An unexpected error occurred"))
                Log.e("ItineraryViewModel", "Error inserting itinerary", e)
            }
        }
    }



    fun insertPlan(plan: Plan) {
        viewModelScope.launch {
            try {
                useCase.insertPlan(plan)
                _eventFlow.emit(EventForAll.Success)
            } catch (e: Exception) {
                _eventFlow.emit(EventForAll.Error(e.message ?: "An unexpected error occurred"))
                Log.e("ItineraryViewModel", "Error inserting plan", e)
            }
        }
    }

    fun deleteItinerary(itinerary: Itinerary) {
        viewModelScope.launch {
            try {
                useCase.deleteItinerary(itinerary)
                _eventFlow.emit(EventForAll.Success)
                Log.e("ItineraryViewModel", "Succeed delete itinerary")

            } catch (e: Exception) {
                _eventFlow.emit(EventForAll.Error(e.message ?: "An unexpected error occurred"))
                Log.e("ItineraryViewModel", "Error delete itinerary", e)
            }
        }
    }

    fun deletePlanCard(planCard: PlanCardData) {
        viewModelScope.launch {
            try {
                useCase.deletePlanCard(planCard)
                _eventFlow.emit(EventForAll.Success)
            } catch (e: Exception) {
                _eventFlow.emit(EventForAll.Error(e.message ?: "An unexpected error occurred"))
                Log.e("ItineraryViewModel", "Error delete plan card", e)
            }
        }
    }

    fun deleteListPlan(planCardDataId: Int) {
        viewModelScope.launch {
            try {
                useCase.deleteListPlan(planCardDataId)
                _eventFlow.emit(EventForAll.Success)
                Log.e("ItineraryViewModel", "Succeed delete list plan")
            } catch (e: Exception) {
                _eventFlow.emit(EventForAll.Error(e.message ?: "An unexpected error occurred"))
                Log.e("ItineraryViewModel", "Error delete list plan", e)
            }
        }
    }

    fun generatePdf(context: Context, itineraryData: ItineraryWithPlanCards) {
        val pdfDocument = PdfDocument()
        val paint = Paint()

        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        var yPosition = 30

        paint.textSize = 20f
        canvas.drawText("Itinerary: ${itineraryData.itinerary.title}", 20f, yPosition.toFloat(), paint)

        yPosition += 40
        paint.textSize = 14f
        canvas.drawText("Date: ${itineraryData.itinerary.date}", 20f, yPosition.toFloat(), paint)

//        val textPaint = TextPaint().apply {
//            color = Color.BLACK
//            textSize = 14f
//        }
//
//        val marginStart = 40f
//        val marginEnd = 40f
//        val textWidth = pageInfo.pageWidth - marginStart - marginEnd
//
//        var yPosition = 100f
//
//// Title
//        canvas.drawText("Itinerary: ${itinerary.title}", marginStart, yPosition, textPaint)
//        yPosition += 30f
//
//// Description (Justified)
//        yPosition += drawJustifiedText(
//            canvas = canvas,
//            text = "Description: ${itinerary.description.orEmpty()}",
//            x = marginStart,
//            y = yPosition,
//            paint = textPaint,
//            width = textWidth.toInt()
//        ) + 20f
//
//// Notes (Justified)
//        yPosition += drawJustifiedText(
//            canvas = canvas,
//            text = "Notes: ${itinerary.notes.orEmpty()}",
//            x = marginStart,
//            y = yPosition,
//            paint = textPaint,
//            width = textWidth.toInt()
//        ) + 20f

        yPosition += 30
        canvas.drawText("Description: ${itineraryData.itinerary.description}", 20f, yPosition.toFloat(), paint)

        yPosition += 30
        canvas.drawText("Notes: ${itineraryData.itinerary.notes}", 20f, yPosition.toFloat(), paint)

        yPosition += 30
        canvas.drawText("Days Amount: ${itineraryData.itinerary.daysCount}", 20f, yPosition.toFloat(), paint)

        itineraryData.listPlanCard.forEachIndexed { index, planCard ->
            yPosition += 40
            canvas.drawText("Day ${index + 1}: ${planCard.planCardData.title}", 20f, yPosition.toFloat(), paint)

            planCard.listPlan.forEach { plan ->
                yPosition += 20
                canvas.drawText("• ${plan.title} (${plan.time}) - Rp${plan.cost}", 40f, yPosition.toFloat(), paint)
            }
        }

        pdfDocument.finishPage(page)

        val file = File(context.getExternalFilesDir(null), "Itinerary-${System.currentTimeMillis()}.pdf")
        pdfDocument.writeTo(FileOutputStream(file))
        pdfDocument.close()

        Toast.makeText(context, "PDF saved in: ${file.absolutePath}", Toast.LENGTH_LONG).show()
        Log.d("ItineraryViewModel", "PDF saved in: ${file.absolutePath}")
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