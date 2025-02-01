package com.jer.banyumastourismapp.presentation.detaildestination

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.common.verySmallIcon
import com.jer.banyumastourismapp.presentation.component.AppBarCustom
import com.jer.banyumastourismapp.presentation.component.BottomBarDetail
import com.jer.banyumastourismapp.ui.theme.BanyumasTourismAppTheme
import kotlin.math.exp

@Composable
fun DetailDestinationScreen(
    modifier: Modifier = Modifier,
    detailDestination: DetailDestination
) {

    val scrollState = rememberScrollState()

    Scaffold (
        bottomBar = {
            BottomBarDetail(
                detailDestination = detailDestination,
                textButton = "Book",
                onClick = {} )
                    },
        modifier = Modifier
    ) { innerPadding ->

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {

            val (appBar, image, placeCard, content,) = createRefs()


            if (detailDestination.imageUrl == null) {
                Image(
                    painter = painterResource(id = R.drawable.viewdefault),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp)
                        .constrainAs(image) {
                            top.linkTo(parent.top)
                        }
                )
            } else {
                AsyncImage(
                    model = detailDestination.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp)
                        .constrainAs(image) {
                            top.linkTo(parent.top)
                        }
                )
            }
            val cornerSize = 32.dp
            Surface (
                color = MaterialTheme.colorScheme.background,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .clip(RoundedCornerShape(cornerSize))
                    .constrainAs(content) {
                        top.linkTo(image.bottom, margin = -cornerSize)
                    }

            ) {
                PlaceDetailContent(
                    detailDestination = detailDestination,
                )
            }

            PlaceCard(
                detailDestination = detailDestination,
                onClickLocation = {},
                modifier = Modifier
                    .constrainAs(placeCard) {
                        centerAround(content.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(horizontal = 30.dp)
            )

            AppBarCustom(
                navigateBack = {  },
                actionIcon = painterResource(id = R.drawable.bookmarkbordericon),
                action = {},
                modifier = Modifier
                    .constrainAs(appBar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(30.dp)

            )


        }
    }
}




@Composable
fun RouteAndAccessibility(modifier: Modifier = Modifier, detailDestination: DetailDestination) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Route & Accessibility",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(15.dp))

        CardExpanded(
            icon = painterResource(id = R.drawable.caricon),
            title = "Private Vehicle",
            content = detailDestination.route
        )

        Spacer(modifier = Modifier.height(15.dp))

        CardExpanded(
            icon = painterResource(id = R.drawable.personicon),
            title = "Accessibility",
            content = detailDestination.accessibility.toString()
        )

        Spacer(modifier = Modifier.height(15.dp))

        CardExpanded(
            icon = painterResource(id = R.drawable.checkicon),
            title = "Tips",
            content = detailDestination.accessibility.toString()
        )
        
    }
}

@Composable
fun CardExpanded(
    modifier: Modifier = Modifier,
    icon: Painter,
    title: String,
    content: String
) {

    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(targetValue = if (expandedState) 180f else 0f)


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
//        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium,
        onClick = { expandedState = !expandedState }

    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row  {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(verySmallIcon)
                    )
                    
                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = title,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                IconButton(
                    onClick = { expandedState = !expandedState},
                    modifier = Modifier
                        .size(verySmallIcon)
                        .rotate(rotationState)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }

            }

            if (expandedState) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = content,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Justify
                )

            }

        }
    }
}

@Composable
fun PlaceDetailContent(
    modifier: Modifier = Modifier,
    detailDestination: DetailDestination
    ) {

    Column (
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(30.dp)
    ) {

        Spacer(modifier = Modifier.height(55.dp))

        Text(
            text = detailDestination.description,
            fontSize = 12.sp,
            textAlign = TextAlign.Justify,
            color = MaterialTheme.colorScheme.onBackground
        )
        
        Spacer(modifier = Modifier.height(30.dp))

        FacilityContent(detailDestination = detailDestination)

        Spacer(modifier = Modifier.height(30.dp))

        RouteAndAccessibility(detailDestination = detailDestination)

    }

}

@Composable
fun FacilityContent(modifier: Modifier = Modifier, detailDestination: DetailDestination) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = "Facility",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ItemFacility1(detailDestination = detailDestination)
        }

        Spacer(modifier = Modifier.height(5.dp))

        if (detailDestination.facility.size > 3) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ItemFacility2(detailDestination = detailDestination)
            }
        }
            
            
        
        
    }
}

@Composable
fun ItemFacility1(modifier: Modifier = Modifier, detailDestination: DetailDestination) {
    detailDestination.facility.take(3).forEach{ item ->
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (item.icon == null) {
                Image(
                    painterResource(id = R.drawable.waterfallicon),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            } else {
                AsyncImage(
                    model = item.icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                )
            }


            Spacer(modifier = Modifier.width(5.dp))

            Text(text = item.text, fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground)

        }
    }
    

}

@Composable
fun ItemFacility2(modifier: Modifier = Modifier, detailDestination: DetailDestination) {
    detailDestination.facility.takeLast(3).forEach{ item ->
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically

            ) {

            if (item.icon == null) {
                Image(
                    painterResource(id = R.drawable.waterfallicon),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            } else {
                AsyncImage(
                    model = item.icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                )
            }


            Spacer(modifier = Modifier.width(5.dp))

            Text(text = item.text, fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground)

        }
    }
}

@Composable
fun PlaceCard(
    modifier: Modifier = Modifier,
    detailDestination: DetailDestination,
    onClickLocation: () -> Unit
) {
    
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.onBackground,
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(15.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = detailDestination.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    modifier = Modifier.width(200.dp)
                )

                Row (
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .width(100.dp)
                        .clickable { onClickLocation() }
                ){
                   Icon(
                       imageVector = Icons.Default.LocationOn,
                       contentDescription = null,
                       tint = MaterialTheme.colorScheme.primaryContainer,
                       modifier = Modifier.size(verySmallIcon)
                   )
                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = detailDestination.location,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.outline,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2

                    )
                }

            }

            Spacer(modifier = Modifier.height(15.dp))

            Surface (
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.surfaceVariant,

            ) {
                Row (
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    ContentPlaceCard(
                        title = "Open At",
                        icon = painterResource(id = R.drawable.timeicon),
                        content = detailDestination.timeOpen,
                    )

                    Divider(
                        modifier = Modifier
                            .width(1.dp)
                            .fillMaxHeight(),
                        color = MaterialTheme.colorScheme.outline
                    )

                    ContentPlaceCard(
                        title = "Rating",
                        icon = painterResource(id = R.drawable.staricon),
                        content = detailDestination.rating.toString(),
                    )

                    Divider(
                        modifier = Modifier
                            .width(1.dp)
                            .fillMaxHeight(),
                        color = MaterialTheme.colorScheme.outline
                    )

                    ContentPlaceCard(
                        title = "Instagram",
                        icon = painterResource(id = R.drawable.instagramicon),
                        content = detailDestination.ig,
                    )

                }
            }
        }

    }
}

@Composable
fun ContentPlaceCard(
    modifier: Modifier = Modifier,
    title: String,
    icon: Painter,
    content: String
) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {

            Image(
                painter = icon,
                contentDescription = null,
                modifier= Modifier.size(verySmallIcon),
            )
            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = title,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = content,
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold
        )
    }

}

data class DetailDestination(
    val title: String,
    val imageUrl: String? = null,
    val location: String,
    val description: String,
    val cost: Int,
    val timeOpen: String,
    val rating: Float,
    val ig: String,
    val facility: List<Facility>,
    val route: String,
    val accessibility: List<String>,
    val tips: List<String>
)



data class Facility(
    val text: String,
    val icon: String? = null,
)

data class Route(
    val transport: String,
    val desc: String
)


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PrevDetailDestination() {

    val listFacilityDummy: List<Facility> = listOf(
        Facility("Waterfall"),
        Facility("Restaurant"),
        Facility("Villa"),
        Facility("Waterfall"),
        Facility("Waterfall"),
        Facility("Waterfall"),
    )
    BanyumasTourismAppTheme {
        DetailDestinationScreen(detailDestination =
            DetailDestination(
                title = "Curug Bayan",
                location = "Desa Ketengger, Baturraden, Banyumas",
                description = "Curug Bayan merupakan salah satu objek wisata yang berada di desa Ketengger, kecamatan Baturaden, kabupaten Banyumas. Curug Bayan memiliki keunikan tersendiri karena terletak dibawah lereng gunung slamet dan memiliki suasana yang sejuk dan dingin",
                cost = 150000,
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
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PrevCardExpanded() {
    BanyumasTourismAppTheme {
        CardExpanded(
            icon = painterResource(id = R.drawable.caricon),
            title = "Private Vehicle",
            content = "Curug Bayan merupakan salah satu objek wisata yang berada di desa Ketengger, kecamatan Baturaden, kabupaten Banyumas. Curug Bayan memiliki keunikan tersendiri karena terletak dibawah lereng gunung slamet dan memiliki suasana yang sejuk dan dingin."
        )
    }
}