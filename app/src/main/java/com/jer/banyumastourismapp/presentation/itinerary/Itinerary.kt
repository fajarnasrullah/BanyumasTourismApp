package com.jer.banyumastourismapp.presentation.itinerary

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.jer.banyumastourismapp.R

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
    val category: Int ,
//    PlanCategory? = null,
//    List<PlanCategory>? = null,
//    List<List<PlanCategory>>,
    val title: String? = null,
    val city: List<City>? = null,
    val time: String? = null,
    val cost: Int? = null,
)
//object ListCategoryPlan {
    val categoryPlan = listOf(
        PlanCategory("On The Way", R.drawable.caricon),
        PlanCategory("Hotel", R.drawable.bedicon),
        PlanCategory("Resto", R.drawable.foodicon),
        PlanCategory("Destination", R.drawable.placeicon),
    )
//}


data class PlanCategory(
    val name: String,
    val icon:  Int,
)

object allAboutList {
    var listPlan: MutableList<Plan> = mutableListOf()
}
