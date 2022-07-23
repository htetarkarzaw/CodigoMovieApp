package com.htetarkarzaw.codigotest1.domain.vo


data class MovieVo(
    val id: Long = -1L,
    val imageUrl: String? = null,
    val posterUrl: String? = null,
    val originalTitle: String = "",
    val title: String = "",
    val overview: String = "",
    val releasedDate: String = "",
    val popularity: Double = -0.0,
    val averageVote: Double = -0.0,
    val voteCount: Int = -1,
    val isFav:Boolean = false,
    val originalLanguage: String = ""
)
