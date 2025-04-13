package com.jer.banyumastourismapp.presentation.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.core.verySmallIcon
import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.domain.model.Itinerary
import com.jer.banyumastourismapp.domain.model.User
import com.jer.banyumastourismapp.domain.model.urlPictures
import com.jer.banyumastourismapp.presentation.component.CategoryRow
import com.jer.banyumastourismapp.presentation.component.DestinationCardStandRow
import com.jer.banyumastourismapp.presentation.itinerary.ItineraryViewModel
import com.jer.banyumastourismapp.presentation.itinerary.component.AlertDialogCore
import com.jer.banyumastourismapp.presentation.profile.ProfileViewModel
import com.jer.banyumastourismapp.ui.theme.BanyumasTourismAppTheme
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
//    viewModel: ProfileViewModel,
    viewModel: ItineraryViewModel,
    destination: LazyPagingItems<Destination>,
    navigateToDetail: (Destination) -> Unit,
    navigateToItinerary: () -> Unit,
    navigateToItineraryForm: () -> Unit,
    navigateToLogin: () -> Unit
) {

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val auth = Firebase.auth
    val firebaseUser = auth.currentUser

    val userData by viewModel.userData.collectAsState()
//    val itinerary by viewModel.itinerary.collectAsState()
    val itinerary by viewModel.itineraryWithPlanCards.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUserData()
//        userData?.let { viewModel.getItinerary(it.uid) }
        firebaseUser?.uid?.let { viewModel.getItinerary(it) }
    }

    LaunchedEffect(Unit) {
        if (firebaseUser == null) {
            navigateToLogin()
        }
    }



    fun signOut() {
        coroutineScope.launch {
            Log.d("HomeScreen", "User Success to Sign Out ")
            val credentialManager = CredentialManager.create(context)
            auth.signOut()
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
            navigateToLogin()

        }
    }

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
                bg, userAndNotif, itinerarySection, category1, category2,
                subTitle, list, subTitle2, list2,
            ) = createRefs()
            
            Box(
                modifier = Modifier
                    .height(220.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .constrainAs(bg) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )

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
                UserTopSection( user = userData, photoUrl = firebaseUser?.photoUrl.toString())

                NotifTopButton(onClick = { signOut() })
                
            }
            ItineraryCard(
                viewModel = viewModel,
                itinerary = itinerary?.itinerary,
                onClick = {
                    if (itinerary?.itinerary?.uid != "" && firebaseUser?.uid == itinerary?.itinerary?.uid && itinerary?.itinerary?.title != null) navigateToItinerary() else navigateToItineraryForm()
                },
                modifier = Modifier
                    .constrainAs(itinerarySection) {
                        top.linkTo(userAndNotif.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(start = 30.dp, end = 30.dp, bottom = 15.dp)

            )

            CategoryRow(
                isDelay = true,
                modifier = Modifier
                    .constrainAs(category1) {
                        top.linkTo(itinerarySection.bottom)
                    }
                    .padding(bottom = 5.dp),

            )

            CategoryRow(
                isDelay = false,
                modifier = Modifier
                    .constrainAs(category2) {
                        top.linkTo(category1.bottom)
                    }
                    .padding(vertical = 5.dp)
                ,

            )
            
            Text(
                text = "Popular Destination",
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .constrainAs(subTitle) {
                        top.linkTo(category2.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(start = 30.dp, top = 10.dp, bottom = 15.dp)
            )

            DestinationCardStandRow(
                destination = destination,
                modifier = Modifier
                    .constrainAs(list) {
                        top.linkTo(subTitle.bottom)
                    },
                onClick = { navigateToDetail(it)}
            )

            Text(
                text = "Nearby Destination",
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .constrainAs(subTitle2) {
                        top.linkTo(list.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(start = 30.dp, top = 15.dp, bottom = 15.dp)
            )

            DestinationCardStandRow(
                destination = destination,
                modifier = Modifier
                    .constrainAs(list2) {
                        top.linkTo(subTitle2.bottom)
                    }
                    .padding(bottom = 120.dp),
                onClick = { navigateToDetail(it) }
            )



        }
    }

}



@Composable
fun ItineraryCard(
    modifier: Modifier = Modifier,
    viewModel: ItineraryViewModel,
    itinerary: Itinerary? = null,
    onClick: () -> Unit
) {

    var showAlert by rememberSaveable { mutableStateOf(false) }

    val randomUrlPicture = rememberSaveable { mutableStateOf(urlPictures.random()) }

    Card(
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.onBackground,
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
                    .padding(start = 15.dp,)
            ) {
                Text(
                    text = "Your Itinerary",
                    fontSize = TextUnit(12f, TextUnitType.Sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                IconButton(
                    onClick = {
                        showAlert = true
                    },
                    content = {
                        Icon(
                            modifier = Modifier.size(verySmallIcon),
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f))

            if (showAlert) {
                AlertDialogCore(
                    negativeText = "No",
                    positiveText = "Yes",
                    onDismiss = { showAlert = false },
                    message = "Are you sure to delete your Itinerary?",
                    actionNegative = { showAlert = false },
                    actionPositive = {
                        showAlert = false
                        if (itinerary != null) {
                            viewModel.deleteItinerary(itinerary)
                        }
                        viewModel.loadItinerary()

                    }
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {

                if (itinerary?.uid != "" && itinerary?.title != null) {
                    AsyncImage(
                        model = randomUrlPicture.value,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(102.dp)
                            .clip(MaterialTheme.shapes.large)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.picturedummy),
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
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.height(102.dp)
                ){
                    Text(
                        text = itinerary?.title ?: "Your itinerary title here.",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(15.dp))


                    Text(
                        text = itinerary?.description ?: "Your itinerary description here.",
                        fontSize = 10.sp,
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
                IconAndText(painter = painterResource(id = R.drawable.moneyicon), text = "Rp. ${itinerary?.totalMoneySpend}" ?: "Rp. -")
                IconAndText(painter = painterResource(id = R.drawable.destinationicon), text = itinerary?.totalDestinations.toString() ?: "-")
                IconAndText(painter = painterResource(id = R.drawable.peopleicon), text = itinerary?.totalMembers.toString() ?: "-")
                IconAndText(painter = painterResource(id = R.drawable.calendaricon), text = itinerary?.date ?: "-")
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
fun UserTopSection(modifier: Modifier = Modifier, user: User?, photoUrl: String = "") {
    Card (
        modifier = Modifier
            .height(50.dp)
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
            modifier = Modifier.padding(start = 10.dp, end = 15.dp,top = 5.dp, bottom = 5.dp)
        ) {

            if (photoUrl == "") {

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
                    model = photoUrl,
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
                    text = "Hi, " + user?.name + "!",
                    fontSize = TextUnit(12f, TextUnitType.Sp),
                    )
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Ready For Your Next Adventure?",
                    fontSize = TextUnit(12f, TextUnitType.Sp),
                    fontWeight = FontWeight.SemiBold,
                )
            }


        }
    }
    
}

@Composable
fun NotifTopButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(width = 50.dp, height = 50.dp)
            .shadow(
                elevation = 5.dp,
                shape = CircleShape,
                spotColor = Color.Black.copy(alpha = 50f),
            )
            .clickable { onClick() },
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





@Preview
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewFirst() {
    BanyumasTourismAppTheme {
//        HomeScreen(user = User("Fajar" ), destination = listOf(
//            Destination(
//                "Raja Ampat",
//                "Raja Ampat, Papua Barat",
//                cost = 3500000,
//            ),
//            Destination(
//                "Raja Ampat",
//                "Raja Ampat, Papua Barat",
//                cost = 3500000,
//            ),
//            Destination(
//                "Raja Ampat",
//                "Raja Ampat, Papua Barat",
//                cost = 3500000,
//            ),
//        )
//        )
    }
}

//@Preview
//@Composable
//private fun PreviewItinerary() {
//    BanyumasTourismAppTheme {
//        ItineraryCard(onClick = {})
//    }
//
//
//}

//@Preview
//@Composable
//private fun PreviewRowCategory() {
//    BanyumasTourismAppTheme {
////        CategoryCard(text = "Banyumas", icon = painterResource(id = R.drawable.mountainicon))
//        CategoryRow()
//    }
//}