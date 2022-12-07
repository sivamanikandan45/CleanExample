package com.example.cleanexample.domain.repository

import androidx.room.Query
import com.example.cleanexample.data.local.entities.MovieEntity
import com.example.cleanexample.data.network.model.NetworkMovie
import com.example.cleanexample.domain.entity.Movie
import com.example.cleanexample.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getRemoteMovies():List<NetworkMovie>
    suspend fun getLocalMovies():List<MovieEntity>
    fun getMovies():Flow<Result<List<Movie>>>
    fun searchMovie(query: String):Flow<Result<List<Movie>>>
}