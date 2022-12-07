package com.example.cleanexample.data.local.dao

import androidx.room.*
import com.example.cleanexample.data.local.entities.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieList(movieList:List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity")
    suspend fun getMovieList():List<MovieEntity>

    @Delete
    suspend fun deleteMovies(movies:List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity where title=:query")
    suspend fun searchMovies(query: String):List<MovieEntity>

}