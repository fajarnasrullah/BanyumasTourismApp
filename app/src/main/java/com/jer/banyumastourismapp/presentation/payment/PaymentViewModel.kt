package com.jer.banyumastourismapp.presentation.payment

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jer.banyumastourismapp.data.remote.retrofit.RetrofitClient
import com.jer.banyumastourismapp.domain.model.TransactionRequest
import com.jer.banyumastourismapp.domain.model.TransactionResponse
import com.jer.banyumastourismapp.domain.usecase.tourism.TourismUseCase
import com.midtrans.sdk.uikit.external.UiKitApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(private val usecase: TourismUseCase) : ViewModel() {

    private val _response = MutableStateFlow<TransactionResponse?>(null)
    val response: StateFlow<TransactionResponse?> = _response

    private val _snapToken = MutableStateFlow<String?>(null)
    val snapToken: StateFlow<String?> = _snapToken

    private val _transferDone = MutableStateFlow(false)
    val transferDone: StateFlow<Boolean> = _transferDone

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    fun loadTransaction(): TransactionResponse? {
        return _response.asStateFlow().value
    }





   fun createTransaction(request: TransactionRequest) {
       viewModelScope.launch {
           try {
               val midtransResponse = usecase.createTransaction(request)
               if (midtransResponse.isSuccessful && midtransResponse.body()?.message == "Transaksi berhasil dibuat") {
                   _response.value = midtransResponse.body()
                   _snapToken.value = midtransResponse.body()?.token
                   _message.value = midtransResponse.body()?.message
                   _transferDone.value = true

                   Log.d("PaymentViewModel", "Succeed to Transaction: ${_response.value}")
                   Log.d("PaymentViewModel", "Succeed to Transaction: ${response.value}")
                   Log.d("PaymentViewModel", "TOKENNNNN: ${snapToken.value}")

               } else {
                   _transferDone.value = false
                   _message.value = "Transaksi Gagal"
                   Log.e("PaymentViewModel", "Failed to Transaction: ${midtransResponse.body()?.message}")
               }
           } catch (e: Exception) {
               _transferDone.value = false
               _message.value = "Transaksi Gagal"
               Log.e("PaymentViewModel", "Error: ${e.message}")
           }


       }

   }


}