package com.example.animalcrossinghelper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.animalcrossinghelper.databinding.FragmentUserBinding
import com.example.animalcrossinghelper.room.User
import com.example.animalcrossinghelper.room.UserDao
import com.example.animalcrossinghelper.utils.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject

class UserFragment : Fragment() {

    val TAG = "UserFragment"

    lateinit var binding: FragmentUserBinding
    private val prefs: SharedPreferencesHelper by inject()
    private val userDao: UserDao by inject()

    init {
        KTP.openRootScope().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        val handler = UserFragmentHandler()
        binding.handler = handler
        binding.userEditLogin.setText(prefs.getActualLogin())
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
                        userDao.getById(prefs.getIdOfLoggedUser()).password //todo посмотреть как заменить это на реактивщину
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
                    prefs.getIdOfLoggedUser(),
                    binding.root, userDao
                )
            ) return
            if (checkFieldsForChange(login, password, binding.root, userDao)) return
            val updatedUser = User(prefs.getIdOfLoggedUser(), login, password)
            runBlocking {
                launch {
                    withContext(Dispatchers.IO) {
                        userDao.update(updatedUser) //todo посмотреть как заменить это на реактивщину
                    }
                }
            }
            Snackbar.make(binding.root, R.string.login_and_password_changed, Snackbar.LENGTH_LONG)
                .show()
            navigate(R.id.hubFragment)
        }

        fun logout(view: View) {
            closeKeyboard(activity as AppCompatActivity)
            prefs.putRememberMe(false)
            navigate(R.id.startFragment)
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
