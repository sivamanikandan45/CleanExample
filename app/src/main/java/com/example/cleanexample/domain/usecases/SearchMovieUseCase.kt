package com.example.cleanexample.domain.usecases

import com.example.cleanexample.domain.entity.Movie
import com.example.cleanexample.domain.repository.MovieRepository
import com.example.cleanexample.domain.util.Result
import kotlinx.coroutines.flow.Flow

class SearchMovieUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(query:String): Flow<Result<List<Movie>>> = repository.searchMovie(query)
}