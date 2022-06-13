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
import com.example.animalcrossinghelper.utils.closeKeyboard
import com.example.animalcrossinghelper.utils.hide
import com.example.animalcrossinghelper.utils.moveCursorToEnd
import com.example.animalcrossinghelper.utils.show
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
            if (checkFieldsForEmptiness(login, password, passwordRepeat)) return
            if (checkPasswordsForEquality(password, passwordRepeat)) return
            var list: List<User> = listOf()
            list = getUsersList(list)
            for (i in list) {
                if (login == i.login) {
                    Snackbar.make(binding.root, R.string.user_already_exists, Snackbar.LENGTH_LONG)
                        .show()
                    return
                }
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
            closeKeyboard(activity as AppCompatActivity)
            val login = binding.editLogin.text.toString()
            val password = binding.editPassword.text.toString()
            if (checkFieldsForEmptiness(login, password)) return
            var list: List<User> = listOf()
            list = getUsersList(list)
            for (i in list) {
                if (login == i.login && password == i.password) {
                    navController.navigate(R.id.mainFragment)
                    sharedPreferencesHelper.putIsLogged(true)
                    sharedPreferencesHelper.putActualLogin(login)
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

        private fun getUsersList(list: List<User>): List<User> {
            var usersList = list
            runBlocking {
                launch {
                    withContext(IO) {
                        usersList = userDao.getAll() //todo посмотреть как заменить это на реактивщину
                    }
                }
            }
            return usersList
        }

        private fun checkPasswordsForEquality(
            password: String,
            passwordRepeat: String
        ): Boolean {
            if (password != passwordRepeat) {
                Snackbar.make(binding.root, R.string.passwords_are_not_equal, Snackbar.LENGTH_LONG)
                    .show()
                return true
            }
            return false
        }

        private fun checkFieldsForEmptiness(
            login: String,
            password: String,
            passwordRepeat: String
        ): Boolean {
            if (login.isEmpty() || password.isEmpty() || passwordRepeat.isEmpty()) {
                Snackbar.make(
                    binding.root,
                    R.string.login_and_passwords_must_not_be_empty,
                    Snackbar.LENGTH_LONG
                ).show()
                return true
            }
            return false
        }

        private fun checkFieldsForEmptiness(
            login: String,
            password: String
        ): Boolean {
            if (login.isEmpty() || password.isEmpty()) {
                Snackbar.make(
                    binding.root,
                    R.string.login_and_passwords_must_not_be_empty,
                    Snackbar.LENGTH_LONG
                ).show()
                return true
            }
            return false
        }
    }
}