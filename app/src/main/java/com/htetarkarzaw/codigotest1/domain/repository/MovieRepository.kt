package com.htetarkarzaw.codigotest1.domain.repository

import com.htetarkarzaw.codigotest1.common.general.Resource
import com.htetarkarzaw.codigotest1.data.local.entities.Movie
import com.htetarkarzaw.codigotest1.domain.vo.MovieVo
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun fetchPopularMovies(): Flow<Resource<Boolean>>
    suspend fun fetchUpcomingMovies(): Flow<Resource<Boolean>>
    suspend fun toggleFavMovie(movieId: Long)
    suspend fun getMovieById(movieId: Long): Flow<Movie>
    suspend fun retrievePopularMovies(): Flow<List<Movie>>
    suspend fun retrieveUpcomingMovies(): Flow<List<Movie>>
    suspend fun insertMovies(movies: List<Movie>)
}