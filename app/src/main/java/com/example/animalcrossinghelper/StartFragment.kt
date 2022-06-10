package com.example.animalcrossinghelper

import android.content.SharedPreferences
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

    val TAG = "StartFragment"

    lateinit var model: MainViewModel //todo перенести на di
    lateinit var binding: FragmentStartBinding
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper //todo перенести на di

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false)
        val handler = StartFragmentHandler()
        binding.handler = handler
        model = ViewModelProviders.of(requireActivity())[MainViewModel::class.java]
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
        return binding.root
    }

    inner class StartFragmentHandler {
        fun register(view: View) {
            //todo перенести логику в вм
            //TODO: логика
        }

        fun signIn(view: View) {
            //todo перенести логику в вм
            //TODO: логика
            var isLogged = false //todo если логин прошел успешно, поменять на тру
            sharedPreferencesHelper.putIsLogged(isLogged)
        }

        fun rememberMe(view: View, isEnabled: Boolean) {
            //todo перенести логику в вм
            sharedPreferencesHelper.putRememberMe(isEnabled)
        }

        fun showPassword(view: View, isEnabled: Boolean) {
            //todo перенести логику в вм
            if (isEnabled) {
                binding.editPassword.show()
                binding.editPasswordRepeat.show()
            } else {
                binding.editPassword.hide()
                binding.editPasswordRepeat.hide()
            }
            binding.editPassword.moveCursorToEnd(binding.editPassword)
            binding.editPasswordRepeat.moveCursorToEnd(binding.editPasswordRepeat)
        }
    }
}