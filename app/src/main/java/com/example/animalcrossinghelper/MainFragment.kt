package com.example.animalcrossinghelper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.animalcrossinghelper.databinding.FragmentMainBinding
import com.example.animalcrossinghelper.utils.navigate
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject

class MainFragment : Fragment() {

    val TAG = "MainFragment"

    lateinit var binding: FragmentMainBinding
    private val prefs: SharedPreferencesHelper by inject()

    init {
        KTP.openRootScope().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        val handler = MainFragmentHandler()
        binding.handler = handler
        binding.login.text = prefs.getActualLogin()
        return binding.root
    }

    inner class MainFragmentHandler {

        fun openUserScreen(view: View) {
            navigate(R.id.userFragment)
        }

        fun openFishScreen(view: View) {
//            navController.navigate() //todo понять как на следующем экране грузить именно дб по рыбе, потом аналогично сделать по остальным категориям
        }
    }
}