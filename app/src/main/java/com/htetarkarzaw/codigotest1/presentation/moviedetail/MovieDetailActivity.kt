package com.htetarkarzaw.codigotest1.presentation.moviedetail

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.htetarkarzaw.codigotest1.R
import com.htetarkarzaw.codigotest1.common.base.BaseActivity
import com.htetarkarzaw.codigotest1.common.general.Endpoint
import com.htetarkarzaw.codigotest1.databinding.ActivityMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MovieDetailActivity :
    BaseActivity<ActivityMovieDetailBinding>(ActivityMovieDetailBinding::inflate) {
    private var movieId: Long = 0L
    private val viewModel: MovieDetailViewModel by viewModels()

    companion object {
        fun newIntent(context: Context, movieId: Long): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra("movieId", movieId)
            return intent
        }
    }

    override fun initUi() {
        setupActionBar()
        movieId = intent.getLongExtra("movieId", 0L)
        viewModel.getMovieById(movieId)
        binding.imgFav.setOnClickListener {
            viewModel.toggleFav(movieId)
        }
    }

    override fun observe() {
        lifecycleScope.launchWhenStarted {
            viewModel.dbMovies.collectLatest { movieVo ->
                if (movieVo.id != -1L) {
                    binding.tvTitle.text = movieVo.title
                    binding.tvDescription.text = movieVo.overview
                    binding.tvAverage.text = movieVo.averageVote.toString()
                    binding.tvReleaseDate.text = movieVo.releasedDate
                    Glide.with(this@MovieDetailActivity)
                        .load(Endpoint.MOVIES_IMAGE_URL + (movieVo?.imageUrl ?: ""))
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .placeholder(R.drawable.place_holder)
                        .into(binding.ivMovie)
                    if (movieVo.isFav) {
                        Glide.with(this@MovieDetailActivity)
                            .load(R.drawable.ic_fav_select)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .placeholder(R.drawable.place_holder)
                            .into(binding.imgFav)
                    } else {
                        Glide.with(this@MovieDetailActivity)
                            .load(R.drawable.ic_fav_unselect)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .placeholder(R.drawable.place_holder)
                            .into(binding.imgFav)
                    }
                }
            }
        }
    }

    private fun setupActionBar() {
        supportActionBar?.let {
            it.title = this.javaClass.simpleName
            it.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}