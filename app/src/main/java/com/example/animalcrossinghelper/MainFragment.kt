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

class MainFragment : Fragment() {

    val TAG = "MainFragment"

    lateinit var binding: FragmentMainBinding
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper //todo перенести на di
    lateinit var navController: NavController //todo перенести на di И НАВЕРНЯКА МОЖНО ЕГО НЕ ПЛОДИТЬ ВЕЗДЕ, В МЕЙНЕ УЖЕ ЕСТЬ

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        val handler = MainFragmentHandler()
        binding.handler = handler
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
        binding.login.text = sharedPreferencesHelper.getActualLogin()
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        return binding.root
    }

    inner class MainFragmentHandler {
        fun openFishScreen(view: View) {
//            navController.navigate() //todo понять как на следующем экране грузить именно дб по рыбе, потом аналогично сделать по остальным категориям
        }
    }
}