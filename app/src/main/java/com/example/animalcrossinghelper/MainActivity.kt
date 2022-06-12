package com.example.animalcrossinghelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.animalcrossinghelper.databinding.ActivityMainBinding
import com.example.animalcrossinghelper.model.Fish
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    lateinit var db: AppDatabase //todo перенести на di
    lateinit var userDao: UserDao //todo перенести на di
    lateinit var binding: ActivityMainBinding
    lateinit var model: MainViewModel //todo перенести на di
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper //todo перенести на di
    lateinit var navController: NavController //todo перенести на di
    lateinit var retrofitHelper: RetrofitHelper //todo перенести на di

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        model = ViewModelProviders.of(this)[MainViewModel::class.java]
        sharedPreferencesHelper = SharedPreferencesHelper(this)
        db = (application as App).getDatabase()
        userDao = db.userDao()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        if (sharedPreferencesHelper.getRememberMe() && sharedPreferencesHelper.getIsLogged()) navController.navigate(
            R.id.mainFragment
        )
        retrofitHelper = RetrofitHelper()
        downloadFish()

    }

    fun downloadFish() { //todo перенести логику во вьюмодель
        val fishFlowable: Flowable<List<Fish>> = retrofitHelper.getApi().getFish()
        fishFlowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSubscriber<List<Fish>>() {
                override fun onNext(t: List<Fish>?) {
                    if (t != null) {
                        for (i in t) {
                            Log.d(TAG, "имя: ${i.name?.name_EUru}")
                            Log.d(TAG, "иконка: ${i.icon_uri}")
                            Log.d(TAG, "цена: ${i.price}")
                            Log.d(TAG, "локация: ${i.availability?.location}")
                            Log.d(TAG, "редкость: ${i.availability?.rarity}")
                            for (j in i.availability?.month_array_northern!!) {
                                Log.d(TAG, "месяц: $j")
                            }
                            for (j in i.availability?.time_array!!) {
                                Log.d(TAG, "часы: $j")
                            }
                            Log.d(TAG, "-------------------------")
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

//    override fun onBackPressed() { //todo разкомментить когда сделаю экран логина чтоб можно было вылогиниваться пока я не сделал
//        //do nothing
//    }


}