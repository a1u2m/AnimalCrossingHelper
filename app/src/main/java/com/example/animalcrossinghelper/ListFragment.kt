package com.example.animalcrossinghelper

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.animalcrossinghelper.databinding.FragmentListBinding
import com.example.animalcrossinghelper.databinding.FragmentStartBinding
import com.example.animalcrossinghelper.utils.navigate
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject

class ListFragment : Fragment() {

    val TAG = "ListFragment"

    lateinit var binding: FragmentListBinding
    private val model: MainViewModel by inject()

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
        Log.d(TAG, "${model.category.value}")
        return binding.root
    }

    inner class ListFragmentHandler {
        fun goBack(view: View) {
            navigate(R.id.hubFragment)
        }
    }
}