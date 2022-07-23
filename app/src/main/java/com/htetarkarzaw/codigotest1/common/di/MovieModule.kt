package com.htetarkarzaw.codigotest1.common.di

import android.content.Context
import androidx.room.Room
import com.htetarkarzaw.codigotest1.BuildConfig
import com.htetarkarzaw.codigotest1.common.general.Constant
import com.htetarkarzaw.codigotest1.common.general.Endpoint
import com.htetarkarzaw.codigotest1.data.local.MovieDatabase
import com.htetarkarzaw.codigotest1.data.remote.MovieApiService
import com.htetarkarzaw.codigotest1.data.repository.MovieRepositoryImpl
import com.htetarkarzaw.codigotest1.domain.repository.MovieRepository
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MovieModule {
    @Provides
    @Singleton
    fun provideAuthRepository(apiService: MovieApiService,db : MovieDatabase): MovieRepository {
        return MovieRepositoryImpl(apiService = apiService, db = db)
    }

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context):MovieDatabase{
        return Room.databaseBuilder(context,MovieDatabase::class.java, Constant.MOVIE_DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Endpoint.MOVIES_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            //this is for logging profiler
            OkHttpClient.Builder()
                .addInterceptor(OkHttpProfilerInterceptor())
                .addNetworkInterceptor(OkHttpProfilerInterceptor())
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

        } else OkHttpClient
            .Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}