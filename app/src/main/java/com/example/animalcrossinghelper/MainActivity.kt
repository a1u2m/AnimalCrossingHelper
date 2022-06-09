package com.example.animalcrossinghelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.animalcrossinghelper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var db: AppDatabase
    lateinit var userDao: UserDao
    lateinit var binding: ActivityMainBinding
    lateinit var model: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        model = ViewModelProviders.of(this)[MainViewModel::class.java]

        db = (application as App).getDatabase()
        userDao = db.userDao()


    }


}