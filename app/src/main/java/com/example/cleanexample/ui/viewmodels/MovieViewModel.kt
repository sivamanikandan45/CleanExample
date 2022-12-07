package com.example.cleanexample.ui.viewmodels

import androidx.lifecycle.*
import androidx.room.Query
import com.example.cleanexample.domain.entity.Movie
import com.example.cleanexample.domain.usecases.GetMoviesUseCase
import com.example.cleanexample.domain.usecases.SearchMovieUseCase
import com.example.cleanexample.domain.util.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieViewModel (private val getMoviesUseCase: GetMoviesUseCase,private val searchMovieUseCase: SearchMovieUseCase) :ViewModel(){
    private val _dataLoading = MutableLiveData(true)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _movies = MutableLiveData<List<Movie>>()
    val movies = _movies

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error


    fun search(query: String):List<Movie>{
        val result= mutableListOf<Movie>()
        if(_movies.value!=null){
            for(movie in _movies.value!!){
                if(movie.title.lowercase().contains(query.lowercase())){
                    result.add(movie)
                }
            }
        }
        else{
            println("It is null")
        }
        return result
        /*viewModelScope.launch {
            delay(500L)
            searchMovieUseCase.invoke(query).collect{result->
                when(result){
                    is Result.Error -> {
                        println("Error caught")
                        _dataLoading.postValue(false)
                        _error.postValue("No network available")
                        movies.postValue(result.data)
                    }
                    is Result.Loading -> {
                        _dataLoading.postValue(true)
                        //val mappedValue=result.data?.map { it.toMovie() }
                        println("got result ${result.data}")
                        if(result.data!=null){
                            movies.postValue(result.data)
                        }

                    }
                    is Result.Success -> {
                        println("got it")
                        //delay(2000)
                        _dataLoading.postValue(false)
                        println("data is ${result.data}")
                        //val mappedValue=result.data?.map { it.toMovie() }
                        movies.postValue(result.data)
                    }
                }
            }
        }*/
    }
    init {

        viewModelScope.launch {
            /*getMoviesUseCase.moviesFlow.collect{result ->
                println("COllected the flow")

            }*/
            delay(500L)
            _dataLoading.postValue(true)
            val result=getMoviesUseCase.invoke().collect { result ->
                println("inside on each")
                when(result){
                    is Result.Error -> {
                        println("Error caught")
                        _dataLoading.postValue(false)
                        _error.postValue("No network available")
                        movies.postValue(result.data)
                    }
                    is Result.Loading -> {
                        _dataLoading.postValue(true)
                        //val mappedValue=result.data?.map { it.toMovie() }
                        println("got result ${result.data}")
                        if(result.data!=null){
                            movies.postValue(result.data)
                        }

                    }
                    is Result.Success -> {
                        println("got it")
                        //delay(2000)
                        _dataLoading.postValue(false)
                        println("data is ${result.data}")
                        //val mappedValue=result.data?.map { it.toMovie() }
                        movies.postValue(result.data)
                    }
                }
            }

            //println("result is ${result}")

            /*println("result is $result")
            result.onEach { result->
                when(result){
                    is Result.Error -> {
                        println("Error caught")
                        _dataLoading.postValue(false)
                        _error.postValue("No network available")
                        books.value = emptyList()
                    }
                    is Result.Loading -> {
                        _dataLoading.postValue(false)
                        //val mappedValue=result.data?.map { it.toMovie() }
                        books.postValue(result.data)
                    }
                    is Result.Success -> {
                        println("got it")
                        //delay(2000)
                        _dataLoading.postValue(false)
                        //val mappedValue=result.data?.map { it.toMovie() }
                        books.postValue(result.data)
                    }
                }
            }*/

            /*when(val result=getMoviesUseCase.invoke()){
                is Result.Error -> {
                    println("Error caught")
                    _dataLoading.postValue(false)
                    _error.postValue("No network available")
                    books.value = emptyList()
                }
                is Result.Loading -> {

                }
                is Result.Success -> {
                    println("got it")
                    delay(2000)
                    _dataLoading.postValue(false)
                    val mappedValue=result.data?.map { it.toMovie() }
                    books.postValue(mappedValue)
                }
            }*/

        }
    }



    /*class MovieViewModelFactory(private val getMoviesUseCase: GetMoviesUseCase) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovieViewModel(getMoviesUseCase) as T
        }
    }*/

}