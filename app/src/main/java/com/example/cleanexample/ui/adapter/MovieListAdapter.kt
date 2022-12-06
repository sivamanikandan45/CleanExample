package com.example.cleanexample.ui.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanexample.R
import com.example.cleanexample.domain.util.ImageCache
import com.example.cleanexample.domain.entity.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL

class MovieListAdapter():RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    //private lateinit var listener:ItemClickListener
    //private var viewType: ViewType =ViewType.GRID
    //private lateinit var list: List<Movie>
    private var list:List<Movie> = listOf()

    interface ItemClickListener{
        fun onItemClick(position:Int)
    }

    /*fun setOnItemClickListener(listener: ItemClickListener){
        this.listener=listener
    }*/

    /*fun setViewType(viewType: ViewType){
        this.viewType=viewType
        println("View type is ${this.viewType}")
    }*/

    /*override fun getItemViewType(position: Int): Int {
        return this.viewType.ordinal
    }*/


    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        fun bind(movie: Movie) {
            nameTextView.text=movie.title
            var bitmapValue: Bitmap? = null
//            fun loadBitmap(resId: Int, imageView: ImageView) {
            val imageKey: String = movie.id.toString()

            val bitmap: Bitmap? = ImageCache.getBitmapFromMemCache(imageKey)?.also {
                println("Fetched from cache at $adapterPosition")
                imageView.setImageBitmap(it)
            } ?: run {
                GlobalScope.launch {
                    val job = launch(Dispatchers.IO) {
                        val imageUrl = URL(movie.posterimg)
                        //println("image is ${list[position]}")
                        try{
                            bitmapValue= BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream())
                        }catch (exception: IOException){
                            println("Exception caught")
                        }
                        //bitmapValue = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream())
                        withContext(Dispatchers.Main) {
                            if(bitmapValue!=null){
                                imageView.setImageBitmap(bitmapValue)
                                ImageCache.addBitmapToCache(movie.id.toString(),bitmapValue!!)
                                println("Image set using coroutine at $adapterPosition")
                            }
                        }
                    }
                    job.join()
                }
                null
            }
        }

        val nameTextView:TextView
        val imageView:ImageView
        init{
            nameTextView=view.findViewById(R.id.movie_title_card)
            imageView=view.findViewById(R.id.movie_image_card)

            /*view.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }*/
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        /*return if(viewType == ViewType.LIST.ordinal){
            val view=LayoutInflater.from(parent.context).inflate(R.layout.movie_list_listitem,parent,false)
            //println("creting list view")
            ViewHolder(view,listener)
        } else{
            val view=LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item,parent,false)
            ViewHolder(view,listener)
        }*/
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_movie,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(arrayList: ArrayList<Movie>) {
        this.list=arrayList
    }
}

