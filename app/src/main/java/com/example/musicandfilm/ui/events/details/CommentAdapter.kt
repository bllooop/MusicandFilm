package com.example.musicandfilm.ui.events.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicandfilm.R
import com.example.musicandfilm.models.Comments
import com.google.firebase.database.FirebaseDatabase

class CommentAdapter( private val comments : List<Comments>):
RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    class CommentViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val bundle = Bundle()
        val database = FirebaseDatabase.getInstance("https://musicandfilm-5497b-default-rtdb.europe-west1.firebasedatabase.app")
        val comment = database.getReference("Comments")
        fun bindComment(comments: Comments, context: Context) {
            val comment_name = itemView.findViewById<TextView>(R.id.comment_name)
            val comment_text = itemView.findViewById<TextView>(R.id.comment_text)
            comment_name.text = comments.email
            comment_text.text = comments.text

            if (comments.type == "movie") {
                comment_text.text = comments.stars + "/5 \n" + comments.text
            }
        }
    }
    override fun onBindViewHolder(holder: CommentAdapter.CommentViewHolder, position: Int) {
        var context: Context = holder.itemView.context
        holder.bindComment(comments.get(position),context)
    }
    override fun getItemCount(): Int {
        return comments.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.CommentViewHolder {
        return CommentAdapter.CommentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        )
    }
}