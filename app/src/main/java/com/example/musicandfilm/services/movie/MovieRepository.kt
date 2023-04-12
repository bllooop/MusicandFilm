package com.example.musicandfilm.services.movie

import androidx.lifecycle.MutableLiveData
import com.example.musicandfilm.models.movies.Movie
import com.example.musicandfilm.models.movies.MovieDetails
import com.example.musicandfilm.models.movies.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository {

    fun getMovieData(moviess: MutableLiveData<List<Movie>>){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getMovieList().enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {}
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                return moviess.postValue(response.body()!!.movies)
            }
        })
    }
    fun getSingleMovieData(id: Int, movie_detail: MutableLiveData<MovieDetails>){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getMovieDetails(id).enqueue(object : Callback<MovieDetails> {
            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {}
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                movie_detail.postValue(response.body())
            }
        })
    }
}