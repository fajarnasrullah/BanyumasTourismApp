package com.jer.banyumastourismapp.presentation.itinerary

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.core.verySmallIcon
import com.jer.banyumastourismapp.domain.model.Itinerary
import com.jer.banyumastourismapp.presentation.component.AppBarCustom
import com.jer.banyumastourismapp.presentation.itinerary.component.ItineraryEvent
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItineraryFormScreen(
    modifier: Modifier = Modifier,
    navToItinerary: () -> Unit,
    navBack: () -> Unit,
    viewModel: ItineraryViewModel
) {

    val scrollState = rememberScrollState()

    val itinerary by viewModel.itinerary.collectAsState()
    val userData by viewModel.userData.collectAsState()
    val auth = Firebase.auth
    val user = auth.currentUser
    val scope = rememberCoroutineScope()

    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var notes by rememberSaveable { mutableStateOf("") }
    var totalMoneySpend by rememberSaveable { mutableIntStateOf(0) }
    var totalDestinations by rememberSaveable { mutableIntStateOf(0) }
    var totalMembers by rememberSaveable { mutableIntStateOf(0) }


    val calendarState = rememberUseCaseState()
    var date by rememberSaveable { mutableStateOf("") }
    var dateRequest by rememberSaveable { mutableStateOf(false) }
    var totalDaysRange by rememberSaveable { mutableStateOf(0) }
    var startDay by rememberSaveable { mutableStateOf("") }
    var endDay by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current
    val snackbarState = remember {SnackbarHostState()}
    var newItinerary by rememberSaveable {
        mutableStateOf(
            Itinerary(
                uid = "",
                title = "",
                daysCount = 0,
                date = "",
                description = "",
                notes = "",
            )
        )
    }


    LaunchedEffect(Unit) {
        viewModel.getUserData()
        if (user != null) {
            viewModel.getItinerary(user.uid)
            Log.d("ItineraryFormScreen", "Get new Itinerary, idUser: ${user.uid}")
        }
//
//        viewModel.itinerary.value?.let{
//
//            newItinerary = newItinerary.copy(title = it.title)
//        }

    }

    LaunchedEffect (viewModel.eventFlow) {


        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ItineraryEvent.Success -> {
                    navToItinerary()
                }
                is ItineraryEvent.Error  -> {
                    snackbarState.showSnackbar(event.message)
                }
            }
        }
    }

    Scaffold (
        snackbarHost = { SnackbarHost(hostState = snackbarState)},
        topBar = { AppBarCustom(
            navigateBack = { navBack() },
            backgroundColor = MaterialTheme.colorScheme.onPrimary,
            title = "Create Your Itinerary",

            modifier = Modifier.fillMaxWidth()
        )
        },
//        bottomBar = { BottomBarDetail(
//            price = "Rp. ${totalPrice.toString() }",
//            textButton = "Pay Now",
//            headline = "Total Price",
//            onClick = { }
//        ) }
    ) { innerPadding ->

        ConstraintLayout (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {

            val content = createRef()

            Column (
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier
                    .constrainAs(content) {
                        top.linkTo(parent.top,)
                        start.linkTo(parent.start,)
                        end.linkTo(parent.end,)
                    }
                    .padding(start = 30.dp, end = 30.dp)
                    .verticalScroll(scrollState)
            ) {

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Title:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )


                itinerary?.title.let{
                    Text(
                        text = it ?: "empty",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Text(
                    text = "Description:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )


                itinerary?.description.let{
                    Text(
                        text = it ?: "empty",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Text(
                    text = "Notes:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )


                itinerary?.notes.let{
                    Text(
                        text = it ?: "empty",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.fillMaxWidth()
                    )
                }


                OutlinedTextField (
                    value = title,
                    onValueChange = {
                        title = it
                                    },
                    label = { Text(text = "Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
                )

                //date
                OutlinedTextField (
                    value = date,
                    onValueChange = {
                        date = it
                                    },
                    label = { Text(text = "Date") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                dateRequest = true
                                calendarState.show()
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.calendaricon),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.size(verySmallIcon )
                            )
                        }
                    }
                )



                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val selectedDateRange = remember {
                        val value = android.util.Range(LocalDate.now(), LocalDate.now().plusDays(1))
                        mutableStateOf(value)
                    }


                    CalendarDialog(
                        state = calendarState,

                        config = CalendarConfig(
                            style = CalendarStyle.MONTH,
                            monthSelection = true,
                            yearSelection = true,

                            ),
                        selection = CalendarSelection.Period(
                            selectedRange = selectedDateRange.value
                        ) {startDate, endDate ->
                            selectedDateRange.value = android.util.Range(startDate, endDate)

                            totalDaysRange = ChronoUnit.DAYS.between(startDate, endDate).toInt() + 1
                            val startDateString = startDate.format(DateTimeFormatter.ofPattern("dd/MM//yyyy", Locale.getDefault()))
                            val endDateString = endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault()))
                            newItinerary = newItinerary.copy(daysCount = totalDaysRange)
                            startDay = startDateString
                            endDay = endDateString
                        },
                        )

                    Log.d("DatePeriodSection", "totalDaysRange: $totalDaysRange")

                    date = "$startDay - $endDay"

                }


                // desc
                OutlinedTextField (
                    value = description,
                    onValueChange = {
                        description = it
                                    },
                    label = { Text(text = "Description") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                )

                // notes
                OutlinedTextField (
                    value = notes,
                    onValueChange = {
                        notes = it
                                    },
                    label = { Text(text = "Notes") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                )

                totalMoneySpend.toString()

                // total money spend
                OutlinedTextField (
                    value = if (totalMoneySpend == 0) "" else totalMoneySpend.toString(),
                    onValueChange = {
                       if (it.isNotEmpty()) totalMoneySpend = it.toInt() else totalMoneySpend = 0
                    },
                    label = { Text(text = "Money Spend Amount") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                )

                // total destinations
                OutlinedTextField (
                    value = if (totalDestinations == 0) "" else totalDestinations.toString(),
                    onValueChange = {
                        if (it.isNotEmpty()) totalDestinations = it.toInt() else totalDestinations = 0
                    },
                    label = { Text(text = "Destination Amount") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                )

                // total members
                OutlinedTextField (
                    value = if (totalMembers == 0) "" else totalMembers.toString(),
                    onValueChange = {
                        if (it.isNotEmpty()) totalMembers = it.toInt() else totalMembers = 0
                    },
                    label = { Text(text = "Members Amount") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                )

                Button(
                    onClick = {

                        newItinerary = newItinerary.copy(uid = userData?.uid ?: "", daysCount = totalDaysRange, title = title, date = date, description = description, notes = notes, totalMoneySpend = totalMoneySpend, totalDestinations = totalDestinations, totalMembers = totalMembers)
                        viewModel.insertItinerary(newItinerary)

                        newItinerary.let {
                            Log.d("ItineraryFormScreen", "Insert new Itinerary -> uid: ${it.uid}, title: ${it.title}, date: ${it.date}")
                        }

                    },
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.background,
                        modifier = Modifier.size(25.dp)
                    )
                }

            }

        }


    }
}



