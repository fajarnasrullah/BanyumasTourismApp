package com.jer.banyumastourismapp.presentation.itinerary.component

import android.os.Build
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.core.verySmallIcon
import com.jer.banyumastourismapp.domain.model.PlanCategory
import com.jer.banyumastourismapp.domain.model.categoryPlan
import com.jer.banyumastourismapp.presentation.component.Category
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogPlanInput(
    modifier: Modifier = Modifier,
    alertTitle: String,
    category: MutableState<Int> = mutableStateOf(0),
    title: MutableState<String> = mutableStateOf(""),
    time: MutableState<String> = mutableStateOf(""),
    cost: MutableState<Int> = mutableStateOf(0),
    onDismiss: () -> Unit,
    onSubmit: () -> Unit
) {

    var showrequest by rememberSaveable { mutableStateOf(true) }

    var isVisible by rememberSaveable { mutableStateOf(false) }
    var showAlert by rememberSaveable {mutableStateOf(false)}
    var selectedMenu = rememberSaveable { mutableStateOf(PlanCategory("", 0)) }
    var expandedState by rememberSaveable { mutableStateOf(false) }
    val rotationState by animateFloatAsState(targetValue = if (expandedState) 180f else 0f)

    var timeRequest by rememberSaveable { mutableStateOf(false) }
    val timeState = rememberUseCaseState()
    var timeText by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    if (showrequest) {
        BasicAlertDialog(
            onDismissRequest = {
                showrequest = false
                onDismiss() },
        ) {

            Surface (
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.background,
            ) {
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(30.dp)
                ) {
                    Text(
                        text = alertTitle,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground,
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Column {
                        //category
                        OutlinedTextField(
                            value = "${category.value + 1} " + selectedMenu.value.name,
                            onValueChange = {
                                if (it.isNotEmpty()) category.value =
                                    it.toInt() else category.value = 0
                            },
                            readOnly = true,
                            label = { Text(text = "Category") },
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        expandedState = !expandedState
                                        isVisible = true
                                    },
                                    modifier = Modifier
                                        .size(verySmallIcon)
                                        .rotate(rotationState)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowDown,
                                        contentDescription = null
                                    )
                                }
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier.width(300.dp)

                        )

                        if (!isVisible) expandedState = false

                        DropdownMenu(
                            offset = DpOffset(200.dp, 10.dp),
                            expanded = isVisible,
                            onDismissRequest = { isVisible = false },
                        ) {
                            categoryPlan.forEach { menu ->
                                DropdownMenuItem(
                                    text = { Text(text = menu.name) },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(id = menu.icon),
                                            contentDescription = null,
                                            modifier = Modifier.size(verySmallIcon),
                                            tint = MaterialTheme.colorScheme.primaryContainer,
                                        )
                                    },
                                    onClick = {

                                        isVisible = false
                                        showAlert = true
                                        selectedMenu.value.name = menu.name

                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))


                    if (showAlert) {
                        when (selectedMenu.value.name) {
                            "On The Way" -> {
                                category.value = 0
                                showAlert = false
                            }
                            "Rest/Sleep" -> {
                                category.value = 1
                                showAlert = false
                            }
                            "Eat" -> {
                                category.value = 2
                                showAlert = false
                            }
                            "Play" -> {
                                category.value = 3
                                showAlert = false
                            }
                            "Destination" -> {
                                category.value = 4
                                showAlert = false
                            }
                        }
                    }

                    //title
                    OutlinedTextField(
                        value = title.value,
                        onValueChange = { title.value = it },
                        label = { Text("Title") },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        modifier = Modifier.width(300.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    //time
                    OutlinedTextField(
                        value = time.value,
                        onValueChange = { time.value = it },
                        label = { Text("Time") },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        trailingIcon = {
                            IconButton(
                                modifier = Modifier.size(verySmallIcon),
                                onClick = {
                                    timeRequest = true
                                    timeState.show()
                                }
                            ) { Icon(
                                painter = painterResource(id = R.drawable.timeicon),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground,
                            ) }
                        },
                        modifier = Modifier.width(300.dp)

                    )

                    if (timeRequest) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            var selectedTime by remember { mutableStateOf(LocalTime.of(23, 20, 0)) }
                            ClockDialog(
                                state = timeState,
                                selection = ClockSelection.HoursMinutes { hours, minutes ->
                                    selectedTime = LocalTime.of(hours, minutes, 0)
                                    time.value = selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                                },
                                config = ClockConfig(
                                    defaultTime = selectedTime,
                                    is24HourFormat = true
                                ),

                                )
                        }

                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    //cost
                    OutlinedTextField(
                        value = if (cost.value == 0) "" else cost.value.toString(),
                        onValueChange = { if (it.isNotEmpty()) cost.value = it.toInt() else cost.value = 0 },
                        label = { Text(text = "Cost") },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next, keyboardType = KeyboardType.Number),
                        modifier = Modifier.width(300.dp)

                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {

                        OutlinedButton(
                            border = BorderStroke(width = 1.dp, color =  MaterialTheme.colorScheme.primaryContainer),
                            shape = MaterialTheme.shapes.small,
                            onClick = {
                                showrequest = false
                                onDismiss()
                            },
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(text = "Close", color = MaterialTheme.colorScheme.onBackground)
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                            shape = MaterialTheme.shapes.small,
                            onClick = {
                                showrequest = false
                                onDismiss()
                                Toast.makeText(context, "Request Sent: \n$title, \n$time, \n$cost", Toast.LENGTH_SHORT).show()
                                onSubmit()
                            },
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(text = "Submit", color = MaterialTheme.colorScheme.onBackground)
                        }


                    }

                }
            }

        }
    }

}

