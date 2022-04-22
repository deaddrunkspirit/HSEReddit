package com.example.hsereddit.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hsereddit.R

/**
 * A simple [Fragment] subclass.
 * Use the [SplashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val refreshToken = getRefreshToken()
//        Log.i("REFRESH_TOKEN", refreshToken ?: "null")
//        if (refreshToken.isNullOrEmpty()) {
//            Log.i("NAVIGATION", "splash to login")
//            findNavController().navigate(
//                R.id.action_splashFragment_to_loginFragment
//            )
//        } else {

            Log.i("NAVIGATION", "splash to home")
            findNavController().navigate(
                R.id.action_splashFragment_to_homeFragment
            )
//        }
    }

    private fun getRefreshToken(): String? {
        val prefs = activity?.getPreferences(Context.MODE_PRIVATE)
        return prefs?.getString("refreshToken", null)
    }

    private fun storeKeyPair(key: String, value: String) {
        val prefs: SharedPreferences? = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs!!.edit()
        editor.putString(key, value)
        editor.commit()
    }
}