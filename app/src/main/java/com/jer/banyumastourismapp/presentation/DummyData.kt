package com.jer.banyumastourismapp.presentation

import com.jer.banyumastourismapp.presentation.detaildestination.DetailDestination
import com.jer.banyumastourismapp.presentation.detaildestination.Facility
import com.jer.banyumastourismapp.domain.model.City
import com.jer.banyumastourismapp.domain.model.Destination
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
val listPlan1 = listOf(
    Plan(
        category = 0 ,
//                    PlanCategory("On The Way", R.drawable.caricon),
        title = "Berangkat",
        city = listCity,
        time = "13.00",
        cost = 0
    ),

    Plan(
        category = 1,
//                    PlanCategory("Resto", R.drawable.foodicon),
        title = "Makan Heula",
        city = listCity,
        time = "20.00",
        cost = 0
    ),
    Plan(
        category = 3,
//                    PlanCategory("Destination", R.drawable.placeicon),
        title = "Sampai cuy",
        city = listCity,
        time = "21.00",
        cost = 0
    ),
    Plan(
        category = 1,
//                    PlanCategory("Hotel", R.drawable.bedicon),
        title = "Menginap di Hotel",
        city = listCity,
        time = "18.00",
        cost = 0
    ),

    )
val listPlan2 = listOf(
    Plan(
        category = 0,
//                    PlanCategory("On The Way", R.drawable.caricon),
        title = "Mangkat",
        city = listCity,
        time = "13.00",
        cost = 0
    ),


    Plan(
        category = 3,
//                    PlanCategory("Destination", R.drawable.placeicon),
        title = "sokin",
        city = listCity,
        time = "21.00",
        cost = 0
    ),

    Plan(
        category = 1,
//                    PlanCategory("Hotel", R.drawable.bedicon),
        title = "Rehat sejenak",
        city = listCity,
        time = "18.00",
        cost = 0
    ),

    Plan(
        category = 2,
//                    PlanCategory("Resto", R.drawable.foodicon),
        title = "Madang lurr",
        city = listCity,
        time = "20.00",
        cost = 0
    ),


    )
val listPlan3 = listOf(
    Plan(
        category = 0,
//                    PlanCategory("On The Way", R.drawable.caricon),
        title = "otw",
        city = listCity,
        time = "13.00",
        cost = 0
    ),
    Plan(
        category = 1,
//                    PlanCategory("Hotel", R.drawable.bedicon),
        title = "turu",
        city = listCity,
        time = "18.00",
        cost = 0
    ),
    Plan(
        category = 2,
//                    PlanCategory("Resto", R.drawable.foodicon),
        title = "mangan",
        city = listCity,
        time = "20.00",
        cost = 0
    ),
    Plan(
        category = 3,
//                    PlanCategory("Destination", R.drawable.placeicon),
        title = "sampai lokasi",
        city = listCity,
        time = "21.00",
        cost = 0
    ),
    Plan(
        category = 3,
//                    PlanCategory("Destination", R.drawable.placeicon),
        title = "sampai lokasi",
        city = listCity,
        time = "21.00",
        cost = 0
    ), Plan(
        category = 3,
//                    PlanCategory("Destination", R.drawable.placeicon),
        title = "sampai lokasi",
        city = listCity,
        time = "21.00",
        cost = 0
    )
)

val listPlan4 = listOf(
    Plan(
        category = 0,
//                    PlanCategory("On The Way", R.drawable.caricon),
        title = "otw",
        city = listCity,
        time = "13.00",
        cost = 0
    ),
    Plan(
        category = 1,
//                    PlanCategory("Hotel", R.drawable.bedicon),
        title = "turu",
        city = listCity,
        time = "18.00",
        cost = 0
    ),
    Plan(
        category = 2,
//                    PlanCategory("Resto", R.drawable.foodicon),
        title = "mangan",
        city = listCity,
        time = "20.00",
        cost = 0
    ),
    Plan(
        category = 2,
//                    PlanCategory("Destination", R.drawable.placeicon),
        title = "sampai lokasi",
        city = listCity,
        time = "21.00",
        cost = 0
    )
)

val listPlan5 = listOf(
    Plan(
        category = 0,
//                    PlanCategory("On The Way", R.drawable.caricon),
        title = "otw",
        city = listCity,
        time = "13.00",
        cost = 0
    ),
    Plan(
        category = 1,
//                    PlanCategory("Hotel", R.drawable.bedicon),
        title = "turu",
        city = listCity,
        time = "18.00",
        cost = 0
    ),
    Plan(
        category = 2,
//                    PlanCategory("Resto", R.drawable.foodicon),
        title = "mangan",
        city = listCity,
        time = "20.00",
        cost = 0
    ),
    Plan(
        category = 3,
//                    PlanCategory("Destination", R.drawable.placeicon),
        title = "sampai lokasi",
        city = listCity,
        time = "21.00",
        cost = 0
    ),
    Plan(
        category = 0,
//                    PlanCategory("On The Way", R.drawable.caricon),
        title = "otw",
        city = listCity,
        time = "13.00",
        cost = 0
    ),
    Plan(
        category = 1,
//                    PlanCategory("Hotel", R.drawable.bedicon),
        title = "turu",
        city = listCity,
        time = "18.00",
        cost = 0
    ),
    Plan(
        category = 2,
//                    PlanCategory("Resto", R.drawable.foodicon),
        title = "mangan",
        city = listCity,
        time = "20.00",
        cost = 0
    ),
    Plan(
        category = 3,
//                    PlanCategory("Destination", R.drawable.placeicon),
        title = "sampai lokasi",
        city = listCity,
        time = "21.00",
        cost = 0
    ), Plan(
        category = 0,
//                    PlanCategory("On The Way", R.drawable.caricon),
        title = "otw",
        city = listCity,
        time = "13.00",
        cost = 0
    ),
    Plan(
        category = 1,
//                    PlanCategory("Hotel", R.drawable.bedicon),
        title = "turu",
        city = listCity,
        time = "18.00",
        cost = 0
    ),
    Plan(
        category = 2,
//                    PlanCategory("Resto", R.drawable.foodicon),
        title = "mangan",
        city = listCity,
        time = "20.00",
        cost = 0
    ),
    Plan(
        category = 3,
//                    PlanCategory("Destination", R.drawable.placeicon),
        title = "sampai lokasi",
        city = listCity,
        time = "21.00",
        cost = 0
    )
)

val listCardPlan = listOf(emptyList(), emptyList(), listPlan3, listPlan4, listPlan5)

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
    membersCount = 5,
    cityGoals = listCity,
    notes = "Libur semesteran 7 hari full di jawa tengah bareng sobat jawir sekontrakan. Bakal berkunjung ke 4 kota dengan 10 destinasi.",
    listCardPlan = listCardPlan

)

val plan = Plan(
    category = 0,
    title = "Kumpul Sejenak",
    city = listCity,
    time = "12.00",
    cost = 0
)



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
        0,
        "Raja Ampat",
        "Raja Ampat, Papua Barat",
        "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
        cost = 3500000,
    ),
    Destination(
        1,
        "Raja Ampat",
        "Raja Ampat, Papua Barat",
        "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
        cost = 3500000,
    ),
    Destination(
        2,
        "Raja Ampat",
        "Raja Ampat, Papua Barat",
        "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
        cost = 3500000,
    ),
    Destination(
        3,
        "Raja Ampat",
        "Raja Ampat, Papua Barat",
        "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
        cost = 3500000,
    ),
    Destination(
        4,
        "Raja Ampat",
        "Raja Ampat, Papua Barat",
        "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
        cost = 3500000,
    ),
    Destination(
        5,
        "Raja Ampat",
        "Raja Ampat, Papua Barat",
        "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
        cost = 3500000,
    ),
    Destination(
        6,
        "Raja Ampat",
        "Raja Ampat, Papua Barat",
        "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
        cost = 3500000,
    ),
    Destination(
        7,
        "Raja Ampat",
        "Raja Ampat, Papua Barat",
        "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
        cost = 3500000,
    ),
    Destination(
        8,
        "Raja Ampat",
        "Raja Ampat, Papua Barat",
        "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
        cost = 3500000,
    ),
)