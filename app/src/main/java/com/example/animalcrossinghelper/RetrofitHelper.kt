package com.example.animalcrossinghelper

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl("https://acnhapi.com")
        .build()

    private val api = retrofit.create(Api::class.java)

    fun getApi(): Api {
        return api
    }

}