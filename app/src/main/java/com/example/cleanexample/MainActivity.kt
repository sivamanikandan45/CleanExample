package com.example.cleanexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //val inflater=menuInflater
        menuInflater.inflate(R.menu.main_menu,menu)
        val searchView=menu?.findItem(R.id.movie_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!=null){
                    appContainer=(application as MyApplication).appContainer
                    appContainer.movieContainer= MovieContainer(appContainer.getMoviesUseCase,appContainer.searchMovieUseCase)
                    val movieViewModel=appContainer.movieContainer?.movieViewModelFactory?.create()
                    movieViewModel?.movies?.observe(this@MainActivity, Observer {
                        val searchedList=movieViewModel?.search(newText)
                        println("the search result is $searchedList")
                        adapter.setData(ArrayList(searchedList))
                        recyclerView.adapter=adapter
                    })
                }
                return true
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.movie_search->{
                /*appContainer=(application as MyApplication).appContainer
                appContainer.movieContainer= MovieContainer(appContainer.getMoviesUseCase,appContainer.searchMovieUseCase)
                //setContentView(R.layout.activity_main)
                val movieViewModel=appContainer.movieContainer?.movieViewModelFactory?.create()
                movieViewModel.search()*/
                println("Search pressed")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer=(application as MyApplication).appContainer
        appContainer.movieContainer= MovieContainer(appContainer.getMoviesUseCase,appContainer.searchMovieUseCase)
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