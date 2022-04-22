package com.example.hsereddit.data.adapters

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hsereddit.R
import com.example.hsereddit.data.model.Post
import com.example.hsereddit.databinding.PostFeedViewBinding

class ThemeScreenAdapter:
    PagingDataAdapter<Post, ThemeScreenAdapter.PostHolder>(DiffUtilCallBack()) {

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
        holder.bind(getItem(position)!!)
    }


    class DiffUtilCallBack: DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.content == newItem.content
        }

    }
}
