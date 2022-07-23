package com.htetarkarzaw.codigotest1.domain.usecase

import com.htetarkarzaw.codigotest1.domain.repository.MovieRepository
import com.htetarkarzaw.codigotest1.domain.vo.MovieVo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class retrievePopularUsecase @Inject constructor(private val repo: MovieRepository) {
    suspend operator fun invoke(): Flow<List<MovieVo>> {
        return repo.retrievePopularMovies().map { list->
            list.map { it.toVo() }
        }
    }
}