package com.example.musicandfilm.services.event

//import com.example.moviecatalog.localdata.FavoriteMovieDatabase
import com.example.musicandfilm.services.news.NewsApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventApiService {

    companion object{
        private const val BASE_URL = "https://kudago.com/public-api/v1.4/"
        private var retrofit : Retrofit? = null
        val clientBuilder = OkHttpClient.Builder()
            .addNetworkInterceptor { chain ->
                chain.proceed(
                    chain.request()
                        .newBuilder()
                        .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36")
                        .build()
                )
            }
            .build()
        val loggingInterceptor = HttpLoggingInterceptor()

        fun getInstance() : Retrofit{
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            //clientBuilder.addInterceptor(loggingInterceptor)

            if(retrofit == null){
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(clientBuilder
                        //.build()
                        )
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }

    }
}