package com.example.animalcrossinghelper

import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.animalcrossinghelper.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    lateinit var model: MainViewModel
    lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false)
        val handler = StartFragmentHandler()
        binding.handler = handler
        model = ViewModelProviders.of(requireActivity())[MainViewModel::class.java]
        return binding.root
    }

    inner class StartFragmentHandler {
        fun register(view: View) {
            //TODO: логика
        }

        fun signIn(view: View) {
            //TODO: логика
        }

        fun rememberMe(view: View) {
            //TODO: логика
        }

        fun showPassword(view: View, isEnabled: Boolean) {
            if (isEnabled) {
                binding.editPassword.inputType =
                    InputType.TYPE_CLASS_TEXT //todo в екстеншн это и то что ниже?
                binding.editPasswordRepeat.inputType = InputType.TYPE_CLASS_TEXT
            } else {
                binding.editPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.editPasswordRepeat.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

            binding.editPassword.text?.length?.let { binding.editPassword.setSelection(it) }
            binding.editPasswordRepeat.text?.length?.let {
                binding.editPasswordRepeat.setSelection(
                    it
                )
            }
        }
    }
}