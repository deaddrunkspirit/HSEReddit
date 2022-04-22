package com.example.hsereddit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hsereddit.R
import com.example.hsereddit.data.model.AuthorizedData
import com.example.hsereddit.databinding.FragmentAddBinding
import com.example.hsereddit.databinding.FragmentLoginBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private var auth: AuthorizedData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        auth = AuthorizedData(
//            username = savedInstanceState?.getString("username")!!,
//            expiresAt = Date(savedInstanceState.getString("expiresAt")!!),
//            authenticationToken = savedInstanceState.getString("authToken")!!,
//            refreshToken = savedInstanceState.getString("refreshToken")!!
//        )
        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(findNavController())
        binding.addPostButton.setOnClickListener {
            findNavController().navigate(R.id.action_addFragment_to_addPostFragment)
        }
        binding.addThemeButton.setOnClickListener {
            findNavController().navigate(R.id.action_addFragment_to_addThemeFragment)
        }
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        if (auth != null ) {
//            outState.putString("username", auth?.username)
//            outState.putString("authToken", auth?.authenticationToken)
//            outState.putString("refreshToken", auth?.refreshToken)
//            outState.putString("expiresAt", auth?.expiresAt.toString())
//        }
//    }
}