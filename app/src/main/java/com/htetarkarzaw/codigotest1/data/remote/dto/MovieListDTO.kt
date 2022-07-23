package com.htetarkarzaw.codigotest1.data.remote.dto

import com.htetarkarzaw.codigotest1.data.local.entities.Movie
import com.htetarkarzaw.codigotest1.domain.vo.MovieVo


data class MovieListDTO(
    val page: Int,
    val results: List<MovieDTO>,
    val total_pages: Int,
    val total_results: Int
)


data class MovieDTO(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Long,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
) {
    fun toEntity(type: String): Movie {
        return Movie(
            id = id,
            imageUrl = backdrop_path,
            posterUrl = poster_path,
            originalTitle = original_title,
            title = title,
            overview = overview,
            releasedDate = release_date,
            popularity = popularity,
            averageVote = vote_average,
            voteCount = vote_count,
            originalLanguage = original_language,
            type = type,
            isFav = false
        )
    }
}