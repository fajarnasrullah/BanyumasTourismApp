package com.jer.banyumastourismapp.presentation.payment

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.midtrans.sdk.uikit.api.model.TransactionResult
import com.midtrans.sdk.uikit.external.UiKitApi
import com.midtrans.sdk.uikit.internal.util.UiKitConstants
import kotlinx.coroutines.delay


@Composable
fun PaymentLoadingScreen(modifier: Modifier = Modifier, viewModel: PaymentViewModel, snapToken: String?) {

    val context = LocalContext.current
    val activity = context as? Activity

    val responseThx by viewModel.response.collectAsState()
    val snap by viewModel.snapToken.collectAsState()
    val message = viewModel.message.collectAsState()


    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        if (result?.resultCode == RESULT_OK) {
            result.data?.let {
                val transactionResult = it.getParcelableExtra<TransactionResult>(UiKitConstants.KEY_TRANSACTION_RESULT)
                Toast.makeText(context,"${transactionResult?.transactionId}", Toast.LENGTH_LONG).show()
            }
        }
    }

    LaunchedEffect(Unit) {
        if (!snapToken.isNullOrEmpty()) {
            delay(2000)
            activity?.let {
                UiKitApi.getDefaultInstance()?.startPaymentUiFlow(
                    it,
                    launcher,
                    snapToken
                )
            }
        } else {
            Log.e("Payment", "Token belum siap atau null: ${snapToken}")
        }
    }

    Box(
        contentAlignment = androidx.compose.ui.Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {

        Text(text = "Your payment on process..", fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
    }
}