package com.example.hsereddit.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hsereddit.R
import com.example.hsereddit.data.model.Post
import com.example.hsereddit.databinding.FragmentEditPostBinding
import com.example.hsereddit.databinding.FragmentPostBinding
import com.example.hsereddit.network.RestApiService

/**
 * A simple [Fragment] subclass.
 * Use the [EditPostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditPostFragment : Fragment() {
    private var _binding: FragmentEditPostBinding? = null
    private val binding get() = _binding!!
    private var postId: Int? = null
    private val api: RestApiService = RestApiService()
    private var content: String? = null
    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postId = getValue("postId").toInt()
        content = getValue("postContent")
        title = getValue("postTitle")
        binding.editPostContentText.setText(content)
        binding.editPostContentTheme.text = getValue("postSectionName")
        binding.editPostContentTitle.setText(title)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_post_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.title) {
            "Save" -> {
                val newContent: String = binding.editPostContentText.text.toString()
                val newTitle: String = binding.editPostContentTitle.text.toString()
                if (newContent != "" && newContent != content) {
                    api.updatePostContent(postId!!, newContent) { }
                }
                if (newTitle != "" && newTitle != title) {
                    api.updatePostTitle(postId!!, newTitle) { }
                }
                findNavController().navigate(R.id.action_editPostFragment_to_homeFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getValue(key: String): String {
        val prefs: SharedPreferences? = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        return prefs!!.getString(key, key).toString()
    }

}