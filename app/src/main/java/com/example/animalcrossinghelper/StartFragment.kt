package com.example.animalcrossinghelper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.animalcrossinghelper.databinding.FragmentStartBinding
import com.example.animalcrossinghelper.room.AppDatabase
import com.example.animalcrossinghelper.room.User
import com.example.animalcrossinghelper.room.UserDao
import com.example.animalcrossinghelper.utils.*
import com.google.android.material.snackbar.Snackbar
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
            closeKeyboard(activity as AppCompatActivity)
            val login = binding.editLogin.text.toString()
            val password = binding.editPassword.text.toString()
            val passwordRepeat = binding.editPasswordRepeat.text.toString()
            if (checkFieldsForEmptiness(login, password, passwordRepeat, binding.root)) return
            if (checkPasswordsForEquality(password, passwordRepeat, binding.root)) return
            if (checkLoginForExisting(login, binding.root, userDao)) return
            val newUser = User(sharedPreferencesHelper.getNextFreeId(), login, password)
            sharedPreferencesHelper.putNextFreeId(sharedPreferencesHelper.getNextFreeId())
            runBlocking {
                launch {
                    withContext(IO) {
                        userDao.insert(newUser) //todo посмотреть как заменить это на реактивщину
                        Snackbar.make(
                            binding.root,
                            R.string.register_complete,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        fun signIn(view: View) {
            //todo перенести логику в вм
            closeKeyboard(activity as AppCompatActivity)
            val login = binding.editLogin.text.toString()
            val password = binding.editPassword.text.toString()
            if (checkFieldsForEmptiness(login, password, view)) return
            var list: List<User> = listOf()
            list = getUsersList(list, userDao)
            for (i in list) {
                if (login == i.login && password == i.password) {
                    navController.navigate(R.id.mainFragment)
                    sharedPreferencesHelper.putIsLogged(true)
                    sharedPreferencesHelper.putActualLogin(login)
                    sharedPreferencesHelper.putIdOfLoggedUser(i.id)
                    return
                } else if (login == i.login && password != i.password) {
                    Snackbar.make(
                        binding.root,
                        R.string.password_is_incorrect,
                        Snackbar.LENGTH_LONG
                    ).show()
                    return
                }
            }
            Snackbar.make(binding.root, R.string.user_is_absent, Snackbar.LENGTH_LONG).show()
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