package com.example.animalcrossinghelper

import com.example.animalcrossinghelper.model.Bug
import com.example.animalcrossinghelper.model.Fish
import com.example.animalcrossinghelper.model.Fossil
import com.example.animalcrossinghelper.model.SeaCreature
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET

interface Api {

    @GET("/v1a/fish/")
    fun getFish(): Flowable<List<Fish>>

    @GET("/v1a/sea/")
    fun getSeaCreatures(): Flowable<List<SeaCreature>>

    @GET("/v1a/bugs/")
    fun getBugs(): Flowable<List<Bug>>

    @GET("/v1a/fossils/")
    fun getFossils(): Flowable<List<Fossil>>

}