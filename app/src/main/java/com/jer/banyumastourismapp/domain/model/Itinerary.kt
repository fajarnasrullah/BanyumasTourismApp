package com.jer.banyumastourismapp.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
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
)],
    indices = [Index(value = ["uid"])]
    )
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
    )],
    indices = [Index(value = ["itineraryId"])]
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
)],
    indices = [Index(value = ["planCardDataId"])]
    )
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
    PlanCategory("Pray", R.drawable.masjidicon),
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

val urlPictures = listOf(
    "https://eventdaerah.kemenparekraf.go.id/storage/app/uploads/public/669/0cd/153/6690cd153a162349133709.jpg",
    "https://asset.kompas.com/crops/latfHWvJG4gfdlgQn5mC4rUHHe0=/0x0:750x500/1200x800/data/photo/2022/02/08/6201d8090b7c8.jpg",
    "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEh7mkQaTFMxTnOVsNdoZYuYnV3N2m99uDL8e9Bmd4AnNjjS7BgIFPQ_-kZ8AgUeopM9xY-Sv-gqgDGni2SYsiGsfE9rMzJHBURF5ZGAq7gl1DoLgP_2meVPnhn4OYCGcxtQjZYOBIlEa9U/s640/infobacktravel.blogspot.com-panduan-wisata-baturaden.jpg",
    "https://assets.promediateknologi.id/crop/0x0:0x0/750x500/webp/photo/2023/01/17/3087847889.jpg",
    "https://camerawisata.com/wp-content/uploads/2018/03/Telaga-Sunyi-Baturraden.png",
    "https://suarabanyumas.com/wp-content/uploads/2023/12/Desa-Ketenger-Baturraden.jpg",
    "https://travelspromo.com/wp-content/uploads/2019/05/area-kebun-raya-baturraden-Avuk-Jimmy.jpg",
    "https://cdn.wisata.app/diary/46a7b2f6-aea6-457b-b514-3c0e8a1c8c79_sm.jpg",
    "https://assetd.kompas.id/kLMSW6oxTMCrCzjA_hWbgPD0qRo=/1024x683/filters:watermark(https://cdn-content.kompas.id/umum/kompas_main_logo.png,-16p,-13p,0)/https%3A%2F%2Fasset.kgnewsroom.com%2Fphoto%2Fpre%2F2022%2F12%2F14%2F047b6bd8-7a4b-4bb6-bf7c-b846712858f8_jpg.jpg",
    "https://assets.promediateknologi.id/crop/0x0:0x0/750x500/webp/photo/2022/10/13/2192877490.jpg",
    "https://superlive.id/storage/superadventure/2020/12/09/0a0eba2eb1e4.jpg",
    "https://muncak.id/storage/0d0eef11-6742-4044-9851-f9fb20707007/rute-gallery-675b2bdb5e94e6.png",
    "https://www.explore-grandest.com/wp-content/uploads/2021/11/paintball-0x870.jpg"

)
