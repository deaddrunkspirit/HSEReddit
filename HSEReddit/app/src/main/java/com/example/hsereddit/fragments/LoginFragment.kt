package com.example.hsereddit.fragments

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.hsereddit.databinding.FragmentLoginBinding

import com.example.hsereddit.R
import com.example.hsereddit.data.auth.LoginViewModel
import com.example.hsereddit.data.auth.LoginViewModelFactory
import com.example.hsereddit.data.model.AuthorizedData
import kotlinx.coroutines.runBlocking

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        val usernameEditText = binding.username
        val passwordEditText = binding.password1
        val loginButton = binding.login
        val signUpButton = binding.signUp
        val loadingProgressBar = binding.loading

        loginViewModel.loginFormState.observe(viewLifecycleOwner,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                loginButton.isEnabled = loginFormState.isDataValid
                loginFormState.usernameError?.let {
                    usernameEditText.error = getString(it)
                }
                loginFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                }
            })

        loginViewModel.loginResult.observe(viewLifecycleOwner,
            Observer { loginResult ->
                loginResult ?: return@Observer
                loadingProgressBar.visibility = View.GONE
                loginResult.error?.let {
                    showLoginFailed(it)
                }
                loginResult.success?.let {
                    updateUiWithUser(it)
                    storeKeyPair("refreshToken", it.refreshToken)
                    storeKeyPair("authToken", it.authenticationToken)
                    storeKeyPair("loginUsername", it.username)
                    storeKeyPair("expiresAt", it.expiresAt.toString())
                    Log.i("TOKEN STORED", it.refreshToken)
                    Log.i("NAVIGATION", "login to home")
                    findNavController().navigate(
                        R.id.action_loginFragment_to_homeFragment
                    )
                }
            })
        loginButton.setOnClickListener {
            runBlocking {
                loginViewModel.login(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }

        signUpButton.setOnClickListener {
            Log.i("NAVIGATION", "login to signup")
            findNavController().navigate(
                R.id.action_loginFragment_to_signUpFragment
            )
        }
    }

    private fun updateUiWithUser(model: AuthorizedData) {
        val welcome = getString(R.string.welcome) + model.username
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
    }

    private fun storeKeyPair(key: String, value: String) {
        val prefs: SharedPreferences? = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs!!.edit()
        editor.putString(key, value)
        editor.commit()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}