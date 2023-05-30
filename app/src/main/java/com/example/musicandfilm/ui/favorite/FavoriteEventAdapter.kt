package com.example.musicandfilm.ui.favorite

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
import com.example.musicandfilm.models.movies.FavoriteMovie
import com.example.musicandfilm.R
import com.example.musicandfilm.models.events.FavoriteEvent
import com.google.firebase.database.FirebaseDatabase

class FavoriteEventAdapter(private val events : List<FavoriteEvent>):
    RecyclerView.Adapter<FavoriteEventAdapter.FavoriteViewHolder>() {
    class FavoriteViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val IMAGE_BASE = "https://kudago.com/media/images/event/"
        val bundle = Bundle()
        val database = FirebaseDatabase.getInstance("https://musicandfilm-5497b-default-rtdb.europe-west1.firebasedatabase.app")
        val favEvent = database.getReference("Events")
        val favorite: ImageButton = view.findViewById(R.id.fav)
        fun bindEvent(favoriteEvent: FavoriteEvent, context: Context) {
            val event_title = itemView.findViewById<TextView>(R.id.event_title)
            val event_date = itemView.findViewById<TextView>(R.id.event_date)
            val event_image = itemView.findViewById<ImageView>(R.id.event_poster)
            event_title.text = favoriteEvent.title.capitalize()
            event_date.text = favoriteEvent.date
            Glide.with(itemView).load(favoriteEvent.image ).into(event_image)
            bundle.putString("id", favoriteEvent.id)
            itemView.setOnClickListener {
                itemView.findNavController()
                    .navigate(R.id.action_navigation_favorite_to_navigation_event_details, bundle)
            }
            favorite.setOnClickListener {
                favEvent.child(favoriteEvent.unix).removeValue()
                Toast.makeText(context,"Мероприятие удалено из избранного", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onBindViewHolder(holder: FavoriteEventAdapter.FavoriteViewHolder, position: Int) {
        var context: Context = holder.itemView.context
        holder.bindEvent(events.get(position),context)
    }
    override fun getItemCount(): Int {
        return events.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteEventAdapter.FavoriteViewHolder {
        return FavoriteEventAdapter.FavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.event_item_fav, parent, false)
        )
    }
}