package com.example.cleanexample.ui.di

import com.example.cleanexample.domain.usecases.GetMoviesUseCase

class MovieContainer(getMoviesUseCase: GetMoviesUseCase) {
    val movieViewModelFactory= MovieViewModelFactory(getMoviesUseCase)
}