package com.jer.banyumastourismapp.presentation

import com.jer.banyumastourismapp.presentation.detaildestination.DetailDestination
import com.jer.banyumastourismapp.domain.model.City
import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.domain.model.Facility
import com.jer.banyumastourismapp.domain.model.Itinerary
import com.jer.banyumastourismapp.domain.model.Plan
import com.jer.banyumastourismapp.presentation.sosmed.Sosmed

val listCity = listOf(
    City("Yogyakarta", ""),
    City("Bandung", ""),
    City("Jakarta", ""),
    City("Surabaya", ""),
    City("Semarang", ""),
)


//val listCardPlanDummy = listOf(emptyList(), emptyList(), listPlan3, listPlan4, listPlan5)
//val listCardPlanDummy = listOf(emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), listPlan)

val listFacilityDummy: List<Facility> = listOf(
    Facility("Waterfall"),
    Facility("Restaurant"),
    Facility("Villa"),
    Facility("Waterfall"),
    Facility("Waterfall"),
    Facility("Waterfall"),
)
val detailDestination = DetailDestination (
    title = "Curug Bayan",
    location = "Desa Ketengger, Baturraden, Banyumas",
    description = "Curug Bayan merupakan salah satu objek wisata yang berada di desa Ketengger, kecamatan Baturaden, kabupaten Banyumas. Curug Bayan memiliki keunikan tersendiri karena terletak dibawah lereng gunung slamet dan memiliki suasana yang sejuk dan dingin",
    price = 150000,
    timeOpen = "08.00 - 17.00",
    rating = 5f,
    ig = "curug_bayan",
    facility = listFacilityDummy,
    route = "Curug Bayan merupakan salah satu objek wisata yang berada di desa Ketengger, kecamatan Baturaden, kabupaten Banyumas. Curug Bayan memiliki keunikan tersendiri karena terletak dibawah lereng gunung slamet dan memiliki suasana yang sejuk dan dingin",
    accessibility = listOf(
        "Jalan sempit",
        "Jalan naik turun",
        "Bisa naik motor dan mobil",
        "Banyak kulineran",
        "Rute mudah ditempuh"
    ),
    tips = listOf(
        "Pakai baju ternyaman",
        "Bawa Jas hujan",
        "Bawa Tenda",
        "Bawa Kamera"
    )
)


val itinerary = Itinerary (
    daysCount = 5,
    title = "Seru-seruan di Jawa Tengah",
    description = "Libur semesteran 7 hari full di jawa tengah bareng sobat jawir sekontrakan. Bakal berkunjung ke 4 kota dengan 10 destinasi.",
    totalMembers = "5",
    notes = "Libur semesteran 7 hari full di jawa tengah bareng sobat jawir sekontrakan. Bakal berkunjung ke 4 kota dengan 10 destinasi.",

)
//
//val plan = Plan(
//    id = 0,
//    planCardDataId = 0,
//    category = 0,
//    title = "Kumpul Sejenak",
////    city = listCity,
//    time = "12.00",
//    cost = 0,
//
//)



//SOSMED
val listSosmed = listOf(
    Sosmed(
        "Huru Hara 5 Hari di Bali",
        "Fajar",
        "Cerita pengalaman liburan di Bali ramai ramai selama 5 hari, ada seru dan mumet nya"
    ),
    Sosmed(
        "Huru Hara 5 Hari di Bali",
        "Fajar",
        "Cerita pengalaman liburan di Bali ramai ramai selama 5 hari, ada seru dan mumet nya"
    ),
    Sosmed(
        "Huru Hara 5 Hari di Bali",
        "Fajar",
        "Cerita pengalaman liburan di Bali ramai ramai selama 5 hari, ada seru dan mumet nya"
    ),
    Sosmed(
        "Huru Hara 5 Hari di Bali",
        "Fajar",
        "Cerita pengalaman liburan di Bali ramai ramai selama 5 hari, ada seru dan mumet nya"
    )

)



//DESTINATION

val listDestination = listOf(
    Destination(
        id = 0,
        title = "Raja Ampat",

        description = "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
        location = "Raja Ampat, Papua Barat",
        cost = 3500000,
    ),

)