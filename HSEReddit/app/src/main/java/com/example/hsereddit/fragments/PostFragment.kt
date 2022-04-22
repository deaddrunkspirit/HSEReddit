package com.example.hsereddit.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.hsereddit.R
import com.example.hsereddit.data.model.Post
import com.example.hsereddit.databinding.FragmentPostBinding
import com.example.hsereddit.network.Requests
import com.example.hsereddit.network.RestApiService
import kotlinx.android.synthetic.main.fragment_theme.*

/**
 * A simple [Fragment] subclass.
 * Use the [PostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PostFragment : Fragment() {
    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!
    private var rating: Int = 0
    private var postId: Int = 0
    private var data: Post? = null
    private val api: RestApiService = RestApiService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postId = getValue("postId").toInt()
        api.getPost(postId) {
//            if (it == null) {
//                cancelPost()
//            } else {
            if (it != null) {
                data = it
                storeKeyPair("sectionId", it.sectionId.toString())
                rating = it.reactionCount
                binding.ratingTextView.text = rating.toString()
                binding.postContentText.text = it.content
                binding.postContentTheme.text = it.sectionName
                binding.postContentTitle.text = it.postTitle
                binding.postCommentsNumber.text = it.commentCount.toString()
//            }
            }
        }

        binding.postContentTheme.setOnClickListener {
            storeKeyPair("sectionId", data?.sectionId.toString())
            findNavController().navigate(R.id.action_postFragment_to_themeFragment)
        }
        binding.likeButton.setOnClickListener {
            upvote()
        }
        binding.dislikeButton.setOnClickListener {
            downvote()
        }
        binding.postCommentsWrapper.setOnClickListener {
            storeKeyPair("postId", postId.toString())
            findNavController().navigate(R.id.action_postFragment_to_commentsFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val currentUserUsername: String = getValue("username")
        val postUsername: String = getValue("loginUsername")
        if (currentUserUsername == postUsername) {
            inflater.inflate(R.menu.post_owner_menu, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.title) {
            "Edit" -> {
                storeKeyPair("postTitle", binding.postContentTitle.text.toString())
                storeKeyPair("postContent", binding.postContentText.text.toString())
                storeKeyPair("postSectionName", binding.postContentTheme.text.toString())
                storeKeyPair("postId", postId.toString())
                findNavController().navigate(
                    R.id.action_postFragment_to_editPostFragment
                )
            }
            "Delete" -> {
                val alertDialog: AlertDialog? = activity?.let {
                    val builder = AlertDialog.Builder(it)
                    builder.apply {
                        setTitle("Delete")
                        setTitle("Do you want to delete this post?")
                        setPositiveButton("Ok",
                            DialogInterface.OnClickListener { dialog, id ->
                                api.deletePost(postId) {}
                                dialog.dismiss()
                                findNavController().navigate(
                                    R.id.action_postFragment_to_homeFragment
                                )
                            })
                        setNegativeButton("Cancel",
                            DialogInterface.OnClickListener { dialog, id ->
                                dialog.dismiss()
                            })
                    }
                    builder.create()
                }
                alertDialog?.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun upvote() {
        api.vote( Requests.VoteRequest(postId, "UPVOTE")) { }
        rating += 1
        binding.ratingTextView.text = "Rating: $rating"
        deactivateLikes()
    }

    private fun downvote() {
        api.vote( Requests.VoteRequest(postId, "DOWNVOTE")) { }
        rating -= 1
        binding.ratingTextView.text = "Rating: $rating"
        deactivateLikes()
    }

    private fun deactivateLikes() {
        binding.dislikeButton.isActivated = false
        binding.dislikeButton.isClickable = false
        binding.likeButton.isActivated = false
        binding.likeButton.isClickable = false
    }

    private fun cancelPost() {
        Toast.makeText(context, "Error finding post", Toast.LENGTH_LONG)
        findNavController().navigate(R.id.action_postFragment_to_homeFragment)
    }


    private fun getValue(key: String): String {
        val prefs: SharedPreferences? = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        return prefs!!.getString(key, key).toString()
    }

    private fun storeKeyPair(key: String, value: String) {
        val prefs: SharedPreferences? = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs!!.edit()
        editor.putString(key, value)
        editor.commit()
    }

}