package com.example.cleanexample.ui.di

import com.example.cleanexample.domain.usecases.GetMoviesUseCase
import com.example.cleanexample.domain.usecases.SearchMovieUseCase
import com.example.cleanexample.ui.viewmodels.MovieViewModel

class MovieViewModelFactory(private val getMoviesUseCase: GetMoviesUseCase,private val searchMovieUseCase: SearchMovieUseCase):
    Factory<MovieViewModel> {
    override fun create(): MovieViewModel {
        return MovieViewModel(getMoviesUseCase,searchMovieUseCase)
    }
}