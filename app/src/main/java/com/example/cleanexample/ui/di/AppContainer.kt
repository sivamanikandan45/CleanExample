package com.example.cleanexample.ui.di

import android.content.Context
import com.example.cleanexample.data.local.AppDB
import com.example.cleanexample.data.local.MovieLocalDataSource
import com.example.cleanexample.data.network.MovieApiImpl
import com.example.cleanexample.data.network.MovieNetworkDataSource
import com.example.cleanexample.data.repository.MovieRepositoryImpl
import com.example.cleanexample.domain.usecases.GetMoviesUseCase

class AppContainer(context: Context) {
    val dao=AppDB.getDB(context).getMovieDao()
    val movieLocalDataSource=MovieLocalDataSource(dao)
    val movieApi=MovieApiImpl()
    val movieNetworkDataSource=MovieNetworkDataSource(movieApi)
    val movieRepository=MovieRepositoryImpl(movieLocalDataSource,movieNetworkDataSource)

   val getMoviesUseCase=GetMoviesUseCase(movieRepository)
    var movieContainer: MovieContainer?=null
//    val movieViewModelFactory= MovieViewModel.MovieViewModelFactory(getMoviesUseCase)
}