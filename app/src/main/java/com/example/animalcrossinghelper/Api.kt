package com.example.animalcrossinghelper

import com.example.animalcrossinghelper.model.Fish
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET

interface Api {

    @GET("/v1a/fish/")
    fun getFish(): Flowable<List<Fish>>

}