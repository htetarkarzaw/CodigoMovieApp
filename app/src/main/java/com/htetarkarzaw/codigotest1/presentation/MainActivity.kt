package com.htetarkarzaw.codigotest1.presentation

import android.content.Context
import android.content.Intent
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.htetarkarzaw.codigotest1.R
import com.htetarkarzaw.codigotest1.common.base.BaseActivity
import com.htetarkarzaw.codigotest1.databinding.ActivityMainBinding
import com.htetarkarzaw.codigotest1.presentation.moviedetail.MovieDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MovieDetailActivity::class.java)
        }
    }
    override fun initUi() {
        setupNavigation()
    }

    override fun observe() {
    }


    private fun setupNavigation() {
        val navController = findNavController(R.id.container)
        binding.bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(
            navController, AppBarConfiguration(
                topLevelDestinationIds = setOf(
                    R.id.popular,
                    R.id.upcoming
                )
            )
        )
    }
}