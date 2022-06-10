package com.example.animalcrossinghelper

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.animalcrossinghelper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var db: AppDatabase //todo перенести на di
    lateinit var userDao: UserDao //todo перенести на di
    lateinit var binding: ActivityMainBinding
    lateinit var model: MainViewModel //todo перенести на di
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper //todo перенести на di
    lateinit var navController: NavController //todo перенести на di

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
    }


}