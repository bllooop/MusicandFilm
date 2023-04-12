package com.example.musicandfilm.ui.movie.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicandfilm.models.movies.MovieDetails
import com.example.musicandfilm.services.movie.MovieApiInterface
import com.example.musicandfilm.services.movie.MovieApiService
import com.example.musicandfilm.services.movie.MovieRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel : ViewModel() {
    var movie_detail = MutableLiveData<MovieDetails>()
    val movieRepository = MovieRepository()

    init {
        movie_detail=MutableLiveData()
    }

    fun getMovie(id: Int){
        movieRepository.getSingleMovieData(id, movie_detail)
    }

    fun getLiveDataObserver(): MutableLiveData<MovieDetails> {
        return movie_detail
    }}