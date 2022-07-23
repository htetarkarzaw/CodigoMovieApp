package com.htetarkarzaw.codigotest1.domain.usecase

import com.htetarkarzaw.codigotest1.common.general.Resource
import com.htetarkarzaw.codigotest1.domain.repository.MovieRepository
import com.htetarkarzaw.codigotest1.domain.vo.MovieVo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class fetchUpcomingUsecase @Inject constructor(private val repo: MovieRepository) {
    suspend operator fun invoke(): Flow<Resource<Boolean>> {
        return repo.fetchUpcomingMovies()
    }
}