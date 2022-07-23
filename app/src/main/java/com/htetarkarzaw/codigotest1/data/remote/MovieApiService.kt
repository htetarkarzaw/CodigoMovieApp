package com.htetarkarzaw.codigotest1.data.remote

import com.htetarkarzaw.codigotest1.BuildConfig
import com.htetarkarzaw.codigotest1.common.general.Endpoint
import com.htetarkarzaw.codigotest1.data.remote.dto.MovieListDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET(Endpoint.MOVIES_POPULAR)
    suspend fun fetchPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<MovieListDTO>

    @GET(Endpoint.MOVIES_UPCOMING)
    suspend fun fetchUpcomingMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<MovieListDTO>
}