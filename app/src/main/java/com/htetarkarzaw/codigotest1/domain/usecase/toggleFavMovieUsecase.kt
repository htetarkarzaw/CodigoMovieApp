package com.htetarkarzaw.codigotest1.domain.usecase

import com.htetarkarzaw.codigotest1.common.general.Resource
import com.htetarkarzaw.codigotest1.domain.repository.MovieRepository
import com.htetarkarzaw.codigotest1.domain.vo.MovieVo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class toggleFavMovieUsecase @Inject constructor(private val repo: MovieRepository) {
    suspend operator fun invoke(movieId:Long) {
        return repo.toggleFavMovie(movieId = movieId)
    }
}