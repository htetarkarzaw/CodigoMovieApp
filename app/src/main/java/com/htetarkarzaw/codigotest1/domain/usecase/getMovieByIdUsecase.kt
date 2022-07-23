package com.htetarkarzaw.codigotest1.domain.usecase

import com.htetarkarzaw.codigotest1.common.general.Resource
import com.htetarkarzaw.codigotest1.domain.repository.MovieRepository
import com.htetarkarzaw.codigotest1.domain.vo.MovieVo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class getMovieByIdUsecase @Inject constructor(private val repo: MovieRepository) {
    suspend operator fun invoke(movieId:Long):Flow<MovieVo> {
        return repo.getMovieById(movieId = movieId).map {
            it?.toVo() ?: MovieVo()
        }
    }
}