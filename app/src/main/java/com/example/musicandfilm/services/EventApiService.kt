package com.example.musicandfilm.services

//import com.example.moviecatalog.localdata.FavoriteMovieDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventApiService {

    companion object{
        private const val BASE_URL = "https://kudago.com/public-api/v1.4/"
       // private const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films"
        private var retrofit : Retrofit? = null
        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()

        fun getInstance() : Retrofit{
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(loggingInterceptor)
            if(retrofit == null){
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(clientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }

    }
}