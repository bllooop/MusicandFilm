package com.example.musicandfilm.ui.movie

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicandfilm.models.movies.FavoriteMovie
import com.example.musicandfilm.R
import com.example.musicandfilm.models.movies.Movie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class MovieAdapter(
    private val movies: List<Movie>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){
    class MovieViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        val favorite: ImageButton = view.findViewById(R.id.fav)
        val bundle = Bundle()
        val database = FirebaseDatabase.getInstance("https://musicandfilm-5497b-default-rtdb.europe-west1.firebasedatabase.app")
        var user = FirebaseAuth.getInstance().currentUser
        var userid = user!!.uid
        val favMovie = database.getReference("Movies")
        fun bindMovie(movie: Movie, context: Context) {
            val movie_title = itemView.findViewById<TextView>(R.id.movie_title)
            val movie_release_date = itemView.findViewById<TextView>(R.id.movie_release_date)
            val movie_poster = itemView.findViewById<ImageView>(R.id.movie_poster)
            movie_title.text = movie.title
            movie_release_date.text = movie.release
            Glide.with(itemView).load(IMAGE_BASE + movie.poster).into(movie_poster)
            bundle.putString("id", movie.id)
            itemView.setOnClickListener {
                itemView.findNavController()
                    .navigate(R.id.action_navigation_main_to_navigation_details, bundle)
            }
            favorite.setOnClickListener {
            val mMovie = FavoriteMovie(userid,movie.id,movie.title,movie.poster,movie.release)
             favMovie.child(movie.id).setValue(mMovie)
                Toast.makeText(context,"Фильм добавлен в избранное", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false))
    }
    override fun getItemCount(): Int = movies.size
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        var context:Context=holder.itemView.context
        holder.bindMovie(movies.get(position),context)
        }
    }
