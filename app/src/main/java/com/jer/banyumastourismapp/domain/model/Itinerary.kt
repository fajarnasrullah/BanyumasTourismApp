package com.jer.banyumastourismapp.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.jer.banyumastourismapp.R
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(foreignKeys = [ForeignKey(
    entity = User::class,
    parentColumns = arrayOf("uid"),
    childColumns = arrayOf("uid"),
    onUpdate = ForeignKey.CASCADE,
    onDelete = ForeignKey.CASCADE
)])
data class Itinerary (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "uid") val uid: String = "",
    val daysCount: Int = 0,
    val title: String? = null,
    val date: String? = null,
    val description: String? = null,
    val cityGoals: List<City> = emptyList(),
    val notes: String? = null,
    val totalMoneySpend: Int = 0,
    val totalDestinations: Int = 0,
    val totalMembers: Int = 0,
    val listCardPlan: List<List<Plan>> = emptyList(),
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
