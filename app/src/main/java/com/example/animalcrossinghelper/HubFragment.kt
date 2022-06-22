package com.example.animalcrossinghelper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.animalcrossinghelper.databinding.FragmentHubBinding
import com.example.animalcrossinghelper.utils.navigate
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject

class HubFragment : Fragment() {

    val TAG = "MainFragment"

    lateinit var binding: FragmentHubBinding
    private val prefs: SharedPreferencesHelper by inject()
    private val model: MainViewModel by inject()

    init {
        KTP.openRootScope().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_hub, container, false)
        val handler = HubFragmentHandler()
        binding.handler = handler
        binding.login.text = prefs.getActualLogin()
        return binding.root
    }

    inner class HubFragmentHandler {

        fun openUserScreen(view: View) {
            navigate(R.id.userFragment)
        }

        fun openFishScreen(view: View) {
            model.category = MainViewModel.Category.Fish
            navigate(R.id.listFragment)
        }

        fun openBugScreen(view: View) {
            model.category = MainViewModel.Category.Bug
            navigate(R.id.listFragment)
        }

        fun openSeaCreatureScreen(view: View) {
            model.category = MainViewModel.Category.SeaCreature
            navigate(R.id.listFragment)
        }

        fun openFossilScreen(view: View) {
            model.category = MainViewModel.Category.Fossil
            navigate(R.id.listFragment)
        }
    }
}