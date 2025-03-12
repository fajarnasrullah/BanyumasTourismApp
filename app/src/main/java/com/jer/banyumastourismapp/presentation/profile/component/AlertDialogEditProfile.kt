package com.jer.banyumastourismapp.presentation.profile.component

import android.os.Build
import android.widget.Toast
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
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.core.verySmallIcon
import com.jer.banyumastourismapp.presentation.profile.ProfileViewModel
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogEditProfile(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel,
    onDismiss: () -> Unit
) {

    val userData by viewModel.userData.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUserData()
    }

    var showrequest by rememberSaveable { mutableStateOf(true) }
    var username by rememberSaveable { mutableStateOf(userData?.name) }
    var description by rememberSaveable { mutableStateOf(userData?.desc) }



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
                        text = "Add Your New Plan Now!",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground,
                    )

                    Spacer(modifier = Modifier.height(10.dp))


                    username?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = { username = it },
                            label = { Text("Username") },
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                            modifier = Modifier.width(300.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))


                        OutlinedTextField(

                            value = userData?.desc ?: "",
                            onValueChange = { description = it },
                            label = { Text(text = "Description") },
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
                                viewModel.editProfile(username ?: "", description ?: "")
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