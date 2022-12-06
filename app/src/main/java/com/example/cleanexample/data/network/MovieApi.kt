package com.example.cleanexample.data.network

import com.example.cleanexample.data.local.entities.MovieEntity
import com.example.cleanexample.data.network.model.NetworkMovie
import com.example.cleanexample.domain.util.Result

interface MovieApi {
    suspend fun getRemoteBooks():List<NetworkMovie>
}