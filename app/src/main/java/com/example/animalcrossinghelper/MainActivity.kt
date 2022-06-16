package com.example.animalcrossinghelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.animalcrossinghelper.api.RetrofitHelper
import com.example.animalcrossinghelper.databinding.ActivityMainBinding
import com.example.animalcrossinghelper.model.BugModel
import com.example.animalcrossinghelper.model.FishModel
import com.example.animalcrossinghelper.model.FossilModel
import com.example.animalcrossinghelper.model.SeaCreatureModel
import com.example.animalcrossinghelper.room.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    private val fishDao: FishDao by inject()
    private val bugDao: BugDao by inject()
    private val seaCreatureDao: SeaCreatureDao by inject()
    private val fossilDao: FossilDao by inject()
    private val prefs: SharedPreferencesHelper by inject()
    private val retrofitHelper: RetrofitHelper by inject()

    init {
        KTP.openRootScope().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KTP.openRootScope().inject(this)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        if (prefs.getRememberMe() && prefs.getIsLogged()) navController.navigate(
            R.id.mainFragment
        )
        runBlocking {
            launch {
                withContext(Dispatchers.IO) { //todo посмотреть как заменить на реактивщину
                    checkForIsNotEmpty()
                }
            }
        }
    }

    private fun checkForIsNotEmpty() { //todo обработать отсутствие интернета (повторный запрос когда он появляется + снекбар с ошибкой)
        if (fishDao.getAll().isEmpty()) {
            downloadFish()
        }
        if (bugDao.getAll().isEmpty()) {
            downloadBugs()
        }
        if (seaCreatureDao.getAll().isEmpty()) {
            downloadSeaCreatures()
        }
        if (fossilDao.getAll().isEmpty()) {
            downloadFossils()
        }
    }

    private fun downloadFish() { //todo перенести логику во вьюмодель
        val fishFlowable: Flowable<List<FishModel>> = retrofitHelper.getApi().getFish()
        fishFlowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSubscriber<List<FishModel>>() {
                override fun onNext(t: List<FishModel>?) {
                    if (t != null) {
                        for (i in t) {
                            val name = i.name?.name_EUru!!
                            val location = i.availability?.location!!
                            val rarity = i.availability?.rarity!!
                            val price = i.price!!
                            val iconUri = i.icon_uri!!
                            val months = mutableListOf<String>()
                            val time = mutableListOf<String>()
                            for (j in i.availability!!.month_array_northern!!) {
                                months.add(j)
                            }
                            for (j in i.availability!!.time_array!!) {
                                time.add(j)
                            }
                            val newFish = Fish(
                                name = name,
                                location = location,
                                rarity = rarity,
                                monthArray = months,
                                timeArray = time,
                                price = price,
                                iconUri = iconUri,
                                userId = 0
                            )
                            runBlocking {
                                launch {
                                    withContext(Dispatchers.IO) {
                                        fishDao.insert(newFish)
                                    }
                                }
                            }
                        }
                    }
                }

                override fun onError(t: Throwable?) {
                    if (t != null) {
                        Log.d(TAG, "onError: ${t.message}")
                    }
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete")
//                    bdTest()
                }
            })
    }

    private fun downloadBugs() { //todo перенести логику во вьюмодель
        val bugModelFlowable: Flowable<List<BugModel>> = retrofitHelper.getApi().getBugs()
        bugModelFlowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSubscriber<List<BugModel>>() {
                override fun onNext(t: List<BugModel>?) {
                    if (t != null) {
                        for (i in t) {
                            val name = i.name?.name_EUru!!
                            val location = i.availability?.location!!
                            val rarity = i.availability?.rarity!!
                            val price = i.price!!
                            val iconUri = i.icon_uri!!
                            val months = mutableListOf<String>()
                            val time = mutableListOf<String>()
                            for (j in i.availability!!.month_array_northern!!) {
                                months.add(j)
                            }
                            for (j in i.availability!!.time_array!!) {
                                time.add(j)
                            }
                            val newBug = Bug(
                                id = 0,
                                name = name,
                                location = location,
                                rarity = rarity,
                                monthArray = months,
                                timeArray = time,
                                price = price,
                                iconUri = iconUri,
                                userId = 0
                            )
                            runBlocking {
                                launch {
                                    withContext(Dispatchers.IO) {
                                        bugDao.insert(newBug)
                                    }
                                }
                            }
                        }
                    }
                }

                override fun onError(t: Throwable?) {
                    if (t != null) {
                        Log.d(TAG, "onError: ${t.message}")
                    }
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                }
            })
    }

    private fun downloadSeaCreatures() { //todo перенести логику во вьюмодель
        val seaCreatureFlowable: Flowable<List<SeaCreatureModel>> =
            retrofitHelper.getApi().getSeaCreatures()
        seaCreatureFlowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSubscriber<List<SeaCreatureModel>>() {
                override fun onNext(t: List<SeaCreatureModel>?) {
                    if (t != null) {
                        for (i in t) {
                            val name = i.name?.name_EUru!!
                            val price = i.price!!
                            val iconUri = i.icon_uri!!
                            val months = mutableListOf<String>()
                            val time = mutableListOf<String>()
                            for (j in i.availability!!.month_array_northern!!) {
                                months.add(j)
                            }
                            for (j in i.availability!!.time_array!!) {
                                time.add(j)
                            }
                            val newSeaCreature = SeaCreature(
                                id = 0,
                                name = name,
                                monthArray = months,
                                timeArray = time,
                                price = price,
                                iconUri = iconUri,
                                userId = 0
                            )
                            runBlocking {
                                launch {
                                    withContext(Dispatchers.IO) {
                                        seaCreatureDao.insert(newSeaCreature)
                                    }
                                }
                            }
                        }
                    }
                }

                override fun onError(t: Throwable?) {
                    if (t != null) {
                        Log.d(TAG, "onError: ${t.message}")
                    }
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                }
            })
    }

    private fun downloadFossils() { //todo перенести логику во вьюмодель
        val fossilFlowable: Flowable<List<FossilModel>> = retrofitHelper.getApi().getFossils()
        fossilFlowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSubscriber<List<FossilModel>>() {
                override fun onNext(t: List<FossilModel>?) {
                    if (t != null) {
                        for (i in t) {
                            val name = i.name?.name_EUru!!
                            val price = i.price!!
                            val newFossil = Fossil(
                                id = 0,
                                name = name,
                                price = price,
                                userId = 0
                            )
                            runBlocking {
                                launch {
                                    withContext(Dispatchers.IO) {
                                        fossilDao.insert(newFossil)
                                    }
                                }
                            }
                        }
                    }
                }

                override fun onError(t: Throwable?) {
                    if (t != null) {
                        Log.d(TAG, "onError: ${t.message}")
                    }
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                }
            })
    }

    override fun onBackPressed() {
        //do nothing
    }
}