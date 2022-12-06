package com.example.cleanexample.data.network

import com.example.cleanexample.data.network.model.NetworkMovie
import com.example.cleanexample.domain.util.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieNetworkDataSource(private val movieApi:MovieApi) {

    /*val latestNews: Flow<Result<List<NetworkMovie>>> = flow {
        while(true) {
            val latestNews = movieApi.getRemoteBooks()
            emit(latestNews) // Emits the result of the request to the flow
            delay(2000) // Suspends the coroutine for some time
        }
    }*/

    /*suspend fun getRemoteMovies(): Result<List<NetworkMovie>> {
        return latestNews
    }*/

    suspend fun getRemoteMovies(): List<NetworkMovie> {
        return movieApi.getRemoteBooks()
    }

}