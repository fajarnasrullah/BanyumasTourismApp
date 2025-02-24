package com.jer.banyumastourismapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jer.banyumastourismapp.R
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity
data class Itinerary(
    val daysCount: Int = 0,
    @PrimaryKey val title: String,
    val description: String = "",
    val membersCount: Int = 0,
    val cityGoals: List<City> = emptyList(),
    val notes: String? = null,
    val listCardPlan: List<List<Plan>>? = null,
): Parcelable

@Parcelize
data class City(
    val name: String,
    val imageUrl: String,
): Parcelable

data class ListPlan(
    val listPlan: List<Plan>
)

@Parcelize
data class Plan(
    val category: Int,
//    PlanCategory? = null,
//    List<PlanCategory>? = null,
//    List<List<PlanCategory>>,
    val title: String? = null,
    val city: List<City>? = null,
    val time: String? = null,
    val cost: Int? = null,
): Parcelable



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
