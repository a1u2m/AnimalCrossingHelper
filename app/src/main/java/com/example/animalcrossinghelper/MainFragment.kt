package com.example.animalcrossinghelper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.animalcrossinghelper.databinding.FragmentMainBinding
import com.example.animalcrossinghelper.databinding.FragmentStartBinding

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        val handler = MainFragmentHandler()
        binding.handler = handler
        return binding.root
    }

    inner class MainFragmentHandler {

    }
}