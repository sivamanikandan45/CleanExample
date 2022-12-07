package com.example.cleanexample.data.mapper

import com.example.cleanexample.data.local.entities.MovieEntity
import com.example.cleanexample.data.network.model.NetworkMovie

class NetworkMovieToEntityMapper:Mapper<NetworkMovie,MovieEntity> {
    override fun map(input: NetworkMovie): MovieEntity {
        return MovieEntity(input.id,input.title,input.posterimg,input.overview,input.popularity,input.backgroundImg)
    }
}