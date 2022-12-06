package com.example.cleanexample.data.network.model

import com.example.cleanexample.data.local.entities.MovieEntity
import com.example.cleanexample.domain.entity.Movie

data class NetworkMovie(val id:Int,
                        var title:String,
                        var posterimg: String,
                        var overview:String,
                        var popularity:Double,
                        var backgroundImg:String)

fun NetworkMovie.toMovie()=Movie(id, title, posterimg, overview, popularity, backgroundImg)

fun NetworkMovie.toMovieEntity()=MovieEntity(id, title, posterimg, overview, popularity, backgroundImg)
