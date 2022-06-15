package com.example.animalcrossinghelper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.animalcrossinghelper.databinding.FragmentStartBinding
import com.example.animalcrossinghelper.databinding.FragmentUserBinding
import com.example.animalcrossinghelper.room.AppDatabase
import com.example.animalcrossinghelper.room.User
import com.example.animalcrossinghelper.room.UserDao
import com.example.animalcrossinghelper.utils.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class UserFragment : Fragment() {

    lateinit var sharedPreferencesHelper: SharedPreferencesHelper //todo перенести на di
    lateinit var binding: FragmentUserBinding
    lateinit var navController: NavController //todo перенести на di И НАВЕРНЯКА МОЖНО ЕГО НЕ ПЛОДИТЬ ВЕЗДЕ, В МЕЙНЕ УЖЕ ЕСТЬ
    lateinit var db: AppDatabase //todo перенести на di
    lateinit var userDao: UserDao //todo перенести на di

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        val handler = UserFragmentHandler()
        binding.handler = handler
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        db = (requireActivity().application as App).getDatabase()
        userDao = db.userDao()
        binding.userEditLogin.setText(sharedPreferencesHelper.getActualLogin())
        binding.userEditPassword.setText(getPassword())
        binding.userEditPasswordRepeat.setText(getPassword())
        return binding.root
    }

    private fun getPassword(): String {
        var password = ""
        runBlocking {
            launch {
                withContext(Dispatchers.IO) {
                    password =
                        userDao.getById(sharedPreferencesHelper.getIdOfLoggedUser()).password //todo посмотреть как заменить это на реактивщину
                }
            }
        }
        return password
    }

    inner class UserFragmentHandler {
        fun save(view: View) {
            closeKeyboard(activity as AppCompatActivity)
            val login = binding.userEditLogin.text.toString()
            val password = binding.userEditPassword.text.toString()
            val passwordRepeat = binding.userEditPasswordRepeat.text.toString()
            if (checkFieldsForEmptiness(login, password, passwordRepeat, binding.root)) return
            if (checkPasswordsForEquality(password, passwordRepeat, binding.root)) return
            if (checkLoginForExisting(
                    login,
                    sharedPreferencesHelper.getIdOfLoggedUser(),
                    binding.root, userDao
                )
            ) return
            if (checkFieldsForChange(login, password, binding.root, userDao)) return
            val updatedUser = User(sharedPreferencesHelper.getIdOfLoggedUser(), login, password)
            runBlocking {
                launch {
                    withContext(Dispatchers.IO) {
                        userDao.update(updatedUser) //todo посмотреть как заменить это на реактивщину
                    }
                }
            }
            Snackbar.make(binding.root, R.string.login_and_password_changed, Snackbar.LENGTH_LONG)
                .show()
            navController.navigate(R.id.mainFragment)
        }

        fun logout(view: View) {
            closeKeyboard(activity as AppCompatActivity)
            sharedPreferencesHelper.putRememberMe(false)
            navController.navigate(R.id.startFragment)
        }

        fun showPassword(view: View, isEnabled: Boolean) {
            //todo перенести логику в вм
            if (isEnabled) {
                binding.userEditPassword.show()
                binding.userEditPasswordRepeat.show()
            } else {
                binding.userEditPassword.hide()
                binding.userEditPasswordRepeat.hide()
            }
            binding.userEditPassword.moveCursorToEnd(binding.userEditPassword)
            binding.userEditPasswordRepeat.moveCursorToEnd(binding.userEditPasswordRepeat)
        }
    }
}
