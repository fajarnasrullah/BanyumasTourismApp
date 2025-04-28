package com.jer.banyumastourismapp.presentation.payment

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.LocaleListCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.jer.banyumastourismapp.BuildConfig
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.data.remote.retrofit.RetrofitClient
import com.jer.banyumastourismapp.domain.model.TransactionRequest
import com.midtrans.sdk.uikit.api.model.CustomColorTheme
import com.midtrans.sdk.uikit.api.model.TransactionResult
import com.midtrans.sdk.uikit.external.UiKitApi
import com.midtrans.sdk.uikit.internal.util.UiKitConstants
import kotlinx.coroutines.launch

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)


        val baseUrl = BuildConfig.MIDTRANS_SANDBOX_BASE_URL
        val clientKey = BuildConfig.MIDTRANS_SANDBOX_CLIENT_KEY


        UiKitApi.Builder()
            .withMerchantClientKey(clientKey)
            .withContext(this)
            .withMerchantUrl(baseUrl)
            .enableLog(true)
            .withColorTheme(CustomColorTheme("#FFE51255", "#B61548", "#FFE51255")) // set theme. it will replace theme on snap theme on MAP ( optional)
            .build()

        setLocaleNew("id")


        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result?.resultCode == RESULT_OK) {
                result.data?.let {
                    val transactionResult = it.getParcelableExtra<TransactionResult>(UiKitConstants.KEY_TRANSACTION_RESULT)
                    Toast.makeText(this,"${transactionResult?.transactionId}", Toast.LENGTH_LONG).show()
                }
            }
        }


//        UiKitApi.getDefaultInstance().startPaymentUiFlow(
//            this@PaymentActivity,
//            launcher,
//            snapToken = sna // Snap Token
//        )

//        lifecycleScope.launch {
//            try {
//                val request = TransactionRequest(
//                    orderId = "order-${System.currentTimeMillis()}",
//                    grossAmount = 100000,
//                    userId = 1,
//                    fieldId = 1
//                )
//
//                val response = RetrofitClient.instance.createTransaction(request)
//
//                if (response.isSuccessful && response.body()?.message == "Transaksi berhasil dibuat") {
//                    val snapToken = response.body()?.token
//
//                    snapToken?.let {
//                        UiKitApi.getDefaultInstance().startPaymentUiFlow(
//                            this@MainActivity,
//                            launcher,
//                            it
//                        )
//                    }
//                } else {
//                    Toast.makeText(this@MainActivity, "Gagal buat transaksi", Toast.LENGTH_SHORT).show()
//                }
//            } catch (e: Exception) {
//                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
//            }
//        }


    }


    private fun setLocaleNew(languageCode: String?) {
        val locales = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(locales)
    }

}