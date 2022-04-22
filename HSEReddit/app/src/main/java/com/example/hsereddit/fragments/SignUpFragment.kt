package com.example.hsereddit.fragments

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.hsereddit.databinding.FragmentSignUpBinding

import com.example.hsereddit.R
import com.example.hsereddit.data.auth.SignUpViewModel
import com.example.hsereddit.data.auth.SignUpViewModelFactory

class SignUpFragment : Fragment() {

    private lateinit var signUpViewModel: SignUpViewModel
    private var _binding: FragmentSignUpBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpViewModel = ViewModelProvider(this, SignUpViewModelFactory())
            .get(SignUpViewModel::class.java)

        val usernameEditText = binding.username
        val password1EditText = binding.password1
        val password2EditText = binding.password2
        val signUpButton = binding.signUp
        val loadingProgressBar = binding.loading

        signUpViewModel.signUpFormState.observe(viewLifecycleOwner,
            Observer { signUpFormState ->
                if (signUpFormState == null) {
                    return@Observer
                }
//                signUpButton.isEnabled = signUpFormState.isDataValid
                signUpFormState.usernameError?.let {
                    usernameEditText.error = getString(it)
                }
                signUpFormState.password1Error?.let {
                    password1EditText.error = getString(it)
                }
                signUpFormState.password2Error?.let {
                    password2EditText.error = getString(it)
                }
            })

        signUpViewModel.signUpResult.observe(viewLifecycleOwner,
            Observer { signUpResult ->
                signUpResult ?: return@Observer
                loadingProgressBar.visibility = View.GONE
                signUpResult.error?.let {
                    showSignUpFailed(it)
                }
                signUpResult.success?.let {
                    updateUi()
                }
            })

//        val afterTextChangedListener = object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//                // ignore
//            }
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                // ignore
//            }
//
//            override fun afterTextChanged(s: Editable) {
//                signUpViewModel.signUpDataChanged(
//                    usernameEditText.text.toString(),
//                    password1EditText.text.toString(),
//                    password2EditText.text.toString()
//                )
//            }
//        }
//        usernameEditText.addTextChangedListener(afterTextChangedListener)
//        password1EditText.addTextChangedListener(afterTextChangedListener)
//        password1EditText.setOnEditorActionListener { _, actionId, _ ->
//            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                signUpViewModel.login(
//                    usernameEditText.text.toString(),
//                    passwordEditText.text.toString()
//                )
//            }
//            false
//        }
//
//        password2EditText.addTextChangedListener(afterTextChangedListener)


        signUpButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            signUpViewModel.registration(
                usernameEditText.text.toString(),
                password1EditText.text.toString(),
                password2EditText.text.toString()
            )
        }
    }

    private fun updateUi() {
        val message = "Sign up Success. Now verify your e-mail, please"
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, message, Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_signUpFragment_to_splashFragment)
    }

    private fun showSignUpFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}