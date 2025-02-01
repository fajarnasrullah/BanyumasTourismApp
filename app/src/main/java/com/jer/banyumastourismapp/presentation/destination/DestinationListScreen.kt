package com.jer.banyumastourismapp.presentation.destination

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.presentation.component.Category
import com.jer.banyumastourismapp.presentation.component.CategoryRow
import com.jer.banyumastourismapp.presentation.component.Destination
import com.jer.banyumastourismapp.presentation.component.DestinationCardLandscape
import com.jer.banyumastourismapp.presentation.component.SearchBarForAll
import com.jer.banyumastourismapp.presentation.home.ItineraryCard
import com.jer.banyumastourismapp.presentation.home.NotifTopButton
import com.jer.banyumastourismapp.presentation.home.UserDummy
import com.jer.banyumastourismapp.presentation.home.UserTopSection
import com.jer.banyumastourismapp.ui.theme.BanyumasTourismAppTheme

@Composable
fun DestinationListScreen(
    modifier: Modifier = Modifier,
    destination: List<Destination>,
    userDummy: UserDummy,
    onClick: () -> Unit
) {

    val scrollState = rememberScrollState()

    Scaffold (
        modifier = modifier
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
//                .fillMaxSize()
                .padding(innerPadding)
//                .verticalScroll(scrollState)
        ) {
            val (bg, searchbar, category, destinationList) = createRefs()

            Box(
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .constrainAs(bg) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            SearchBarForAll(
                hint = "Search Destination",
                modifier = Modifier
                    .constrainAs(searchbar) {
                        top.linkTo(parent.top)



                    }
                    .padding(30.dp)
            )

            CategoryRow(
                modifier = Modifier
                    .constrainAs(category) {
                        top.linkTo(bg.bottom)

                    }
                    .padding(top = 30.dp, bottom = 15.dp),

            )

            DestinationCardLandscapeColumn(
                destination = destination,
                onClick = {},
                modifier = Modifier
                    .constrainAs(destinationList) {
                        top.linkTo(category.bottom)

                    }
                    .fillMaxHeight()
            )
          
//





        }
    }



}

@Composable
fun DestinationCardLandscapeColumn(
    modifier: Modifier = Modifier,
    destination: List<Destination>,
    onClick: () -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues( start = 30.dp, end = 30.dp, bottom = 30.dp, top = 20.dp),

        modifier = modifier
    ) {
        items(destination.size) { index ->
            DestinationCardLandscape(
                destination = destination[index],
                onClick = { onClick() })
        }

    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewDestinationListScreen() {
    BanyumasTourismAppTheme {
        DestinationListScreen(
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
    }

}

