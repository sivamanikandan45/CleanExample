package com.example.cleanexample.domain.entity


data class Movie(val id:Int,
                 var title:String,
                 var posterimg: String,
                 var overview:String,
                 var popularity:Double,
                 var backgroundImg:String)
