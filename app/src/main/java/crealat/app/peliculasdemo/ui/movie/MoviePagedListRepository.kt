package crealat.app.peliculasdemo.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import crealat.app.peliculasdemo.data.api.POST_PER_PAGE
import crealat.app.peliculasdemo.data.api.TheMovieDBInterface
import crealat.app.peliculasdemo.data.repository.MovieDataSource
import crealat.app.peliculasdemo.data.repository.MovieDataSourceFactory
import crealat.app.peliculasdemo.data.repository.NetworkState
import com.oxcoding.moviemvvm.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository (private val apiService : TheMovieDBInterface) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }
}