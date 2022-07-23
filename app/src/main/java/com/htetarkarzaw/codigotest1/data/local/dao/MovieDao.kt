package com.htetarkarzaw.codigotest1.data.local.dao

import androidx.room.*
import com.htetarkarzaw.codigotest1.common.general.Constant
import com.htetarkarzaw.codigotest1.data.local.entities.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("SELECT * FROM ${Constant.MOVIE_TABLE_NAME} WHERE type='${Constant.MOVIE_TYPE_UPCOMING}'")
    fun retrievesUpcomingMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM ${Constant.MOVIE_TABLE_NAME} WHERE type='${Constant.MOVIE_TYPE_POPULAR}'")
    fun retrievesPopularMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM ${Constant.MOVIE_TABLE_NAME} WHERE id == :id")
    suspend fun getMovieById(id: Long): Movie?

    @Query("SELECT * FROM ${Constant.MOVIE_TABLE_NAME} WHERE id == :id")
    fun getMovieByIdViaFlow(id: Long): Flow<Movie>

    @Update
    suspend fun updateMovie(movie: Movie): Int
}