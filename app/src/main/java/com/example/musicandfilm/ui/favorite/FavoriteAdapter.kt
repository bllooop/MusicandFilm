package com.example.musicandfilm.ui.favorite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicandfilm.models.FavoriteMovie
import com.example.musicandfilm.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FavoriteAdapter( private val movies : List<FavoriteMovie>):
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    class FavoriteViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        val bundle = Bundle()
        val database = FirebaseDatabase.getInstance("https://musicandfilm-5497b-default-rtdb.europe-west1.firebasedatabase.app")
        val favMovie = database.getReference("Movies")
        val favorite: ImageButton = view.findViewById(R.id.fav)
        fun bindMovie(favoriteMovie: FavoriteMovie, context: Context) {
            val movie_title = itemView.findViewById<TextView>(R.id.movie_title)
            val movie_release_date = itemView.findViewById<TextView>(R.id.movie_release_date)
            val movie_poster = itemView.findViewById<ImageView>(R.id.movie_poster)
            movie_title.text = favoriteMovie.title
            movie_release_date.text = favoriteMovie.release_date
            Glide.with(itemView).load(IMAGE_BASE + favoriteMovie.poster_path).into(movie_poster)
            bundle.putString("id", favoriteMovie.id_movie)
            itemView.setOnClickListener {
                itemView.findNavController()
                    .navigate(R.id.action_navigation_favorite_to_navigation_details, bundle)
            }
            favorite.setOnClickListener {
                favMovie.child(favoriteMovie.id_movie).removeValue()
                Toast.makeText(context,"Фильм удален из избранного", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onBindViewHolder(holder: FavoriteAdapter.FavoriteViewHolder, position: Int) {
        var context: Context = holder.itemView.context
        holder.bindMovie(movies.get(position),context)
    }
    override fun getItemCount(): Int {
        return movies.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.FavoriteViewHolder {
        return FavoriteAdapter.FavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item_fav, parent, false)
        )
    }
}