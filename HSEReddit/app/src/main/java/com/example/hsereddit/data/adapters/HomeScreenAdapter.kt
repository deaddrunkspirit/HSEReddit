package com.example.hsereddit.data.adapters

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hsereddit.R
import com.example.hsereddit.data.model.Post
import com.example.hsereddit.databinding.PostFeedViewBinding


class HomeScreenAdapter:
    PagingDataAdapter<Post, HomeScreenAdapter.PostHolder>(DiffUtilCallBack()) {

    class PostHolder(view: View): RecyclerView.ViewHolder(view) {
        var postId = 0
        val binding = PostFeedViewBinding.bind(view)
        fun bind(post: Post) = with(binding) {
            postId = post.id
            authorView.text = post.username
            titleView.text = post.postTitle
            shortTextView.text = post.content
            themeView.text = post.sectionName
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): PostHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.post_feed_view,
                parent,
                false
            )
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
            holder.itemView.setOnClickListener { view ->
                val activity = view!!.context as AppCompatActivity
                storeKeyPair("postId", holder.postId.toString(), activity)
                storeKeyPair("username", item.username, activity)
                activity.findNavController(R.id.nav_host_fragment)
                    .navigate(R.id.action_homeFragment_to_postFragment)
            }
        }
    }


    class DiffUtilCallBack: DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.content == newItem.content
        }
    }

    private fun storeKeyPair(key: String, value: String, activity: AppCompatActivity) {
        val prefs: SharedPreferences? = activity.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs!!.edit()
        editor.putString(key, value)
        editor.commit()
    }
}
