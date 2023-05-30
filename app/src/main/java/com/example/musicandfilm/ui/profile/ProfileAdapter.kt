package com.example.musicandfilm.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicandfilm.R
import com.example.musicandfilm.models.events.Event
import com.example.musicandfilm.models.events.FavoriteEvent
import com.example.musicandfilm.room.RecentHistory
import com.example.musicandfilm.ui.events.EventAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class ProfileAdapter (
    private val recents: List<RecentHistory>
) : RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>(){
    class ProfileViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val bundle = Bundle()

        fun bindRecents(recents: RecentHistory, context: Context) {
            var user = FirebaseAuth.getInstance().currentUser
                val title = itemView.findViewById<TextView>(R.id.title)
                val poster = itemView.findViewById<ImageView>(R.id.poster)
                val date = itemView.findViewById<TextView>(R.id.date)
                Glide.with(itemView).load(recents.image).into(poster)
                date.text = recents.date
                if (recents.type == "news") {
                    title.text = recents.title.subSequence(0, 15).toString() + "..."
                    bundle.putString("id", recents.type_id)
                    itemView.setOnClickListener {
                        itemView.findNavController()
                            .navigate(
                                R.id.action_navigation_profile_to_navigation_news_details,
                                bundle
                            )
                    }
                }
                if (recents.type == "events") {
                    title.text = recents.title.subSequence(0, 15).toString() + "..."
                    bundle.putString("id", recents.type_id)
                    itemView.setOnClickListener {
                        itemView.findNavController()
                            .navigate(
                                R.id.action_navigation_profile_to_navigation_event_details,
                                bundle
                            )
                    }
                }
                if (recents.type == "movies") {
                    title.text = recents.title
                    bundle.putString("id", recents.type_id)
                    itemView.setOnClickListener {
                        itemView.findNavController()
                            .navigate(
                                R.id.action_navigation_profile_to_navigation_movies_details,
                                bundle
                            )
                    }
                }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return ProfileViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recent_item, parent, false))
    }
    override fun getItemCount(): Int = recents.size
    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        var context: Context =holder.itemView.context
        holder.bindRecents(recents.get(position),context)
    }
}