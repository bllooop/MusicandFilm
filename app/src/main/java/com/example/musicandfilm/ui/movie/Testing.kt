/* package com.example.musicandfilm.ui.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicandfilm.R
import com.example.musicandfilm.databinding.FragmentMainBinding
import com.example.musicandfilm.models.MovieDetails


class DetailsFragment : Fragment() {
    private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
    private var _binding: Fragment? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = Fragment.inflate(inflater, container, false)
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
        val movie_title: TextView = binding.movie_title
        movie_title.text = movies.title
        movie_release_date.text = movies.releaseDate
        movie_runtime.text = movies.runtime.toString() + " мин"
        movie_status.text = "Выпущен"
        movie_vote_average.text = movies.voteAverage.toString()
        Glide.with(this).load(IMAGE_BASE + movies.posterPath).into(movie_poster)
    }
}
*/

