package com.example.hsereddit.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.hsereddit.R
import com.example.hsereddit.data.adapters.NoFilterAdapter
import com.example.hsereddit.data.model.Result
import com.example.hsereddit.data.model.Section
import com.example.hsereddit.databinding.FragmentAddPostBinding
import com.example.hsereddit.network.Requests
import com.example.hsereddit.network.RestApiService


/**
 * A simple [Fragment] subclass.
 * Use the [AddPostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPostFragment : Fragment() {

    private val api: RestApiService = RestApiService()
    private var _binding: FragmentAddPostBinding? = null
    private var sectionList: ArrayList<Section> = arrayListOf()
    val getSectionsResult: LiveData<ArrayList<String>> = MutableLiveData<ArrayList<String>>(
        arrayListOf())

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.postSaveButton.setOnClickListener {
            if (checkFieldsBeforeCreatingPost()) {
                val themeId = getSectionId(binding.postThemeTextInput.text.toString())
                val data = Requests.CreatePostRequest(
                    content = binding.postTextInput.text.toString(),
                    postId = 0,
                    postTitle = binding.postTitleInput.text.toString(),
                    sectionId = themeId
                )
                Log.i("POST POST", "${data.sectionId} ${data.postId} ${data.postTitle} ${data.content}")
                api.createPost(data) { }
                Toast.makeText(context, "Post created", Toast.LENGTH_SHORT)
                findNavController().navigate(R.id.action_addPostFragment_to_addFragment)
            }
        }

        getSectionsResult.observe(viewLifecycleOwner, Observer {
            binding.postThemeTextInput.setAdapter(
                NoFilterAdapter(
                    requireContext(),
                    R.layout.post_theme_dropdown_item,
                    it
                )
            )
        })
        api.getAllSection(0) {
            val response = if (it == null) {
                Result.Error(IllegalArgumentException("EMPTY RESULT"))
            } else {
                Result.Success(it)
            }
            if (response is Result.Success) {
                val collection = response.data.map { x -> x.name }
                Log.i("ADD NAMES", collection.toString())
                getSectionsResult.value?.addAll(collection)
                sectionList.addAll(response.data)
                (binding.postThemeTextInput.adapter as NoFilterAdapter<String>).addItems(collection)
            } else {
                Toast.makeText(context, "Post cannot be created", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_addPostFragment_to_addFragment)
            }
        }
    }

    private fun checkFieldsBeforeCreatingPost(): Boolean{
        val text = binding.postTextInput.text.toString()
        val title = binding.postTitleInput.text.toString()
        val theme = binding.postThemeTextInput.text.toString()
        if (text == "") {
            Toast.makeText(context, "Please, input post text", Toast.LENGTH_LONG)
            return false
        }
        return when {
            title == "" -> {
                Toast.makeText(context, "Please, input title", Toast.LENGTH_LONG)
                false
            }
            theme == "" -> {
                Toast.makeText(context, "Please, input theme", Toast.LENGTH_LONG)
                false
            }
            else -> true
        }
    }

    private fun getSectionId(str: String): Int {
        for (s: Section in sectionList) {
            if (s.name == str) {
                return s.id
            }
        }
        return 5
    }


}