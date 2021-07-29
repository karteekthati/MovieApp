package com.kk.movieapp.ViewModel

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.kk.movieapp.data.movieDetails.MovieDetails
import com.kk.movieapp.remote.Events
import com.kk.movieapp.remote.MovieInterface
import com.kk.movieapp.remote.Status
import com.kk.movieapp.ui.Paging.MoviePaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.kk.movieapp.remote.Result
import com.kk.movieapp.Repository.MovieDetailsRepository

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieInterface: MovieInterface,
private val repository: MovieDetailsRepository
) : ViewModel() {
    private var query = MutableLiveData<String>("")

    private val _movieDetails = MutableLiveData<Events<Result<MovieDetails>>>()
    val movieDetails:LiveData<Events<Result<MovieDetails>>> =  _movieDetails

    val list = query.switchMap {query->
        Pager(PagingConfig(pageSize = 10)) {
            MoviePaging(query, movieInterface)
        }.liveData.cachedIn(viewModelScope)
    }

    fun setQuery(s:String){
        query.postValue(s)
    }

    fun getMovieDetails(imdbId:String) = viewModelScope.launch {
        _movieDetails.postValue(Events(Result(Status.LOADING, null, null)))
        _movieDetails.postValue(Events(repository.getMovieDetails(imdbId)))
    }
}