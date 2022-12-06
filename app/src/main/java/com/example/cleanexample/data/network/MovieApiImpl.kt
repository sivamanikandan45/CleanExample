package com.example.cleanexample.data.network

import com.example.cleanexample.data.network.model.NetworkMovie
import com.example.cleanexample.domain.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MovieApiImpl:MovieApi {
    override suspend fun getRemoteBooks(): List<NetworkMovie> {
        var list:MutableList<NetworkMovie> = mutableListOf()
        var error=false
        withContext(Dispatchers.IO){
                val url="https://api.themoviedb.org/3/trending/movie/day?api_key=08e4a6a03c5c292c1893f7127324e5f3"
                var response=""
                //try{
                    val connection= URL(url).openConnection() as HttpURLConnection
                    val reader= BufferedReader(InputStreamReader(connection.inputStream))

                    var line=reader.readLine()
                    while(line!=null){
                        response+=line
                        line=reader.readLine()
                    }
                /*}catch (ex:IOException){
                    println("Exception caught")
                    error=true
                    return@withContext
                }*/


                if(response.isNotEmpty()){
                    val jsonObject= JSONTokener(response).nextValue() as JSONObject
                    val jsonArray=jsonObject.getJSONArray("results")

                    for(i in 0 until jsonArray.length()){
                        val link="https://image.tmdb.org/t/p/w500/"
                        val id=jsonArray.getJSONObject(i).getString("id").toInt()
                        val imgURI=jsonArray.getJSONObject(i).getString("poster_path")
                        val bgURI=jsonArray.getJSONObject(i).getString("backdrop_path")
                        val overview=jsonArray.getJSONObject(i).getString("overview")
                        val title=jsonArray.getJSONObject(i).getString("original_title")
                        val popularity=jsonArray.getJSONObject(i).getString("popularity")
                        val movie= NetworkMovie(id,title,(link+imgURI.toString()),overview,popularity.toDouble(),(link+bgURI.toString()))
                        list.add(movie)
                    }
                    println("list fetched $list")
                    //insertMovieList(list)
                }else{

                }

        }
        return list
    }
}