package com.htetarkarzaw.codigotest1.presentation.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.htetarkarzaw.codigotest1.common.general.Resource
import com.htetarkarzaw.codigotest1.data.local.entities.Movie
import com.htetarkarzaw.codigotest1.domain.usecase.fetchPopularUsecase
import com.htetarkarzaw.codigotest1.domain.usecase.getMovieByIdUsecase
import com.htetarkarzaw.codigotest1.domain.usecase.retrievePopularUsecase
import com.htetarkarzaw.codigotest1.domain.usecase.toggleFavMovieUsecase
import com.htetarkarzaw.codigotest1.domain.vo.MovieVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieByIdUsecase: getMovieByIdUsecase,
    private val toggleFavMovieUsecase: toggleFavMovieUsecase
) : ViewModel() {
    private val _dbMovies = MutableStateFlow(MovieVo())
    val dbMovies get() = _dbMovies.asStateFlow()

    fun getMovieById(movieId: Long) {
        viewModelScope.launch {
            getMovieByIdUsecase(movieId).collectLatest {
                _dbMovies.value = it
            }
        }
    }

    fun toggleFav(movieId:Long){
        viewModelScope.launch {
            toggleFavMovieUsecase(movieId)
        }
    }
}