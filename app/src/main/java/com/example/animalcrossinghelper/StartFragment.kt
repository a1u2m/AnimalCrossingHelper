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
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.animalcrossinghelper.databinding.FragmentStartBinding
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class StartFragment : Fragment() {

    val TAG = "StartFragment"

    lateinit var model: MainViewModel //todo перенести на di
    lateinit var binding: FragmentStartBinding
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper //todo перенести на di
    lateinit var db: AppDatabase //todo перенести на di
    lateinit var userDao: UserDao //todo перенести на di
    lateinit var navController: NavController //todo перенести на di И НАВЕРНЯКА МОЖНО ЕГО НЕ ПЛОДИТЬ ВЕЗДЕ, В МЕЙНЕ УЖЕ ЕСТЬ

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
        db = (requireActivity().application as App).getDatabase()
        userDao = db.userDao()
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        return binding.root
    }

    inner class StartFragmentHandler {
        fun register(view: View) {
            //todo перенести логику в вм
            //TODO: логика
            val login = binding.editLogin.text.toString()
            val password = binding.editPassword.text.toString()
            val passwordRepeat = binding.editPasswordRepeat.text.toString()
            if (password != passwordRepeat) return //todo добавить снекбар "пароли не совпадают или чето такое"
            var list: List<User> = listOf()
            runBlocking {
                launch {
                    withContext(IO) {
                        list = userDao.getAll() //todo посмотреть как заменить это на реактивщину
                    }
                }
            }
            for (i in list) {
                if (login == i.login) return //todo добавить снекбар "пользователь с таким логином уже существует"
            }
            val newUser = User(sharedPreferencesHelper.getId(), login, password)
            sharedPreferencesHelper.putNextFreeId(sharedPreferencesHelper.getId())
            runBlocking {
                launch {
                    withContext(IO) {
                        userDao.insert(newUser) //todo посмотреть как заменить это на реактивщину
                    }
                }
            }
        }

        fun signIn(view: View) {
            //todo перенести логику в вм
            //TODO: логика
            test
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