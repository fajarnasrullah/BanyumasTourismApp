package com.jer.banyumastourismapp.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
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
    val notes: String? = null,
    val totalMoneySpend: Int = 0,
    val totalDestinations: Int = 0,
    val totalMembers: Int = 0,
): Parcelable

@Parcelize
data class City(
    val name: String,
    val imageUrl: String,
): Parcelable

@Parcelize
@Entity(
    foreignKeys = [ForeignKey(
        entity = Itinerary::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("itineraryId"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class PlanCardData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "itineraryId") val itineraryId: Int,
    val title: String? = null,
) : Parcelable

@Parcelize
@Entity(foreignKeys = [ForeignKey(
    entity = PlanCardData::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("planCardDataId"),
    onUpdate = ForeignKey.CASCADE,
    onDelete = ForeignKey.CASCADE
)])
data class Plan(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "planCardDataId") val planCardDataId: Int,
    val category: Int,
//    PlanCategory? = null,
//    List<PlanCategory>? = null,
//    List<List<PlanCategory>>,
    val title: String? = null,
//    val city: List<City>? = null,
    val time: String? = null,
    val cost: Int? = null,
): Parcelable

@Parcelize
data class PlanCardWithPlans(
    @Embedded val planCardData: PlanCardData,
    @Relation(
        parentColumn = "id",
        entityColumn = "planCardDataId"
    )
    val listPlan: List<Plan>
):Parcelable

@Parcelize
data class ItineraryWithPlanCards(
    @Embedded val itinerary: Itinerary,
    @Relation (
        entity = PlanCardData::class,
        parentColumn = "id",
        entityColumn = "itineraryId"
    )
    val listPlanCard: List<PlanCardWithPlans>

):Parcelable


val categoryPlan = listOf(
    PlanCategory("On The Way", R.drawable.caricon),
    PlanCategory("Rest/Sleep", R.drawable.bedicon),
    PlanCategory("Eat", R.drawable.foodicon),
    PlanCategory("Play", R.drawable.playicon),
    PlanCategory("Destination", R.drawable.placeicon),
    )

@Parcelize
data class PlanCategory(
    var name: String,
    var icon:  Int,
): Parcelable

object allAboutList {
    var listPlan: MutableList<Plan> = mutableListOf()
}
