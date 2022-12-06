package com.example.cleanexample.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cleanexample.data.local.dao.MovieDao
import com.example.cleanexample.data.local.entities.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDB:RoomDatabase() {
    abstract fun getMovieDao(): MovieDao

    companion object{
        @Volatile
        private var instance:AppDB?=null

        fun getDB(context: Context?):AppDB{
            val temp= instance
            if(temp != null){
                return temp
            }
            synchronized(this){
                val newInstance= Room.databaseBuilder(context!!, AppDB::class.java, "movie").build()
                instance=newInstance
                return newInstance
            }
            //return dbInstance
        }

    }
}