package com.example.cleanexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanexample.ui.di.AppContainer
import com.example.cleanexample.ui.di.MovieContainer
import com.example.cleanexample.ui.adapter.MovieListAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar:ProgressBar
    private lateinit var adapter: MovieListAdapter
    private lateinit var manager: RecyclerView.LayoutManager
    lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer=(application as MyApplication).appContainer
        appContainer.movieContainer= MovieContainer(appContainer.getMoviesUseCase)
        setContentView(R.layout.activity_main)
        val movieViewModel=appContainer.movieContainer?.movieViewModelFactory?.create()
        //val movieViewModel=ViewModelProvider(this)[MovieViewModel::class.java]
        recyclerView=findViewById<RecyclerView>(R.id.recycler)
        progressBar=findViewById(R.id.progress)
        manager= GridLayoutManager(this,2)
        adapter=MovieListAdapter()
        recyclerView.adapter=adapter
        recyclerView.layoutManager=manager
        movieViewModel?.movies?.observe(this, Observer {
            progressBar.visibility= View.GONE
            adapter.setData(ArrayList(it))
            adapter.notifyDataSetChanged()
        })

       // appContainer = (application as Application).appContainer

        movieViewModel?.error?.observe(this, Observer {
            println("got error")
            /*Snackbar.make(recyclerView,it,Snackbar.LENGTH_LONG)
                .show()*/
        })

        movieViewModel?.dataLoading?.observe(this, Observer {
            if(it==true){
                progressBar.visibility= View.VISIBLE
            }else{
                progressBar.visibility= View.GONE
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        appContainer.movieContainer=null
    }
}