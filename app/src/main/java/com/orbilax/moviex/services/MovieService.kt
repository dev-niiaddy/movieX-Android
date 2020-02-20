package com.orbilax.moviex.services

import com.google.gson.GsonBuilder
import com.orbilax.moviex.model.GenreList
import com.orbilax.moviex.model.MovieDetails
import com.orbilax.moviex.model.MoviesPage
import com.orbilax.moviex.util.TIMEOUT_IN_SECONDS
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface MovieService {

//    @POST("register")
//    fun registerNewUser(@Body newUser: Register?): Observable<UserToken>

    @GET("movie/now_playing")
    fun getNowPlayingPage(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): Observable<MoviesPage>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): Observable<MoviesPage>

    @GET("discover/movie")
    fun getExplorePage(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): Observable<MoviesPage>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): Observable<MovieDetails>

    @GET("genre/movie/list")
    fun getGenreList(@Query("api_key") apiKey: String = API_KEY): Observable<GenreList>

    @GET("movie/{movie_id}/recommendations")
    fun getRecommendedMovies(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Observable<MoviesPage>

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
        private const val API_KEY = "ab0d2a1c784226043ce16ce959090bf7"

        private val gson = GsonBuilder()
//            .registerTypeAdapter(Date::class.java, DateDeserializer())
            .create()

        private val builder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)


        fun createService(): MovieService {

            val httpClient = OkHttpClient.Builder()
//                .addInterceptor(object : NetworkConnectionInterceptor() {
//                    override fun isInternetAvailable(): Boolean {
//                        return isOnline(context)
//                    }
//
//                    override fun onInternetUnavailable() {
//                        if (context is InternetConnectionListener) {
//                            context.onInternetUnavailable()
//                        }
//                    }
//                })
                .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)

            val client = httpClient.build()
            val retrofit = builder.client(client).build()
            return retrofit.create(MovieService::class.java)
        }
    }

}