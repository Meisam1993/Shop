package com.example.shop.feature.detail.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.databinding.ItemCommentBinding
import com.example.shop.services.data.dataclasses.Comment

class CommentAdapter(private val showAll: Boolean = false) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    var comments = ArrayList<Comment>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class CommentViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindComment(comment: Comment) {
            binding.title.text = comment.title
            binding.date.text = comment.date
            binding.author.text = comment.author.email
            binding.content.text = comment.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemCommentBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (comments.size > 3 && !showAll) 3 else comments.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindComment(comments[position])
    }
}