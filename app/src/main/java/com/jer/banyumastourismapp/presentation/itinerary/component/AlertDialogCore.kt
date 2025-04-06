package com.jer.banyumastourismapp.presentation.itinerary.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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