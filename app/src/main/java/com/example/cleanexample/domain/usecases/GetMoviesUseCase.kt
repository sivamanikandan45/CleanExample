package com.example.cleanexample.domain.usecases

import com.example.cleanexample.data.local.MovieLocalDataSource
import com.example.cleanexample.data.local.dao.MovieDao
import com.example.cleanexample.data.network.MovieApiImpl
import com.example.cleanexample.data.network.MovieNetworkDataSource
import com.example.cleanexample.data.network.model.NetworkMovie
import com.example.cleanexample.data.repository.MovieRepositoryImpl
import com.example.cleanexample.domain.entity.Movie
import com.example.cleanexample.domain.repository.MovieRepository
import com.example.cleanexample.domain.util.Result
import kotlinx.coroutines.flow.Flow

class GetMoviesUseCase(var movieRepository: MovieRepository) {
//    val moviesFlow=movieRepository.moviesFlow
    operator fun invoke(): Flow<Result<List<Movie>>> =movieRepository.getMovies()
}