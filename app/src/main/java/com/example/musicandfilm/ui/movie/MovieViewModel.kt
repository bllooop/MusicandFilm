package com.example.musicandfilm.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicandfilm.models.movies.Movie
import com.example.musicandfilm.models.movies.MovieResponse
import com.example.musicandfilm.services.movie.MovieApiInterface
import com.example.musicandfilm.services.movie.MovieApiService
import com.example.musicandfilm.services.movie.MovieRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {
     var moviess = MutableLiveData<List<Movie>>()
     val movieRepository = MovieRepository()
     fun getLiveDataObserver():MutableLiveData<List<Movie>>{
          return moviess
     }

     fun getAllMovies(){
          movieRepository.getMovieData(moviess)
     }
}
