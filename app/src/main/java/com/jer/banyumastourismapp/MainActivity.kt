package com.jer.banyumastourismapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.libraries.places.api.Places
import com.jer.banyumastourismapp.domain.model.City
import com.jer.banyumastourismapp.domain.model.Plan
import com.jer.banyumastourismapp.domain.model.PlanCategory
import com.jer.banyumastourismapp.domain.model.categoryPlan
import com.jer.banyumastourismapp.presentation.maps.MapsUtils
import com.jer.banyumastourismapp.presentation.navgraph.NavGraph
import com.jer.banyumastourismapp.presentation.navgraph.Route
import com.jer.banyumastourismapp.ui.theme.BanyumasTourismAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiKey = MapsUtils.getApiKeyFromManifest(this)
        if (!Places.isInitialized() && apiKey != null) {
            Places.initialize(applicationContext, apiKey)
        }

//        enableEdgeToEdge()
        setContent {

//            val listFacilityDummy: List<Facility> = listOf(
//                Facility("Waterfall"),
//                Facility("Restaurant"),
//                Facility("Villa"),
//                Facility("Waterfall"),
//                Facility("Waterfall"),
//                Facility("Waterfall"),
//            )

            val categoryPlanList =
                listOf (
                    listOf(
                        PlanCategory("Resto", R.drawable.foodicon),
                        PlanCategory("Resto", R.drawable.foodicon),
                        PlanCategory("Destination", R.drawable.placeicon),
                        PlanCategory("On The Way", R.drawable.caricon),
//                        PlanCategory("Hotel", R.drawable.bedicon),
                    ),
//                    categoryPlan,
                    categoryPlan,
                    categoryPlan,
                    categoryPlan,
                    categoryPlan,
//                    categoryPlan
                )
            val listCity = listOf(
                City("Yogyakarta", ""),
                City("Bandung", ""),
                City("Jakarta", ""),
                City("Surabaya", ""),
                City("Semarang", ""),
            )



            BanyumasTourismAppTheme {
//                Scaffold(
//                    modifier = Modifier
//                        .fillMaxSize()
//
//                ) { innerPadding ->

//                    ItineraryScreen(
//                        modifier = Modifier.padding(innerPadding),
//                        user = User(name = "Fajar Nasrullah"),
//                        itinerary = Itinerary (
//                            daysCount = 5,
//                            title = "Seru-seruan di Jawa Tengah",
//                            description = "Libur semesteran 7 hari full di jawa tengah bareng sobat jawir sekontrakan. Bakal berkunjung ke 4 kota dengan 10 destinasi.",
//                            membersCount = 5,
//                            cityGoals = listCity,
//                            notes = "Libur semesteran 7 hari full di jawa tengah bareng sobat jawir sekontrakan. Bakal berkunjung ke 4 kota dengan 10 destinasi.",
//                            listCardPlan = listCardPlan
//
//                        ),
//                        plan = Plan(
//                            category = 0,
////                            PlanCategory("On The Way", R.drawable.caricon),
//                            title = "Kumpul Sejenak",
//                            city = listCity,
//                            time = "12.00",
//                            cost = 0
//                        ),
////            listPlan = listPlan1,
//                        onClick = {}
//
//
//                    )

//                    DetailDestinationScreen(
//                        modifier = Modifier.padding(innerPadding),
//                        detailDestination =
//                    DetailDestination(
//                        title = "Curug Bayan",
//                        location = "Desa Ketengger, Baturraden, Banyumas",
//                        description = "Curug Bayan merupakan salah satu objek wisata yang berada di desa Ketengger, kecamatan Baturaden, kabupaten Banyumas. Curug Bayan memiliki keunikan tersendiri karena terletak dibawah lereng gunung slamet dan memiliki suasana yang sejuk dan dingin",
//                        cost = 150000,
//                        timeOpen = "08.00 - 17.00",
//                        rating = 5f,
//                        ig = "curug_bayan",
//                        facility = listFacilityDummy,
//                        route = "Curug Bayan merupakan salah satu objek wisata yang berada di desa Ketengger, kecamatan Baturaden, kabupaten Banyumas. Curug Bayan memiliki keunikan tersendiri karena terletak dibawah lereng gunung slamet dan memiliki suasana yang sejuk dan dingin",
//                        accessibility = listOf(
//                            "Jalan sempit",
//                            "Jalan naik turun",
//                            "Bisa naik motor dan mobil",
//                            "Banyak kulineran",
//                            "Rute mudah ditempuh"
//                        ),
//                        tips = listOf(
//                            "Pakai baju ternyaman",
//                            "Bawa Jas hujan",
//                            "Bawa Tenda",
//                            "Bawa Kamera"
//                        )
//
//                    )
//                    )

//                    DestinationListScreen(
//                        modifier = Modifier.padding(innerPadding),
//                        user = User("Fajar"),
//                        destination = listOf(
//                            Destination(
//                                "Raja Ampat",
//                                "Raja Ampat, Papua Barat",
//                                "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
//                                3500000,
//                            ),
//                            Destination(
//                                "Raja Ampat",
//                                "Raja Ampat, Papua Barat",
//                                "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
//                                3500000,
//                            ),
//                            Destination(
//                                "Raja Ampat",
//                                "Raja Ampat, Papua Barat",
//                                "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
//                                3500000,
//                            ),
//                            Destination(
//                                "Raja Ampat",
//                                "Raja Ampat, Papua Barat",
//                                "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
//                                3500000,
//                            ),
//                            Destination(
//                                "Raja Ampat",
//                                "Raja Ampat, Papua Barat",
//                                "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
//                                3500000,
//                            ),
//                            Destination(
//                                "Raja Ampat",
//                                "Raja Ampat, Papua Barat",
//                                "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
//                                3500000,
//                            ),
//                            Destination(
//                                "Raja Ampat",
//                                "Raja Ampat, Papua Barat",
//                                "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
//                                3500000,
//                            ),
//                        ),
//                        navigateToDetail = {}
//                    )

//                    HomeScreen(
//                        user = User("Fajar","https://t4.ftcdn.net/jpg/02/14/74/61/360_F_214746128_31JkeaP6rU0NzzzdFC4khGkmqc8noe6h.jpg"), destination = listOf(
//                        Destination(
//                            "Raja Ampat",
//                            "Raja Ampat, Papua Barat",
//                            cost = 3500000,
//                        ),
//                        Destination(
//                            "Raja Ampat",
//                            "Raja Ampat, Papua Barat",
//                            cost = 3500000,
//                        ),
//                        Destination(
//                            "Raja Ampat",
//                            "Raja Ampat, Papua Barat",
//                            cost = 3500000,
//                        ),
//
//                        ),
//                        modifier = Modifier
//                            .padding(innerPadding)
//                    )

//                Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer )) {

//                    val startDestination = viewModel.startDestination
                    val startDestination = Route.AppStartNavigation.route
                    NavGraph(startDestination = startDestination)
//                }
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )

//                } ini batas Scaffold
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BanyumasTourismAppTheme {
        Greeting("Android")
    }
}