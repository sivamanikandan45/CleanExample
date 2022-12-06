package com.example.cleanexample.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cleanexample.domain.entity.Movie

@Entity
data class MovieEntity(@PrimaryKey val id:Int,
                       var title:String,
                       var posterimg: String,
                       var overview:String,
                       var popularity:Double,
                       var backgroundImg:String)

fun MovieEntity.toMovie()=Movie(id, title, posterimg, overview, popularity, backgroundImg)

