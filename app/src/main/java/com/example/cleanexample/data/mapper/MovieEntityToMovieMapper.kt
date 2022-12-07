package com.example.cleanexample.data.mapper

import com.example.cleanexample.data.local.entities.MovieEntity
import com.example.cleanexample.domain.entity.Movie

class MovieEntityToMovieMapper:Mapper<MovieEntity,Movie> {
    override fun map(input: MovieEntity): Movie {
        return Movie(input.id,input.title,input.posterimg,input.overview,input.popularity,input.backgroundImg)
    }
}