package com.example.animalcrossinghelper.api

import com.example.animalcrossinghelper.model.BugModel
import com.example.animalcrossinghelper.model.FishModel
import com.example.animalcrossinghelper.model.FossilModel
import com.example.animalcrossinghelper.model.SeaCreatureModel
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET

interface Api {

    @GET("/v1a/fish/")
    fun getFish(): Flowable<List<FishModel>>

    @GET("/v1a/sea/")
    fun getSeaCreatures(): Flowable<List<SeaCreatureModel>>

    @GET("/v1a/bugs/")
    fun getBugs(): Flowable<List<BugModel>>

    @GET("/v1a/fossils/")
    fun getFossils(): Flowable<List<FossilModel>>

}