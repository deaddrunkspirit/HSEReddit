package com.example.hsereddit.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hsereddit.data.adapters.HomeScreenAdapter
import com.example.hsereddit.data.adapters.SearchScreenAdapter
import com.example.hsereddit.data.adapters.ThemeScreenAdapter
import com.example.hsereddit.data.paging.PostPagingSectionSource
import com.example.hsereddit.data.paging.PostPagingSubscriptionSource
import com.example.hsereddit.databinding.FragmentThemeBinding
import com.example.hsereddit.network.RestApiService
import kotlinx.android.synthetic.main.fragment_theme.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [ThemeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThemeFragment : Fragment() {
    private var _binding: FragmentThemeBinding? = null
    private val binding get() = _binding!!
    private var searchScreenAdapter = ThemeScreenAdapter()
    private val api: RestApiService = RestApiService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThemeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sectionId = getValue("sectionId").toInt()
        val sectionName = getValue("sectionName")
        val sectionFlow = Pager(
            PagingConfig(pageSize = 50)
        ) {
            PostPagingSectionSource(api, sectionId)
        }.flow
        binding.themePostsView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = searchScreenAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            sectionFlow.collectLatest { data ->
                searchScreenAdapter.submitData(data)
            }
        }

        binding.button.setOnClickListener {
            api.subscribeToSection(sectionId) { }
            Toast.makeText(context, "Subscribed on section $sectionName", Toast.LENGTH_SHORT)
        }
    }

    private fun getValue(key: String): String {
        val prefs: SharedPreferences? = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        return prefs!!.getString(key, key).toString()
    }
}