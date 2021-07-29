package com.kk.movieapp.Repository

import android.provider.SyncStateContract
import com.kk.movieapp.data.movieDetails.MovieDetails
import com.kk.movieapp.remote.MovieInterface
import com.kk.movieapp.remote.Status
import com.kk.movieapp.remote.Result
import com.kk.movieapp.utils.Constants
import java.lang.Exception

class MovieDetailsRepository(private val movieInterface: MovieInterface) {
    suspend fun getMovieDetails(imdbId: String): Result<MovieDetails>{


        return try {
val response = movieInterface.getMovieDetails(imdbId, Constants.API_Key)
            if(response.isSuccessful){
                Result(Status.SUCCESS, response.body(), null)
            }
            else{
                Result(Status.ERROR, null, null)
            }
        }
        catch (e:Exception){
            Result(Status.ERROR, null, null)
        }
    }
}