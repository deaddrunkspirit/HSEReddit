package com.example.hsereddit.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hsereddit.R
import com.example.hsereddit.data.adapters.HomeScreenAdapter
import com.example.hsereddit.data.adapters.SearchScreenAdapter
import com.example.hsereddit.data.model.Post
import com.example.hsereddit.data.paging.PostPagingTitleKeywordsSource
import com.example.hsereddit.databinding.FragmentHomeBinding
import com.example.hsereddit.databinding.FragmentSearchBinding
import com.example.hsereddit.network.RestApiService
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {

    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null
//    private var postsList: List<Post>? = null
    private var searchScreenAdapter: SearchScreenAdapter = SearchScreenAdapter()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var api: RestApiService = RestApiService()

    private val searchTitleFlow: Flow<PagingData<Post>> = Pager(
        PagingConfig(pageSize = 50)
    ) {
        PostPagingTitleKeywordsSource(api)
    }.flow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewSearchPosts.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = searchScreenAdapter
        }
        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(findNavController())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if (searchItem != null)
            searchView = searchItem.actionView as SearchView?
        if (searchView != null) {
            searchView!!.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    Log.i("onQueryTextChange", newText)
                    PostPagingTitleKeywordsSource.keywords = newText
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    Log.i("onQueryTextSubmit", query)
                    viewLifecycleOwner.lifecycleScope.launch {
                        searchTitleFlow.collectLatest { data ->
                            searchScreenAdapter.submitData(data)
                        }
                    }
                    return true
                }
            }
            searchView!!.setOnQueryTextListener(queryTextListener)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.action_search ->
                return false
            else -> {
            }
        }
        searchView!!.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }
}