package com.example.hsereddit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hsereddit.R
import com.example.hsereddit.data.adapters.CommentsAdapter
import com.example.hsereddit.data.model.Comment
import com.example.hsereddit.databinding.FragmentAddThemeBinding
import com.example.hsereddit.network.RestApiService
import kotlinx.android.synthetic.main.fragment_comments.*
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 * Use the [CommentsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommentsFragment : Fragment() {
    private var _binding: FragmentAddThemeBinding? = null
    private val binding get() = _binding!!
    private val api: RestApiService = RestApiService()
    private var postId: Int? = null
    val commentsList = ArrayList<Comment>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddThemeBinding.inflate(inflater, container, false)
        postId = savedInstanceState?.getInt("postId")
        if (postId == null) {
            cancelCommentsPage()
        } else {
            api.getAllCommentsForPost(postId!!) {
                if (it == null || it.count() == 0) {
                    cancelCommentsPage()
                } else {
                    commentsList.addAll(it)
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val commentsAdapter = CommentsAdapter()
        commentsAdapter.addComments(commentsList)
        comments_recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = CommentsAdapter()
        }

    }


    private fun cancelCommentsPage() {
        Toast.makeText(context, "Error finding post", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_commentsFragment_to_homeFragment)
    }




}