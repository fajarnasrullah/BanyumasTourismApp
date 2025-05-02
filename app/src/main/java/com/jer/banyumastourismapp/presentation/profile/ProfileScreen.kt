package com.jer.banyumastourismapp.presentation.profile

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.core.verySmallIcon
import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.presentation.component.DestinationCardLandscape
import com.jer.banyumastourismapp.presentation.itinerary.component.AlertDialogCore
import com.jer.banyumastourismapp.presentation.profile.bookmark.BookmarkState
import com.jer.banyumastourismapp.presentation.profile.component.AlertDialogEditProfile
import kotlinx.coroutines.launch


@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    state: BookmarkState,
    navigateToDetail: (Destination) -> Unit,
    navToTicket: () -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {

    var showAlert by rememberSaveable { mutableStateOf(false) }
    var showAlertSignOut by rememberSaveable { mutableStateOf(false) }

    val auth = Firebase.auth
    val firebaseUser = auth.currentUser
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val userData by viewModel.userData.collectAsState()



    fun signOut() {
        coroutineScope.launch {
            Log.d("ProfileScreen", "User Success to Sign Out ")
            val credentialManager = CredentialManager.create(context)
            auth.signOut()
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
            navigateToLogin()

        }
    }

    LaunchedEffect(Unit) {
        if (firebaseUser == null) {
            navigateToLogin()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getUserData()
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxHeight()
            .padding(30.dp)
    ) {

        Surface (
            shape = CircleShape,
            border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primaryContainer)
        ) {
            if (firebaseUser?.photoUrl == null) {
                Image(
                    painter = painterResource(id = R.drawable.userimage),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            } else {
                AsyncImage(
                    model = firebaseUser.photoUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            }
        }
        

        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()

        ){
            if (userData != null) {
                userData?.name?.let {
                    Text(
                        text = it,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            } else {
                firebaseUser?.displayName?.let {
                    Text(
                        text = it,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }

            }


            IconButton(
                onClick = {
                    showAlert = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(verySmallIcon)
                )
            }

            if (showAlert) {
                AlertDialogEditProfile(viewModel = viewModel, onDismiss = {showAlert = false})
            }

        }


        if (userData?.desc != null) {
            userData?.desc?.let {
                Text(
                    text = it,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    textAlign = TextAlign.Center,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }

        Spacer(modifier = Modifier.height(30.dp))


        Card (
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Column (
                modifier = Modifier.padding(15.dp)
            ){

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navToTicket()
                        }
                ){
                    Row (verticalAlignment = Alignment.CenterVertically,){
                        Box (
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(45.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                                ),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ticketicon),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.size(25.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(15.dp))

                        Text(
                            text = "My Ticket",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }


                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .size(25.dp)

                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        }
                ){
                    Row (verticalAlignment = Alignment.CenterVertically,){
                        Box (
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(45.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                                ),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.historyicon),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.size(25.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(15.dp))

                        Text(
                            text = "Orders History",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }


                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .size(25.dp)

                    )
                }

                Spacer(modifier = Modifier.height(15.dp))


                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showAlertSignOut = true
                        }
                ){

                    Row (verticalAlignment = Alignment.CenterVertically,) {

                        Box (
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(45.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .background(color = MaterialTheme.colorScheme.error.copy(alpha = 0.3f)),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.logouticon),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(25.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(15.dp))

                        Text(
                            text = "Sign Out",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }


                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .size(25.dp)

                    )
                }

                if (showAlertSignOut) {
                    AlertDialogCore(
                        negativeText = "No",
                        positiveText = "Yes",
                        onDismiss = { showAlert = false },
                        message = "Are you sure to sign out from the app?",
                        actionNegative = { showAlert = false },
                        actionPositive = {
                            showAlert = false
                            signOut()

                        }
                    )
                }

            }

        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "My Bookmark",
            fontSize = 16.sp,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        BookmarkList(state = state, onClick = navigateToDetail)

        Spacer(modifier = Modifier.height(30.dp))

    }
}

@Composable
fun BookmarkList(modifier: Modifier = Modifier, state: BookmarkState, onClick: (Destination) -> Unit) {

    LazyRow (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(start = 15.dp, end = 30.dp, top = 15.dp, bottom = 15.dp),
        modifier = Modifier.fillMaxWidth()

    ) {
        items(state.listBookMarkDestination) { item ->
            DestinationCardLandscape(
                destination = item,
                onClick = onClick,
                buttonVisibility = false,
                modifier = Modifier.width(348.dp)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun PrevProfile() {
//    BanyumasTourismAppTheme {
//        ProfileScreen(
//            user = User("Fajar", desc = "aokwokaowkokaowkoakwok"),
//            state = BookmarkState(),
//            navigateToDetail = { /*TODO*/ }
//        )
//    }
//
//}