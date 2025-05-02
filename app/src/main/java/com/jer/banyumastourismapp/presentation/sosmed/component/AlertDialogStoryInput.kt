package com.jer.banyumastourismapp.presentation.sosmed.component

import android.os.Build
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.rotate
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
fun AlertDialogStoryInput(
    modifier: Modifier = Modifier,
    alertTitle: String,
    destination: MutableState<String> = mutableStateOf(""),
    category: MutableState<String> = mutableStateOf(""),
    message: MutableState<String> = mutableStateOf(""),
    rating: MutableState<Int> = mutableStateOf(0),
    onDismiss: () -> Unit,
    onSubmit: () -> Unit
) {

    var showrequest by rememberSaveable { mutableStateOf(true) }

    var isVisible by rememberSaveable { mutableStateOf(false) }
    var isVisible2 by rememberSaveable { mutableStateOf(false) }
    var showAlert by rememberSaveable { mutableStateOf(false) }
    var showAlert2 by rememberSaveable { mutableStateOf(false) }
    var selectedMenu = rememberSaveable { mutableStateOf(PlanCategory("", 0)) }
    var selectedMenu2 = rememberSaveable { mutableStateOf(PlanCategory("", 0)) }
    var expandedState by rememberSaveable { mutableStateOf(false) }
    var expandedState2 by rememberSaveable { mutableStateOf(false) }
    val rotationState by animateFloatAsState(targetValue = if (expandedState) 180f else 0f)
    val rotationState2 by animateFloatAsState(targetValue = if (expandedState2) 180f else 0f)


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

                    Spacer(modifier = Modifier.height(15.dp))

                    //destination
                    OutlinedTextField(
                        value = destination.value,
                        onValueChange = { destination.value = it },
                        label = { Text("Destination") },
                        maxLines = 2,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        modifier = Modifier.width(300.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))


                    //category
                    Column {

                        OutlinedTextField(
                            value = selectedMenu2.value.name,
                            onValueChange = {
                                if (it.isNotEmpty()) category.value =
                                    it else category.value = ""
                            },
                            readOnly = true,
                            label = { Text(text = "Category") },

                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        expandedState2 = !expandedState2
                                        isVisible2 = true
                                    },
                                    modifier = Modifier
                                        .size(verySmallIcon)
                                        .rotate(rotationState2)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowDown,
                                        contentDescription = null
                                    )
                                }
                            },
                            modifier = Modifier.width(300.dp)

                        )

                        if (!isVisible2) expandedState2 = false

                        DropdownMenu(
                            offset = DpOffset(200.dp, 10.dp),
                            expanded = isVisible2,
                            onDismissRequest = { isVisible2 = false },
                        ) {
                            listCategory.forEach { category ->
                                DropdownMenuItem(
                                    text = { Text(text = category.name) },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(id = category.icon),
                                            contentDescription = null,
                                            modifier = Modifier.size(verySmallIcon),
                                            tint = MaterialTheme.colorScheme.primaryContainer,
                                        )
                                    },
                                    onClick = {
                                        isVisible2 = false
                                        showAlert2 = true
                                        selectedMenu2.value.name = category.name

                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))


                    if (showAlert2) {
                        when (selectedMenu2.value.name) {
                            "Mountain" -> {
                                category.value = "Mountain"
                                showAlert2 = false
                            }
                            "Play Ground" -> {
                                category.value = "Play Ground"
                                showAlert2 = false
                            }
                            "Waterfall" -> {
                                category.value = "Waterfall"
                                showAlert2 = false
                            }
                            "Temple" -> {
                                category.value = "Temple"
                                showAlert2 = false
                            }
                            "Park" -> {
                                category.value = "Park"
                                showAlert2 = false
                            }
                            "Museum" -> {
                                category.value = "Museum"
                                showAlert2 = false
                            }
                            "Forest" -> {
                                category.value = "Forest"
                                showAlert2 = false
                            }
                            "Lake" -> {
                                category.value = "Lake"
                                showAlert2 = false
                            }

                        }
                    }


                    Spacer(modifier = Modifier.height(10.dp))

                    //rating
                    Column {

                        OutlinedTextField(
                            value = selectedMenu.value.name,
                            onValueChange = {
                                if (it.isNotEmpty()) rating.value =
                                    it.toInt() else rating.value = 0
                            },
                            readOnly = true,
                            label = { Text(text = "Rating") },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.staricon),
                                    contentDescription = null,
                                    modifier = Modifier.size(verySmallIcon)
                                )
                            },
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
                            listRate.forEach { rate ->
                                DropdownMenuItem(
                                    text = { Text(text = rate.name) },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(id = rate.icon),
                                            contentDescription = null,
                                            modifier = Modifier.size(verySmallIcon),
                                            tint = MaterialTheme.colorScheme.primaryContainer,
                                        )
                                    },
                                    onClick = {
                                        isVisible = false
                                        showAlert = true
                                        selectedMenu.value.name = rate.name

                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))


                    if (showAlert) {
                        when (selectedMenu.value.name) {
                            "5" -> {
                                rating.value = 5
                                showAlert = false
                            }
                            "4" -> {
                                rating.value = 4
                                showAlert = false
                            }
                            "3" -> {
                                rating.value = 3
                                showAlert = false
                            }
                            "2" -> {
                                rating.value = 2
                                showAlert = false
                            }
                            "1" -> {
                                rating.value = 1
                                showAlert = false
                            }

                        }
                    }

                    //message
                    OutlinedTextField(
                        value = message.value,
                        onValueChange = { message.value = it },
                        label = { Text("Message") },
                        maxLines = 8,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
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
                                onSubmit()
                            },
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(text = "Post", color = MaterialTheme.colorScheme.onBackground)
                        }


                    }

                }
            }

        }
    }

}


val listRate = listOf(
    PlanCategory("5", R.drawable.staricon),
    PlanCategory("4", R.drawable.staricon),
    PlanCategory("3", R.drawable.staricon),
    PlanCategory("2", R.drawable.staricon),
    PlanCategory("1", R.drawable.staricon),
)

val listCategory = listOf(
    PlanCategory("Mountain", R.drawable.mountainicon),
    PlanCategory("Play Ground", R.drawable.beachicon),
    PlanCategory("Waterfall", R.drawable.waterfallicon),
    PlanCategory("Temple", R.drawable.tampleiconsvg),
    PlanCategory("Park", R.drawable.parkicon),
    PlanCategory("Museum", R.drawable.museumicon),
    PlanCategory("Forest", R.drawable.foresticon),
    PlanCategory("Lake", R.drawable.lakeicon),
)
