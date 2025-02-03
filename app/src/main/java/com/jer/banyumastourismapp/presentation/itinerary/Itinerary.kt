package com.jer.banyumastourismapp.presentation.itinerary

import android.widget.TimePicker
import androidx.compose.ui.graphics.painter.Painter

data class Itinerary(
    val daysCount: Int,
    val title: String,
    val description: String,
    val membersCount: Int,
    val cityGoals: List<City>,
    val notes: String? = null,
    val listCardPlan: List<List<Plan>>? = null,
)

data class City(
    val name: String,
    val imageUrl: String,
)

data class ListPlan(
    val listPlan: List<Plan>
)

data class Plan(
    val category: List<PlanCategory>? = null,
    val title: String? = null,
    val city: List<City>? = null,
    val time: String? = null,
    val cost: Int? = null,
)

data class PlanCategory(
    val name: String,
    val icon: Painter,
)

object allAboutList {
    var listPlan: MutableList<Plan> = mutableListOf()
}
