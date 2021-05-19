package ru.geekbrains.android2.movieapp.model.rest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.geekbrains.android2.movieapp.model.rest.rest_entities.CategoryDTO
import ru.geekbrains.android2.movieapp.model.rest.rest_entities.GenresDTO
import ru.geekbrains.android2.movieapp.model.rest.rest_entities.MovieDetailDTO

interface BackendAPI {
    @GET(endPointCategory)
    fun getCategory(@Path("category") categoryName:String, @Query(paramApi_key) api_key:String,
    @Query(paramLanguage) language:String, @Query(paramPage) page:Int): Call<CategoryDTO>

    @GET(endPointMovie)
    fun getMovieDetail(@Path("movieId") id:Int, @Query(paramApi_key) api_key:String,
                    @Query(paramLanguage) language:String): Call<MovieDetailDTO>

    @GET(endPointGenre)
    fun getGenres(@Query(paramApi_key) api_key:String,
                       @Query(paramLanguage) language:String): Call<GenresDTO>
}