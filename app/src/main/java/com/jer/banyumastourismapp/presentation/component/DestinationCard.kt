package com.jer.banyumastourismapp.presentation.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType.Companion.Sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.core.verySmallIcon
import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.ui.theme.BanyumasTourismAppTheme
import com.jer.banyumastourismapp.ui.theme.OrangeNice


@Composable
fun DestinationCardLandscape(
    modifier: Modifier = Modifier,
    destination: Destination,
    buttonVisibility: Boolean,
    onClick: (Destination) -> Unit,
    navigateToOrders: (() -> Unit)? = null
) {


    Card(
        modifier = modifier
            .height(if (buttonVisibility) 160.dp else 130.dp)
            .clickable { onClick(destination) },
        shape = ShapeDefaults.Large,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 10.dp),

            ) {
            if (destination.imageUrl == "") {
                Image(
                    painter = painterResource(id = R.drawable.picturedummy),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.large)
                        .width(150.dp)
                        .fillMaxHeight()
                )
            } else {
                AsyncImage(
                    model = destination.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.large)
                        .width(150.dp)
                        .height(150.dp)
                )
            }
            Spacer(modifier = Modifier.width(15.dp))

            Column (
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxHeight()
            ) {

                Column {

                    Text(
                        text = destination.title,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )


                    if (!buttonVisibility){
                        Text(
                            text = destination.location,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.outline,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = destination.description ?: "",
                        fontSize = 8.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = if (buttonVisibility) 2 else 3,
                        overflow = TextOverflow.Ellipsis
                    )

                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()

                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.priceicon),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(verySmallIcon)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Rp. " + destination.cost.toString(),
                            fontSize = TextUnit(10f, Sp),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = OrangeNice,
                            modifier = Modifier.size(verySmallIcon)
                        )

                        Text(
                            text = "5.0",
                            fontSize = TextUnit(10f, Sp),
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                if (buttonVisibility) {
                    Button(
                        onClick = {
                            if (navigateToOrders != null) {
                                navigateToOrders()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)

                    ) {
                        Text(
                            text = "Book",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                }

            }


        }
    }

}


@Composable
fun DestinationCardPotrait(
    modifier: Modifier = Modifier,
    destination: Destination,
    onClick: (Destination) -> Unit
) {

    Card(
        modifier = Modifier
            .height(250.dp)
            .width(174.dp),
        onClick = { onClick(destination) },
        shape = ShapeDefaults.Large,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(all = 10.dp)
                .fillMaxHeight(),

        ) {
            Column(
            ){
                if (destination.imageUrl == "") {
                    Image(
                        painter = painterResource(id = R.drawable.picturedummy),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.large)
                            .size(158.dp)
                    )
                } else {
                    AsyncImage(
                        model = destination.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.large)
                            .size(158.dp)
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))

                Column() {
                    Text(
                        text = destination.title,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )


                    Text(
                        text = destination.location,
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.outline,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis

                    )
                }
            }


            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()

            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.priceicon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(verySmallIcon)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Rp. " + destination.cost.toString(),
                        fontSize = TextUnit(10f, Sp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = OrangeNice,
                        modifier = Modifier.size(verySmallIcon)
                    )

                    Text(
                        text = "5.0",
                        fontSize = TextUnit(10f, Sp),
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }

}





//@Preview
//@Composable
//private fun PreviewDestinationCardRow() {
//    BanyumasTourismAppTheme {
//        DestinationCardStandRow(destination = listDestination,
//            onClick = {}
//        )
//    }
//}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewDestinationCard() {

    BanyumasTourismAppTheme {
        DestinationCardPotrait(destination = Destination(
            0,
            "Raja Ampat",
            "Raja Ampat, Papua Barat",
            location = "jalan jalanan afjicbfwefuiwebfwneiufbwenfiouwebfnweufknwiuvwsnfuwiefhnweiu",
            cost = 3500000,

        ),
            onClick = {}
        )
    }

}

//@Preview(showBackground = true)
//@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
//@Composable
//private fun PreviewDestinationCardLandscape() {
//
//    BanyumasTourismAppTheme {
//        DestinationCardLandscape(
//            destination = Destination(
//                0,
//                "Raja Ampat",
//                "Raja Ampat, Papua Barat",
//                "3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.3 Air  terjun (salah satu nya 20 meter), deck bentuk hati, rumah makan, gazebo, camping ground.",
//                3500000,
//            ),
//            buttonVisibility = true,
//            onClick = {}
//        )
//    }
//
//}