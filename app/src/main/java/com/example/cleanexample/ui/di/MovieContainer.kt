package com.example.cleanexample.ui.di

import com.example.cleanexample.domain.usecases.GetMoviesUseCase
import com.example.cleanexample.domain.usecases.SearchMovieUseCase

class MovieContainer(getMoviesUseCase: GetMoviesUseCase,searchMovieUseCase: SearchMovieUseCase) {
    val movieViewModelFactory= MovieViewModelFactory(getMoviesUseCase, searchMovieUseCase)
}