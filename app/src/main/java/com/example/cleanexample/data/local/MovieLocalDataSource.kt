package com.example.cleanexample.data.local

import androidx.room.Query
import com.example.cleanexample.data.local.dao.MovieDao
import com.example.cleanexample.data.local.entities.MovieEntity
import javax.inject.Inject

class MovieLocalDataSource(private val dao: MovieDao) {

  suspend fun getLocalMovies(): List<MovieEntity> {
      return dao.getMovieList()
  }

    suspend fun deleteMovies(movies:List<MovieEntity>){
        dao.deleteMovies(movies)
    }

    suspend fun insertMovies(movies: List<MovieEntity>){
        dao.insertMovieList(movies)
    }

    suspend fun searchMovies(query: String): List<MovieEntity>{
        return dao.searchMovies(query)
    }

}