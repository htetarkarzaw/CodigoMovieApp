package com.htetarkarzaw.codigotest1.presentation.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.htetarkarzaw.codigotest1.common.general.Resource
import com.htetarkarzaw.codigotest1.data.local.entities.Movie
import com.htetarkarzaw.codigotest1.domain.usecase.fetchPopularUsecase
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
class PopularFragmentViewModel @Inject constructor(
    private val retrievePopularUsecase: retrievePopularUsecase,
    private val fetchPopularUsecase: fetchPopularUsecase,
    private val toggleFavMovieUsecase: toggleFavMovieUsecase
) : ViewModel() {
    private val _dbMovies = MutableStateFlow<List<MovieVo>>(emptyList())
    val dbMovies get() = _dbMovies.asStateFlow()

    private val _movies = MutableStateFlow<Resource<Boolean>>(Resource.Loading())
    val movies get() = _movies.asStateFlow()

    init {
        fetchPopularMovies()
        viewModelScope.launch {
            retrievePopularUsecase().collectLatest {
                _dbMovies.value = it
            }
        }
    }

    fun fetchPopularMovies() {
        _movies.value = Resource.Loading()
        viewModelScope.launch {
            fetchPopularUsecase().collectLatest {
                _movies.value = it
            }
        }
    }

    fun toggleFav(movieId:Long){
        viewModelScope.launch {
            toggleFavMovieUsecase(movieId)
        }
    }
}