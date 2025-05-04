package com.jer.banyumastourismapp.presentation.ticket

import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.core.verySmallIcon
import com.jer.banyumastourismapp.domain.model.Ticket
import com.jer.banyumastourismapp.presentation.component.AppBarCustom

@Composable
fun TicketHistoryScreen(modifier: Modifier = Modifier, viewModel: TicketViewModel, navBack: () -> Unit) {

    val ticketHistory by viewModel.ticketHistory.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    LaunchedEffect(Unit) {
        viewModel.getTicketHistory(user?.uid ?: "")
    }

    Scaffold (
        topBar = {
            AppBarCustom(
                title = "Ticket History",
                navigateBack = {
                    navBack()
                }
            )
        },

        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->


        ConstraintLayout (
            modifier = Modifier.padding(innerPadding)
        ) {

            val (listTicket, progress) = createRefs()

            if (isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    trackColor = Color.Gray,
                    strokeCap = StrokeCap.Round,
                    strokeWidth = 8.dp,
                    modifier = Modifier
                        .size(100.dp)
                        .constrainAs(progress) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(15.dp)
                )
            } else {
                LazyColumn (
                    contentPadding = PaddingValues(15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier
                        .constrainAs(listTicket) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {

                    ticketHistory?.let {
                        items(count = it.size) { index->
                            TicketCard(ticket = it.reversed()[index])
                        }
                    }
                }
            }

        }





    }

}

@Composable
fun TicketCard(modifier: Modifier = Modifier, ticket: Ticket) {

    Card (
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)) {

            Icon(
                painter = painterResource(id = R.drawable.ticketicon),
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = MaterialTheme.colorScheme.primaryContainer
            )

            Spacer(modifier = Modifier.width(15.dp))

            Column {

                ticket.title?.let {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "Rp. ${ticket.price}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.outline
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(Color.Black))

                    Spacer(modifier = Modifier.width(10.dp))


                    Text(
                        text = "${DateUtils.getRelativeTimeSpanString(ticket.createdAt)}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }



        }
    }
}