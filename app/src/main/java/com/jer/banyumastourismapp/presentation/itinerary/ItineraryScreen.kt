package com.jer.banyumastourismapp.presentation.itinerary

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.common.verySmallIcon
import com.jer.banyumastourismapp.presentation.component.AppBarCustom
import com.jer.banyumastourismapp.presentation.home.User
import com.jer.banyumastourismapp.presentation.itinerary.component.AlertDialogPlanInput
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

@Composable
fun ItineraryScreen(
    modifier: Modifier = Modifier,
    user: User,
    itinerary: Itinerary,
    plan: Plan,
//    listPlan: List<Plan>,
    onClick: () -> Unit
) {


    val scrollState = rememberScrollState()


    Scaffold (
        modifier = modifier,
        topBar = {
            AppBarCustom(
                navigateBack = { },
                title = "Your Itinerary",
                backgroundColor = MaterialTheme.colorScheme.onPrimary,
//                actionIcon = painterResource(id = R.drawable.bookmarkbordericon),
//                action = { },
                modifier = Modifier.shadow(8.dp)
            ) }
    ) { innerPadding ->

        ConstraintLayout (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState),

        ) {

            val ( title, userNDate, description, notes, planSection) = createRefs()

            Text(
                text = itinerary.title,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                    }
                    .padding(top = 30.dp, start = 30.dp, end = 30.dp)
            )

            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(userNDate) {
                        top.linkTo(title.bottom)
                    }
                    .padding(horizontal = 30.dp, vertical = 30.dp)
            ) {
                UserSection(user = user)
                DatePeriodSection(onClick = { /*TODO*/ }, date = "August 21 - 25")
            }


            DescriptionSection(
                description = itinerary.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(description) {
                        top.linkTo(userNDate.bottom)

                    }
                    .padding(horizontal = 30.dp)
            )

            CardExpand(
                itinerary = itinerary,
                notes = itinerary.notes,
                onClick = {},
                isNotes = true,
                listCategoryPlan = categoryPlan,
//                plan.category ?: emptyList(),
                modifier = Modifier
                    .constrainAs(notes) {
                        top.linkTo(description.bottom)
                    }
                    .padding(start = 30.dp, end = 30.dp, top = 15.dp, bottom = 30.dp)

            )

            PlanSection(
                plan = plan,
                itinerary = itinerary,
                onClick = { },
                listCardPlan = itinerary.listCardPlan ,
                listCategoryPlan = categoryPlan,
//                plan.category ?: emptyList(),
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(planSection) {
                        top.linkTo(notes.bottom)
                    }
                    .padding(horizontal = 30.dp)
            )

        }
    }
}


@Composable
fun PlanSection(
    modifier: Modifier = Modifier,
    plan: Plan,
    listCardPlan: List<List<Plan>>? = null,
    listCategoryPlan: List<PlanCategory>,
//    listCategoryPlan: List<List<PlanCategory>>,
    itinerary: Itinerary,
    onClick: () -> Unit
) {


    val daysArray = IntArray(itinerary.daysCount)
    val finalListPlan = MutableList(itinerary.daysCount) { plan }

//    val finalListPlan = listCardPlan?.takeIf { it.isNotEmpty() }?.flatten() ?: listOf(plan)

    Column (
        modifier = modifier
    ) {
        Text(
            text = "Your Plan",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.height(15.dp))

        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(bottom = 30.dp),
            modifier = Modifier.heightIn(max = 2000.dp)
        ) {

                itemsIndexed(finalListPlan) { index, plan ->
                    CardExpand(
                        isNotes = false,
                        plan = plan,
                        listPlan = listCardPlan?.get(index),
                        listCategoryPlan = listCategoryPlan,
                        itinerary = itinerary,
                        dayNumber = index,
                        onClick = {
                            onClick()
                        }
                    )

                }

        }
//        Log.d("PlanSection", "listCardPlan size: ${listCategoryPlan.size ?: 0}")

    }
}

@Composable
fun PlanColumn(
    modifier: Modifier = Modifier,
    listPlan: List<Plan>,
    listCategoryPlan: List<PlanCategory>
//    listCategoryPlan: List<List<PlanCategory>>
) {
    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(bottom = 5.dp),
        modifier = Modifier.heightIn(max = 200.dp)
    ) {


        if (listPlan.isNotEmpty()) {
            itemsIndexed(
                listPlan
//                ?: emptyList()
            ) { index, plan ->

                PlanItem(
                    plan = plan,
                    indexItem = plan.category ,
//                    index,
                    listCategoryPlanItem = listCategoryPlan,
                    onClick = { }
                )
            }
        } else {
            item {
                Text(
                    text = "Plan is Empty",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(vertical = 15.dp)
                )
            }
        }

    }

//    Log.d("PlanColumn", "listCardPlan size: ${listCategoryPlan.size }")

}


@Composable
fun PlanItem(
    modifier: Modifier = Modifier,
    plan: Plan? = null,
    listCategoryPlanItem: List<PlanCategory>? = null ,
    indexItem: Int,
    onClick: () -> Unit
) {

//    Log.d("PlanItem", "listCardPlan size: ${listCategoryPlanItem.size }")

    Column (
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)

            )  {
                if (listCategoryPlanItem?.get(indexItem) != null) {
                    Icon(
                        painter = painterResource(id =
                        listCategoryPlanItem[indexItem].icon
//                    plan?.category!!.icon
//                listCategoryPlanItem[indexItem].icon
                        ),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(verySmallIcon)
                    )
                } else {
                    Text(
                        text =  "",
                        fontSize = 10.sp,
                    )
                }


                Text(
                    text = plan?.time ?: "",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = plan?.title ?: "Plan is Empty",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f)
            )

            Box(modifier = Modifier.weight(1f))

        }

        Spacer(modifier = Modifier.height(2.dp))

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        )
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardExpand(
    modifier: Modifier = Modifier,
    plan: Plan? = null,
    itinerary: Itinerary? = null,
    listPlan: List<Plan>? = null,
//    listCategoryPlan: List<List<PlanCategory>>,
    listCategoryPlan: List<PlanCategory>,
    onClick: () -> Unit,
    notes: String? = null,
    isNotes: Boolean = false,
    dayNumber: Int? = null
) {
    var expandedState by rememberSaveable { mutableStateOf(false) }
    val rotationState by animateFloatAsState(targetValue = if (expandedState) 180f else 0f)
    var isVisible by rememberSaveable { mutableStateOf(false) }
    var showAlert by rememberSaveable {mutableStateOf(false)}
    var selectedMenu by rememberSaveable { mutableStateOf("") }

//    Log.d("CardExpand", "listCategoryPlan size: ${listCategoryPlan?.size ?: 0}")

    Card(
        modifier = modifier
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



                if (isNotes) {

                    Row (modifier = Modifier.weight(1f)) {
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

                        Spacer(modifier = Modifier.width(15.dp))

                        Text(
                            text = "Notes",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                } else {

                    Row (modifier = Modifier.weight(1f)) {

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

                        Spacer(modifier = Modifier.width(15.dp))


                        if (dayNumber != null) {
                            Text(
                                text = "Day ${dayNumber + 1}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                    }


//                    itinerary?.cityGoals?.forEach { city ->
//                        Text(
//                            text = city.name,
//                            fontSize = 10.sp,
//                            fontStyle = FontStyle.Italic,
//                            color = MaterialTheme.colorScheme.outline
//                        )
//                    }
                }

                Box(modifier = Modifier.weight(2f))


                val listAction = listOf("Add", "Delete")

                IconButton(
                    onClick = {
//                        onClick()
                        isVisible = true

                              },

                    modifier = Modifier
                        .weight(0.5f)
                        .size(verySmallIcon)
                        .align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                val context = LocalContext.current
                DropdownMenu(
                    offset = DpOffset(200.dp, 10.dp),
                    expanded = isVisible,
                    onDismissRequest = { isVisible = false },
                ) {
                    listAction.forEach { menu ->
                        DropdownMenuItem(
                            text = { Text(text = menu) },
                            onClick = {
//                                onClick()
                                // logic nya disini cuy

                                isVisible = false
                                showAlert = true
                                selectedMenu = menu

                            }
                        )
                    }
                }

                if (showAlert) {
                    when (selectedMenu) {
                        "Add" -> {
                            AlertDialogPlanInput(onDismiss = {showAlert = false})
                        }

                        "Delete" -> {
                            AlertDialogCore(
                                negativeText = "No",
                                positiveText = "Yes",
                                onDismiss = { showAlert = false },
                                message = "Are you sure to delete this plan?",
                                actionNegative = {  },
                                actionPositive = { }
                            )
                        }
                    }

                }



            }


            if (expandedState) {
                Spacer(modifier = Modifier.height(15.dp))

                if (isNotes) {

                    Text(
                        text = notes ?: "" ,
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Justify
                    )


                } else {

                    if (listPlan != null) {
                        PlanColumn(
                            listPlan = listPlan ?: emptyList(),
                            modifier = Modifier.fillMaxWidth(),
                            listCategoryPlan = listCategoryPlan
//                            plan?.category ?: emptyList()
                        )
                    }
                    else {
                        Text(
                            text = "Plan is Empty",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }

                }


            }

        }
    }
}




@Composable
fun AlertDialogCore(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    negativeText: String,
    positiveText: String,
    message: String,
    actionNegative: () -> Unit,
    actionPositive: () -> Unit,
) {

    var showrequest by remember { mutableStateOf(true) }
    if (showrequest)  {
        AlertDialog(
            onDismissRequest = {
                showrequest = false
                onDismiss() },
            tonalElevation = 8.dp,
            confirmButton = {
                TextButton(
                    onClick = {
                        actionPositive()
                        showrequest = false
                        onDismiss()
                    }
                ) {
                    Text(text = positiveText)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        actionNegative()
                        showrequest = false
                        onDismiss()
                    }
                ) {
                    Text(text = negativeText)
                }
            },
            text = { Text(text = message) },
        )
    }


}


@Composable
fun DescriptionSection(modifier: Modifier = Modifier, description: String) {
    Column (
        modifier = modifier
    ) {

        Text(
            text = "Description",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = description,
            fontSize = 12.sp,
            textAlign = TextAlign.Justify,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun UserSection(modifier: Modifier = Modifier, user: User) {
    Row (
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Surface (
            color = MaterialTheme.colorScheme.primaryContainer,
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
            shape = CircleShape,
        ) {
            Box(modifier = Modifier.size(40.dp), contentAlignment = Alignment.Center) {
                if (user.photoUrl.isNullOrBlank()) {
                    Image(
                        painter = painterResource(id = R.drawable.userimage),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)

                    )
                } else {
                    AsyncImage(
                        model = user.photoUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)

                    )
                }
            }
        }



        Spacer(modifier = Modifier.width(5.dp))

        Column (
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Chief",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = user.name,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePeriodSection(modifier: Modifier = Modifier, onClick: () -> Unit, date: String ) {


    val calendarState = rememberUseCaseState()
    var totalDaysRange by remember { mutableStateOf(0) }
    var start by rememberSaveable { mutableStateOf("") }
    var end by rememberSaveable { mutableStateOf("") }

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
                val startDateString = startDate.format(DateTimeFormatter.ofPattern("dd/MM", Locale.getDefault()))
                val endDateString = endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault()))
                start = startDateString
                end = endDateString
            },

        )

        Log.d("DatePeriodSection", "totalDaysRange: $totalDaysRange")

    }




    Row (
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {


        Surface (
            color = MaterialTheme.colorScheme.primaryContainer,
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
            shape = CircleShape,
            onClick = {
                calendarState.show()
//                onClick()
            }
        ) {
            Box(modifier = Modifier.size(40.dp), contentAlignment = Alignment.Center) {
                Icon(
                    painter = painterResource(id = R.drawable.calendaricon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.surfaceContainerLowest,
                    modifier = Modifier.size(verySmallIcon)
                )
            }
        }

        Spacer(modifier = Modifier.width(5.dp))

        Column (
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Departure",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text =  "${start} - \n${end}",
//                date,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PrevItineraryScreen() {
//    val categoryPlan = listOf(
//        PlanCategory("On The Way", { painterResource(id = R.drawable.caricon) }),
//        PlanCategory("Hotel", { painterResource(id = R.drawable.bedicon) }),
//        PlanCategory("Resto", { painterResource(id = R.drawable.foodicon) }),
//        PlanCategory("Destination", { painterResource(id = R.drawable.placeicon) }),
//    )

//    val categoryPlanList =
//        listOf (
//            categoryPlan,
//            categoryPlan,
//            categoryPlan,
//            categoryPlan,
//            categoryPlan
//        )
//
//    val listCity = listOf(
//        City("Yogyakarta", ""),
//        City("Bandung", ""),
//        City("Jakarta", ""),
//        City("Surabaya", ""),
//        City("Semarang", ""),
//    )
//
//    val listPlan1 = listOf(
//        Plan(
//            category = PlanCategory("On The Way", R.drawable.caricon),
//            title = "Berangkat",
//            city = listCity,
//            time = "13.00",
//            cost = 0
//        ),
//
//        Plan(
//            category = PlanCategory("Resto", R.drawable.foodicon),
//            title = "Makan Heula",
//            city = listCity,
//            time = "20.00",
//            cost = 0
//        ),
//        Plan(
//            category = PlanCategory("Destination", R.drawable.placeicon),
//            title = "Sampai cuy",
//            city = listCity,
//            time = "21.00",
//            cost = 0
//        ),
//        Plan(
//            category = PlanCategory("Hotel", R.drawable.bedicon),
//            title = "Menginap di Hotel",
//            city = listCity,
//            time = "18.00",
//            cost = 0
//        ),
//
//        )
//    val listPlan2 = listOf(
//        Plan(
//            category = PlanCategory("On The Way", R.drawable.caricon),
//            title = "Mangkat",
//            city = listCity,
//            time = "13.00",
//            cost = 0
//        ),
//
//
//        Plan(
//            category = PlanCategory("Destination", R.drawable.placeicon),
//            title = "sokin",
//            city = listCity,
//            time = "21.00",
//            cost = 0
//        ),
//
//        Plan(
//            category = PlanCategory("Hotel", R.drawable.bedicon),
//            title = "Rehat sejenak",
//            city = listCity,
//            time = "18.00",
//            cost = 0
//        ),
//
//        Plan(
//            category = PlanCategory("Resto", R.drawable.foodicon),
//            title = "Madang lurr",
//            city = listCity,
//            time = "20.00",
//            cost = 0
//        ),
//
//
//        )
//    val listPlan3 = listOf(
//        Plan(
//            category = PlanCategory("On The Way", R.drawable.caricon),
//            title = "otw",
//            city = listCity,
//            time = "13.00",
//            cost = 0
//        ),
//        Plan(
//            category = PlanCategory("Hotel", R.drawable.bedicon),
//            title = "turu",
//            city = listCity,
//            time = "18.00",
//            cost = 0
//        ),
//        Plan(
//            category = PlanCategory("Resto", R.drawable.foodicon),
//            title = "mangan",
//            city = listCity,
//            time = "20.00",
//            cost = 0
//        ),
//        Plan(
//            category = PlanCategory("Destination", R.drawable.placeicon),
//            title = "sampai lokasi",
//            city = listCity,
//            time = "21.00",
//            cost = 0
//        )
//    )
//
//    val listPlan4 = listOf(
//        Plan(
//            category = PlanCategory("On The Way", R.drawable.caricon),
//            title = "otw",
//            city = listCity,
//            time = "13.00",
//            cost = 0
//        ),
//        Plan(
//            category = PlanCategory("Hotel", R.drawable.bedicon),
//            title = "turu",
//            city = listCity,
//            time = "18.00",
//            cost = 0
//        ),
//        Plan(
//            category = PlanCategory("Resto", R.drawable.foodicon),
//            title = "mangan",
//            city = listCity,
//            time = "20.00",
//            cost = 0
//        ),
//        Plan(
//            category = PlanCategory("Destination", R.drawable.placeicon),
//            title = "sampai lokasi",
//            city = listCity,
//            time = "21.00",
//            cost = 0
//        )
//    )
//
//    val listPlan5 = listOf(
//        Plan(
//            category = PlanCategory("On The Way", R.drawable.caricon),
//            title = "otw",
//            city = listCity,
//            time = "13.00",
//            cost = 0
//        ),
//        Plan(
//            category = PlanCategory("Hotel", R.drawable.bedicon),
//            title = "turu",
//            city = listCity,
//            time = "18.00",
//            cost = 0
//        ),
//        Plan(
//            category = PlanCategory("Resto", R.drawable.foodicon),
//            title = "mangan",
//            city = listCity,
//            time = "20.00",
//            cost = 0
//        ),
//        Plan(
//            category = PlanCategory("Destination", R.drawable.placeicon),
//            title = "sampai lokasi",
//            city = listCity,
//            time = "21.00",
//            cost = 0
//        )
//    )
//    val listCardPlan = listOf(listPlan1, listPlan2, listPlan3, listPlan4, listPlan5)

//    BanyumasTourismAppTheme {
//        ItineraryScreen(
//            user = User(name = "Fajar Nasrullah"),
//            itinerary = Itinerary (
//                daysCount = 5,
//                title = "Seru-seruan di Jawa Tengah",
//                description = "Libur semesteran 7 hari full di jawa tengah bareng sobat jawir sekontrakan. Bakal berkunjung ke 4 kota dengan 10 destinasi.",
//                membersCount = 5,
//                cityGoals = listCity,
//                notes = "Libur semesteran 7 hari full di jawa tengah bareng sobat jawir sekontrakan. Bakal berkunjung ke 4 kota dengan 10 destinasi.",
//                listCardPlan = listCardPlan
//
//            ),
//            plan = Plan(
//                category =  PlanCategory("On The Way", R.drawable.caricon),
////                categoryPlanList,
//                title = "Kumpul Sejenak",
//                city = listCity,
//                time = "12.00",
//                cost = 0
//            ),
////            listPlan = listPlan1,
//            onClick = {}


//        )
//    }
}

//@Preview(showBackground = true)
//@Composable
//private fun PrevPlanSection() {
//
//    val listCity = listOf(
//        City("Yogyakarta", ""),
//        City("Bandung", ""),
//        City("Jakarta", ""),
//        City("Surabaya", ""),
//        City("Semarang", ""),
//    )
//    val listPlan = listOf(
//        Plan(
//            category = emptyList(),
//            title = "Kumpul Sejenak",
//            city = listCity,
//            time = "12.00",
//            cost = 0
//        ),
//        Plan(
//            category = emptyList(),
//            title = "Kumpul Sejenak",
//            city = listCity,
//            time = "12.00",
//            cost = 0
//        ),
//        Plan(
//            category = emptyList(),
//            title = "Kumpul Sejenak",
//            city = listCity,
//            time = "12.00",
//            cost = 0
//        ),Plan(
//            category = emptyList(),
//            title = "Kumpul Sejenak",
//            city = listCity,
//            time = "12.00",
//            cost = 0
//        ),Plan(
//            category = emptyList(),
//            title = "Kumpul Sejenak",
//            city = listCity,
//            time = "12.00",
//            cost = 0
//        )
//
//    )
//
//    BanyumasTourismAppTheme {
//        PlanSection(
//            plan = Plan(
//                category = emptyList(),
//                title = "Kumpul Sejenak",
//                city = listCity,
//                time = "12.00",
//                cost = 0
//            ),
//
//
//            itinerary = Itinerary (
//                daysCount = 5,
//                title = "Seru-seruan di Jawa Tengah",
//                description = "Libur semesteran 7 hari full di jawa tengah bareng sobat jawir sekontrakan. Bakal berkunjung ke 4 kota dengan 10 destinasi.",
//                membersCount = 5,
//                cityGoals = listCity,
////                notes = "Libur semesteran 7 hari full di jawa tengah bareng sobat jawir sekontrakan. Bakal berkunjung ke 4 kota dengan 10 destinasi."
//
//            ),
//            onClick = {},
//
////            listPlan = listPlan
//        )
//    }
//}
