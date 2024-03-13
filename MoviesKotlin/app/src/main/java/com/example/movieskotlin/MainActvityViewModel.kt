package com.example.movieskotlin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActvityViewModel:ViewModel() {
    val movieLiveData=MutableLiveData<MovieModel?>()
private val repo=Repo()




fun getMovies(){
    repo.getMovie{
        it?.let {
            movieLiveData.value=it
        }
    }
}




}