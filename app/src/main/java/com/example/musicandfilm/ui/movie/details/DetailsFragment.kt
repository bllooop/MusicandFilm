package com.example.musicandfilm.ui.movie.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.musicandfilm.databinding.FragmentDetailsBinding
import com.example.musicandfilm.models.movies.MovieDetails


class DetailsFragment : Fragment() {
    private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)
        //return inflater.inflate(R.layout.fragment_main, container, false)
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = this.arguments
        val input = args?.getString("id")
        val id = input!!.toInt()
        val viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        viewModel.getSingleMovieData(id).observe(viewLifecycleOwner, Observer {
            bindMovie(it)
        })
    }
    fun bindMovie(movies: MovieDetails){
        val movie_title: TextView = binding.movieTitle
        val movie_runtime: TextView = binding.movieRuntime
        val movie_status: TextView = binding.movieStatus
        val movie_vote_average: TextView = binding.movieVoteAverage
        val movie_poster: ImageView = binding.moviePoster
        val movie_release_date: TextView = binding.movieReleaseDate
        movie_title.text = movies.title
        movie_release_date.text = movies.releaseDate
        movie_runtime.text = movies.runtime.toString() + " мин"
        movie_status.text = "Выпущен"
        movie_vote_average.text = movies.voteAverage.toString()
        Glide.with(this).load(IMAGE_BASE + movies.posterPath).into(movie_poster)
    }
}