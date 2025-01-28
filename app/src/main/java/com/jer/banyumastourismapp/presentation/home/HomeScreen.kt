package com.jer.banyumastourismapp.presentation.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.common.smallText
import com.jer.banyumastourismapp.common.verySmallIcon
import com.jer.banyumastourismapp.ui.theme.BanyumasTourismAppTheme
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    userDummy: UserDummy,
    categories: List<Category>
) {

    val scrollState = rememberScrollState()

    Scaffold (
//        containerColor = Color(0xFF0EA8B9),
        modifier = modifier,
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)

        ) {

            val (
                userAndNotif,
                itinerary,
                category1,
                category2,
                subTitle,
                list,
            ) = createRefs()

            Row (

                modifier = Modifier
                    .constrainAs(userAndNotif) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .padding(all = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                UserTopSection( userDummy = userDummy)

                NotifTopButton()
                
            }
            ItineraryCard(
                userDummy = userDummy,
                modifier = Modifier
                    .constrainAs(itinerary) {
                        top.linkTo(userAndNotif.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(start = 30.dp, end = 30.dp, bottom = 30.dp)

            )

            CategoryRow(
                modifier = Modifier
                    .constrainAs(category1) {
                        top.linkTo(itinerary.bottom)
                    }
                    .padding(bottom = 5.dp),
                categories = listOf(
                    Category("Mountain", painterResource(id = R.drawable.mountainicon)),
                    Category("Beach", painterResource(id = R.drawable.beachicon)),
                    Category("Waterfall", painterResource(id = R.drawable.waterfallicon)),
                    Category("Temple", painterResource(id = R.drawable.tampleiconsvg)),
                    )
            )

            CategoryRow(
                modifier = Modifier.constrainAs(category2) {
                    top.linkTo(category1.bottom)
                }
                    .padding(vertical = 5.dp)
                ,
                categories = listOf(
                    Category("Beach", painterResource(id = R.drawable.beachicon)),
                    Category("Temple", painterResource(id = R.drawable.tampleiconsvg)),
                    Category("Mountain", painterResource(id = R.drawable.mountainicon)),
                    Category("Waterfall", painterResource(id = R.drawable.waterfallicon)),
//                    Category("Bandung"),
//                    Category("Banyumas"),
//                    Category("Wonosobo"),
//                    Category("Lombok"),
//                    Category("Bali")
                )
            )


        }
    }

}

data class Category(
    val text: String,
    val icon: Painter? = null
)



@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    text: String,
    icon: Painter? = null,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    Card (
        modifier = Modifier
            .height(37.dp)
            .clickable { onClick() },
        shape = CircleShape,

        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)  MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.outlineVariant ,
            contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onBackground
        ),

        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp,)

    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(all = 5.dp)
        ) {

            if ( icon != null) {

                Surface (
                    shape = CircleShape,
                    color = Color.White,
                ) {

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(29.dp),
                    ) {
                        Image(
                            painter = icon,
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(verySmallIcon)
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.width(5.dp))

            Text (
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                )

            Spacer(modifier = Modifier.width(5.dp))
        }

    }
}

@Composable
fun CategoryRow(
    modifier: Modifier = Modifier,
    categories: List<Category>
) {

    val listState = rememberLazyListState()
    val selectedIndex = remember { mutableStateOf(-1) }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            listState.animateScrollToItem(index = (listState.firstVisibleItemIndex + 1) % categories.size)
            delay(2000)
        }
        
    }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(categories) { index, category ->
            CategoryCard(
                text = category.text,
                icon = category.icon,
                isSelected = selectedIndex.value == index,
                onClick = {selectedIndex.value = index}
            )
        }
    }
}

@Composable
fun ItineraryCard(modifier: Modifier = Modifier, userDummy: UserDummy) {
    Card(

        shape = MaterialTheme.shapes.large,
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary,
            contentColor = Color.Black,
            disabledContainerColor = Color.Black,
            disabledContentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier

    ) {
        Column(
            verticalArrangement = Arrangement.Center,

        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, top = 5.dp)
            ) {
                Text(
                    text = "Your Itinerary",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                IconButton(
                    onClick = { /*TODO*/ },
                    content = {
                        Icon(
                            modifier = Modifier.size(verySmallIcon),
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {

                if (userDummy.photoUrl == null) {
                    Image(
                        painter = painterResource(id = R.drawable.picturedummy),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(102.dp)
                            .clip(MaterialTheme.shapes.large)
                    )
                } else {
                    AsyncImage(
                        model = userDummy.photoUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(102.dp)
                            .clip(MaterialTheme.shapes.large)
                    )
                }

                Spacer(modifier = Modifier.width(15.dp))

                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ){
                    Text(
                        text = "Seru-seruan di Jawa Tengah",
                        style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(15.dp))


                    Text(
                        text = "Libur semesteran 7 hari full di jawa tengah bareng sobat jawir sekontrakan. Bakal berkunjung ke 4 kota dengan 10 destinasi.",
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        color = MaterialTheme.colorScheme.onBackground,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 4
                    )
                }
            }

//            Spacer(modifier = Modifier.height(15.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
            ) {
                IconAndText(painter = painterResource(id = R.drawable.moneyicon), text = "Rp. 100.000")
                IconAndText(painter = painterResource(id = R.drawable.destinationicon), text = "8")
                IconAndText(painter = painterResource(id = R.drawable.peopleicon), text = "12")
                IconAndText(painter = painterResource(id = R.drawable.calendaricon), text = "22 - 27 August")
            }
        }

    }
}


@Composable
fun IconAndText(modifier: Modifier = Modifier, painter: Painter, text: String) {
    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = text,
            fontSize = TextUnit(10f, TextUnitType.Sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall
        )
    }

}

@Composable
fun UserTopSection(modifier: Modifier = Modifier, userDummy: UserDummy) {
    Card (
        modifier = Modifier
            .size(width = 270.dp, height = 50.dp)
            .clickable {

            },
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            
        ),
        border = BorderStroke(2.dp, Color(0xFFDEDEDE)),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp,)

    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(all = 5.dp)
        ) {

            if (userDummy.photoUrl == null) {

                Image(
                    painter = painterResource(id = R.drawable.userimage),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(41.dp)
                        .clip(CircleShape)
                )
            } else {
                AsyncImage(
                    model = userDummy.photoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(41.dp)
                        .clip(CircleShape)
                )
            }



            Spacer(modifier = Modifier.width(5.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Hi, " + userDummy.text + "!",
                    style = MaterialTheme.typography.bodySmall,
                )
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Ready For Your Next Adventure?",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                )
            }


        }
    }
    
}

@Composable
fun NotifTopButton(modifier: Modifier = Modifier, ) {
    Card(
        modifier = Modifier
            .size(width = 50.dp, height = 50.dp)
            .shadow(
                elevation = 5.dp,
                shape = CircleShape,
                spotColor = Color.Black.copy(alpha = 50f),
            ),
        shape = CircleShape,
        CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black,

            ),
        border = BorderStroke(2.dp, Color(0xFFDEDEDE)),

    ) {
        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(50.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(25.dp)
            )
        }
    }

}


data class UserDummy(
    val text: String,
    val photoUrl: String? = null,


    )


@Preview
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewFirst() {
    BanyumasTourismAppTheme {
        HomeScreen(userDummy = UserDummy("Fajar" ), categories =
        listOf()
        )
    }
}

@Preview
@Composable
private fun PreviewItinerary() {
    BanyumasTourismAppTheme {
        ItineraryCard(userDummy = UserDummy("Fajar"))
    }


}

//@Preview
//@Composable
//private fun PreviewRowCategory() {
//    BanyumasTourismAppTheme {
////        CategoryCard(text = "Banyumas", icon = painterResource(id = R.drawable.mountainicon))
//        CategoryRow()
//    }
//}