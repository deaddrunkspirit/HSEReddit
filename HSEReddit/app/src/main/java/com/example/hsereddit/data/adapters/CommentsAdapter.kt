package com.example.hsereddit.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hsereddit.R
import com.example.hsereddit.data.model.Comment
import com.example.hsereddit.databinding.CommentFeedViewBinding

class CommentsAdapter: RecyclerView.Adapter<CommentsAdapter.CommentHolder>() {
    private val commentsList = mutableListOf<Comment>()

    class CommentHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = CommentFeedViewBinding.bind(view)
        fun bind(comment: Comment) = with(binding) {
            commentAuthorUsername.text = comment.username.toString()
            commentTextView.text = comment.text
            commentDate.text = formatDate(comment.date)
        }

        private fun formatDate(str: String): String {
            // TODO format date
            return str
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        return CommentHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.comment_feed_view, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        holder.bind(commentsList[position])
    }

    override fun getItemCount(): Int {
        return commentsList.size
    }

    fun addComments(comments: Collection<Comment>) {
        commentsList.addAll(comments)
        notifyDataSetChanged()
    }
}