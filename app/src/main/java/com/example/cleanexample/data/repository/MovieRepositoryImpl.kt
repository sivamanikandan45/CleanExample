package com.example.cleanexample.data.repository

import com.example.cleanexample.data.local.MovieLocalDataSource
import com.example.cleanexample.data.local.entities.MovieEntity
import com.example.cleanexample.data.local.entities.toMovie
import com.example.cleanexample.data.mapper.Mapper
import com.example.cleanexample.data.mapper.MovieEntityToMovieMapper
import com.example.cleanexample.data.mapper.NetworkMovieToEntityMapper
import com.example.cleanexample.data.network.MovieNetworkDataSource
import com.example.cleanexample.data.network.model.NetworkMovie
import com.example.cleanexample.data.network.model.toMovieEntity
import com.example.cleanexample.domain.entity.Movie
import com.example.cleanexample.domain.repository.MovieRepository
import com.example.cleanexample.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class MovieRepositoryImpl (private val movieLocalDataSource: MovieLocalDataSource,
                           private val movieNetworkDataSource: MovieNetworkDataSource,
                           private val toMovieEntityMapper:Mapper<NetworkMovie,MovieEntity>,
                           private val toMovieMapper: Mapper<MovieEntity,Movie>):MovieRepository{

    override suspend fun getRemoteMovies(): List<NetworkMovie> {
        return movieNetworkDataSource.getRemoteMovies()
    }

    override suspend fun getLocalMovies(): List<MovieEntity> {
        return movieLocalDataSource.getLocalMovies()
    }

    override fun getMovies(): Flow<Result<List<Movie>>> = flow {
        println("get movies called")
        emit(Result.Loading())

        val moviesFromLocal=movieLocalDataSource.getLocalMovies().map { toMovieMapper.map(it) }//it.toMovie()
        emit(Result.Loading(data = moviesFromLocal))

        println("loadsing local data is $moviesFromLocal")

        try{
            val remoteMovies=movieNetworkDataSource.getRemoteMovies()
            val moviesList=remoteMovies.map { toMovieEntityMapper.map(it) } //NetworkMovieToEntityMapper().map(it) it.toMovieEntity()
            movieLocalDataSource.deleteMovies(moviesList)
            movieLocalDataSource.insertMovies(moviesList)

        }catch (ex:IOException){
            println("emitting error")
            emit(Result.Error(message = "Oops! Something went wrong", data = moviesFromLocal))
            println("error data is $moviesFromLocal")
        }

        val newMoviesFromLocal=movieLocalDataSource.getLocalMovies().map { toMovieMapper.map(it) }
        emit(Result.Success(newMoviesFromLocal))
        println("new data data is $moviesFromLocal")

    }

    override fun searchMovie(query: String): Flow<Result<List<Movie>>> = flow{
        println("search movies called")
        emit(Result.Loading())

        val moviesFromLocal=movieLocalDataSource.searchMovies(query).map { toMovieMapper.map(it) }
        emit(Result.Loading(data = moviesFromLocal))
        println("searched local data is $moviesFromLocal")

        /*println("loadsing local data is $moviesFromLocal")

        try{
            val remoteMovies=movieNetworkDataSource.getRemoteMovies()
            val moviesList=remoteMovies.map { it.toMovieEntity() }
            movieLocalDataSource.deleteMovies(moviesList)
            movieLocalDataSource.insertMovies(moviesList)

        }catch (ex:IOException){
            println("emitting error")
            emit(Result.Error(message = "Oops! Something went wrong", data = moviesFromLocal))
            println("error data is $moviesFromLocal")
        }*/

        val newMoviesFromLocal=movieLocalDataSource.searchMovies(query).map { toMovieMapper.map(it) }
        emit(Result.Success(newMoviesFromLocal))
        println("new data data is $moviesFromLocal")
    }


}