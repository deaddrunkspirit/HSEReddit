package com.example.hsereddit.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.hsereddit.R
import com.example.hsereddit.data.model.AuthorizedData
import com.example.hsereddit.data.model.Result
import com.example.hsereddit.data.model.Section
import com.example.hsereddit.databinding.FragmentAddThemeBinding
import com.example.hsereddit.network.Requests
import com.example.hsereddit.network.RestApiService
import kotlinx.coroutines.runBlocking
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [AddThemeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddThemeFragment : Fragment() {
    private var _binding: FragmentAddThemeBinding? = null
    private val binding get() = _binding!!
    private var token: String? = null
    private val api: RestApiService = RestApiService()
    private var auth: AuthorizedData? = null
    private var sectionCreated: Section? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddThemeBinding.inflate(inflater, container, false)
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
//        TODO val token = getValue("authToken")
        val token = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZW5tb3NvbGtvdiIsImV4cCI6MTY1MDYyNjU1NCwiaWF0IjoxNjUwNTM2NTU0LCJzY29wZSI6IlJPTEVfVVNFUiJ9.KO_OIfEd2W6u2PRsuo1AgwvSsq4fjIV8Dmzo83pK5hV7aRAlOOX9Ncvzn66WGCXiG3WHkTku4npP3qcdniweOv1ziiSo9UxdkprSUmIeYYA8lTNFR5c5yZTmSU5YEWWPK9mYyUku4-_ZiBvAiIMuK5AGTNGqmrMRGtJfrAOvfn9OpcnsNS18h1pLoO0LQkfCPDx5x_DnREJm5h4A-lV8AwmkxErZELPDGf80sU4HR2S_5gp8gM6T0dyGNH7JAKUDMrA7AmTwPeyq_q5HgH9jedIakYoizSwa-dxCyUsuodqIdUCbiKpcE1o_y4H78UAO5_EK_RXCO_2OUrp7dh-fvg"
        binding.saveThemeButton.setOnClickListener {
            runBlocking {
                val theme: String = binding.addThemeInput.text.toString()
                api.createSection(
                    token, Requests.CreateSectionRequest(
                        description = "fuck",
                        id = 0,
                        numberOfPosts = 0,
                        name = theme
                    )
                ) {
                        val section: Result<Section> = if (it == null) {
                            Result.Error(IllegalArgumentException("EMPTY RESULT"))
                        } else {
                            Result.Success(it)
                        }

                        if (section is Result.Success) {
                            sectionCreated = section.data
                            findNavController().navigate(R.id.action_addThemeFragment_to_addFragment)
                        } else {
                            Toast.makeText(context, "Theme already exist . . .", Toast.LENGTH_LONG)
                                .show()
                        }
                }
            }
        }
    }


    private fun storeKeyPair(key: String, value: String) {
        val prefs: SharedPreferences? = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs!!.edit()
        editor.putString(key, value)
        editor.commit()
    }


    private fun getValue(key: String): String {
        val prefs: SharedPreferences? = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        return prefs!!.getString(key, key).toString()
    }
}