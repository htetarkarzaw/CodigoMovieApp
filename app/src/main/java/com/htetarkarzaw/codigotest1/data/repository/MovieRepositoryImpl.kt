package com.htetarkarzaw.codigotest1.data.repository

import com.htetarkarzaw.codigotest1.common.general.RemoteResource
import com.htetarkarzaw.codigotest1.common.general.Resource
import com.htetarkarzaw.codigotest1.common.general.safeApiCall
import com.htetarkarzaw.codigotest1.data.local.MovieDatabase
import com.htetarkarzaw.codigotest1.data.local.entities.Movie
import com.htetarkarzaw.codigotest1.data.remote.MovieApiService
import com.htetarkarzaw.codigotest1.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: MovieApiService,
    db: MovieDatabase
) : MovieRepository {
    private val dao = db.movieDao()

    override suspend fun fetchPopularMovies(): Flow<Resource<Boolean>> {
        return when (val response = safeApiCall { apiService.fetchPopularMovies() }) {
            is RemoteResource.ErrorEvent -> {
                flow {
                    emit(Resource.Error(error = "Retrieve Popular Movie Error!${response.message}"))
                }
            }
            is RemoteResource.LoadingEvent -> {
                flow { emit(Resource.Loading()) }
            }
            is RemoteResource.SuccessEvent -> {
                response.data?.results?.let {
                    insertMovies(movies = it.map { dto ->
                        dto.toEntity("popular")
                    })
                }
                flow {
                    emit(Resource.Success(data = true))
                }
            }
        }
    }


    override suspend fun fetchUpcomingMovies(): Flow<Resource<Boolean>> {
        return when (val response = safeApiCall { apiService.fetchUpcomingMovie() }) {
            is RemoteResource.ErrorEvent -> {
                flow {
                    emit(Resource.Error(error = "Retrieve Upcoming Movie Error!"))
                }
            }
            is RemoteResource.LoadingEvent -> {
                flow { emit(Resource.Loading()) }
            }
            is RemoteResource.SuccessEvent -> {
                response.data?.results?.let {
                    insertMovies(movies = it.map { dto ->
                        dto.toEntity("upcoming")
                    })
                }
                flow {
                    emit(Resource.Success(data = true))
                }
            }
        }
    }

    override suspend fun toggleFavMovie(movieId: Long) {
        val movie = dao.getMovieById(movieId)
        if (movie != null) {
            movie.isFav = movie.isFav != true
            dao.updateMovie(movie)
        }
    }

    override suspend fun getMovieById(movieId: Long): Flow<Movie> {
        return dao.getMovieByIdViaFlow(movieId)
    }

    override suspend fun retrievePopularMovies(): Flow<List<Movie>> {
        return dao.retrievesPopularMovies()
    }

    override suspend fun retrieveUpcomingMovies(): Flow<List<Movie>> {
        return dao.retrievesUpcomingMovies()
    }

    override suspend fun insertMovies(movies: List<Movie>) {
        dao.insertMovies(movies)
    }
}