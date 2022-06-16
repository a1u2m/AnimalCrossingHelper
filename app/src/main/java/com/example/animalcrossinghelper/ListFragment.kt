package com.example.animalcrossinghelper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.animalcrossinghelper.databinding.FragmentListBinding
import com.example.animalcrossinghelper.databinding.FragmentStartBinding
import toothpick.ktp.KTP

class ListFragment : Fragment() {

    val TAG = "ListFragment"

    lateinit var binding: FragmentListBinding

    init {
        KTP.openRootScope().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        val handler = ListFragmentHandler()
        binding.handler = handler
        return binding.root
    }

    inner class ListFragmentHandler {

    }
}