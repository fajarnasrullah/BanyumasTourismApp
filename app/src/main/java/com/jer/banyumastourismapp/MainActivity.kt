package com.jer.banyumastourismapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jer.banyumastourismapp.presentation.component.Destination
import com.jer.banyumastourismapp.presentation.destination.DestinationListScreen
import com.jer.banyumastourismapp.presentation.home.HomeScreen
import com.jer.banyumastourismapp.presentation.home.UserDummy
import com.jer.banyumastourismapp.ui.theme.BanyumasTourismAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            BanyumasTourismAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    DestinationListScreen(
                        modifier = Modifier.padding(innerPadding),
                        userDummy = UserDummy("Fajar"),
                        destination = listOf(
                            Destination(
                                "Raja Ampat",
                                "Raja Ampat, Papua Barat",
                                "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
                                3500000,
                            ),
                            Destination(
                                "Raja Ampat",
                                "Raja Ampat, Papua Barat",
                                "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
                                3500000,
                            ),
                            Destination(
                                "Raja Ampat",
                                "Raja Ampat, Papua Barat",
                                "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
                                3500000,
                            ),
                            Destination(
                                "Raja Ampat",
                                "Raja Ampat, Papua Barat",
                                "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
                                3500000,
                            ),
                            Destination(
                                "Raja Ampat",
                                "Raja Ampat, Papua Barat",
                                "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
                                3500000,
                            ),
                            Destination(
                                "Raja Ampat",
                                "Raja Ampat, Papua Barat",
                                "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
                                3500000,
                            ),
                            Destination(
                                "Raja Ampat",
                                "Raja Ampat, Papua Barat",
                                "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
                                3500000,
                            ),
                        ),
                        onClick = {}
                    )

//                    HomeScreen(userDummy = UserDummy("Fajar","https://t4.ftcdn.net/jpg/02/14/74/61/360_F_214746128_31JkeaP6rU0NzzzdFC4khGkmqc8noe6h.jpg"), destination = listOf(
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
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
                }
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