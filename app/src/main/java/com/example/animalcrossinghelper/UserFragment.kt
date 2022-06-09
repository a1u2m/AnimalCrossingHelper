package com.example.animalcrossinghelper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.animalcrossinghelper.databinding.FragmentStartBinding
import com.example.animalcrossinghelper.databinding.FragmentUserBinding

class UserFragment : Fragment() {

    lateinit var binding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        val handler = UserFragmentHandler()
        binding.handler = handler
        return binding.root
    }

    inner class UserFragmentHandler {

    }
}