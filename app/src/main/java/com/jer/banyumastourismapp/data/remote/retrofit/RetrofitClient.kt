package com.jer.banyumastourismapp.data.remote.retrofit

import com.jer.banyumastourismapp.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val instance: MidtransApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://merchant-server-midtrans-sandbox.vercel.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MidtransApiService::class.java)
    }
}