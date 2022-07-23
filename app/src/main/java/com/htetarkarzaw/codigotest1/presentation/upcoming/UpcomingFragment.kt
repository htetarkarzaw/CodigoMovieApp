package com.htetarkarzaw.codigotest1.presentation.upcoming

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.htetarkarzaw.codigotest1.common.base.BaseFragment
import com.htetarkarzaw.codigotest1.common.general.Resource
import com.htetarkarzaw.codigotest1.databinding.FragmentUpcomingBinding
import com.htetarkarzaw.codigotest1.presentation.adapter.MovieAdapter
import com.htetarkarzaw.codigotest1.presentation.moviedetail.MovieDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class UpcomingFragment : BaseFragment<FragmentUpcomingBinding>(FragmentUpcomingBinding::inflate) {
    private val viewModel: UpcomingFragmentViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter
    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.dbMovies.collectLatest {
                it.let { movieList ->
                    Timber.e("MovieList: ${movieList.size}")
                    movieList.let {
                        if (movieList.isNotEmpty()) {
                            movieAdapter.submitList(movieList)
                            binding.tvNoData.visibility = View.GONE
                        } else {
                            binding.tvNoData.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.movies.collectLatest {
                binding.swipeRefresh.isRefreshing = false
                when (it) {
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Timber.e("Popular List Api Call-> Error ${it.message} ")
                    }
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        Timber.e("Popular List Api Call-> Loading ")
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE

                    }
                }
            }
        }
    }

    override fun initUi() {
        setupRecyclerView()
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.fetchPopularMovies()
        }
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter({ clickDetail(it) }, { clickFav(it) }).apply {
            binding.rvMovie.layoutManager = LinearLayoutManager(
                mContext,
                LinearLayoutManager.VERTICAL, false
            )
            binding.rvMovie.adapter = this
        }
    }

    private fun clickDetail(position: Int) {
        val item = movieAdapter.getClickItem(position)
        requireActivity().startActivity(MovieDetailActivity.newIntent(mContext, item.id))
    }

    private fun clickFav(position: Int) {
        val item = movieAdapter.getClickFav(position)
        movieAdapter.notifyItemChanged(position)
        viewModel.toggleFav(movieId = item.id)
    }
}