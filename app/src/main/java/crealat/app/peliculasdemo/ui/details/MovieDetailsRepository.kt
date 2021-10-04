package crealat.app.peliculasdemo.ui.details

import androidx.lifecycle.LiveData
import crealat.app.peliculasdemo.data.api.TheMovieDBInterface
import crealat.app.peliculasdemo.data.repository.MovieDetailsNetworkDataSource
import crealat.app.peliculasdemo.data.repository.NetworkState
import com.oxcoding.moviemvvm.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository (private val apiService : TheMovieDBInterface) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetails> {

        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse

    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }



}