package com.example.hsereddit.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hsereddit.R
import com.example.hsereddit.data.adapters.HomeScreenAdapter
import com.example.hsereddit.data.model.AuthorizedData
import com.example.hsereddit.data.model.Post
import com.example.hsereddit.data.paging.PostPagingOwnedSource
import com.example.hsereddit.data.paging.PostPagingSubscriptionSource
import com.example.hsereddit.databinding.FragmentHomeBinding
import com.example.hsereddit.network.RestApiService
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private var homeScreenAdapter: HomeScreenAdapter = HomeScreenAdapter()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var api: RestApiService = RestApiService()

//    private val subscriptionFlow = Pager(
//        PagingConfig(pageSize = 50)
//    ) {
//        PostPagingSubscriptionSource(api)
//    }.flow
    private val myPostsFlow: Flow<PagingData<Post>> = Pager(
        PagingConfig(pageSize = 50)
    ) {
        PostPagingOwnedSource(api, getValue("loginUsername"))
    }.flow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewHome.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = homeScreenAdapter
        }
        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(findNavController())

        viewLifecycleOwner.lifecycleScope.launch {
            myPostsFlow.collectLatest { data ->
                homeScreenAdapter.submitData(data)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_filter_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.filter_my_posts) {
            viewLifecycleOwner.lifecycleScope.launch {
                myPostsFlow.collectLatest { data ->
                    homeScreenAdapter.submitData(data)
                }
            }
            return true
        }
        else if (item.itemId == R.id.filter_subscription_posts) {
//            lifecycleScope.launch {
//                subscriptionFlow.collectLatest { data ->
//                    homeScreenAdapter.submitData(data)
//                }
//            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun getValue(key: String): String {
        val prefs: SharedPreferences? = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        return prefs!!.getString(key, key).toString()
    }
}