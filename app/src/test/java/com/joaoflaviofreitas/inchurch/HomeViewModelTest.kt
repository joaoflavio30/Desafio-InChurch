
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.testing.asSnapshot
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import app.cash.turbine.test
import com.joaoflaviofreitas.inchurch.TestDispatcherProvider
import com.joaoflaviofreitas.inchurch.domain.model.Genre
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import com.joaoflaviofreitas.inchurch.domain.usecases.GetPopularMovies
import com.joaoflaviofreitas.inchurch.domain.usecases.GetTrendingMovies
import com.joaoflaviofreitas.inchurch.domain.usecases.GetUpcomingMovies
import com.joaoflaviofreitas.inchurch.domain.usecases.SearchMoviesByTerm
import com.joaoflaviofreitas.inchurch.ui.home.HomeViewModel
import com.joaoflaviofreitas.inchurch.utils.toMovie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.Date

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class HomeViewModelTest {

    private val getTrendingMovies: GetTrendingMovies = mockk()

    private val getPopularMovies: GetPopularMovies = mockk()

    private val getUpcomingMovies: GetUpcomingMovies = mockk()

    private val searchMoviesByTerm: SearchMoviesByTerm = mockk()

    private var dispatcherProvider = TestDispatcherProvider()

    private val testScope = TestCoroutineScope()

    private val pagingData = PagingData.from(
        listOf(
            movie,
        ),
    )

    private val pagingDataMock: PagingData<Movie> = PagingData.from(listOf(movie))

    private val viewModel = HomeViewModel(
        getTrendingMovies,
        getPopularMovies,
        getUpcomingMovies,
        searchMoviesByTerm,
        dispatcherProvider,
    )

    @Test
    fun `When GetTrendingMovies, expected listMovie `() = runTest {
        // Given
        val expected = listOf(movie)
        coEvery { getTrendingMovies.execute() } returns flowOf(pagingData)

        // When ( Initialize ViewModel )
        val viewModel = HomeViewModel(
            getTrendingMovies,
            getPopularMovies,
            getUpcomingMovies,
            searchMoviesByTerm,
            dispatcherProvider,
        )
        viewModel.trendingMovies.test {
            val itemSnapshot = viewModel.trendingMovies.asSnapshot {
                scrollTo(0)
            }
            assertEquals(expected.size, itemSnapshot.size)
        }
    }

    @Test
    fun testGetPopularMovies() = runTest {
        // Given
        coEvery { getPopularMovies.execute() } returns flowOf(
            pagingData,
        )

        coVerify { getPopularMovies.execute() }
    }

    companion object {
        val genres = listOf(
            Genre(1, "Action"),
            Genre(2, "Adventure"),
            Genre(3, "Science Fiction"),
        )

        val movie = Movie(
            id = 123,
            posterPath = "poster_path.jpg",
            isAdult = false,
            overview = "This is the movie overview.",
            originalTitle = "Original Title",
            originalLanguage = "en",
            title = "Movie Title",
            releaseDate = Date(1679875200000), // January 26, 2023
            popularity = 7.8,
            voteCount = 1500,
            video = false,
            voteAverage = 7.5,
            backdropPath = "backdrop_path.jpg",
            genres = genres,
        )
        val movie2 = Movie(
            id = 1234,
            posterPath = "poster_path.jpg",
            isAdult = false,
            overview = "This is the movie overview.",
            originalTitle = "Original Title",
            originalLanguage = "en",
            title = "Movie Title3",
            releaseDate = Date(1679875200000), // January 26, 2023
            popularity = 7.8,
            voteCount = 1500,
            video = false,
            voteAverage = 7.5,
            backdropPath = "backdrop_path.jpg",
            genres = genres,
        )

        fun execute(page: Int = 1): Flow<PagingData<Movie>> =
            repository.getTrendingMovies(page).map {
                it.map { responseMovie ->
                    responseMovie.toMovie()
                }
            }
        val repository: MovieRepository = mockk()
    }

    private class MyDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    class ListUpdateTestCallback : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}

        override fun onRemoved(position: Int, count: Int) {}

        override fun onMoved(fromPosition: Int, toPosition: Int) {}

        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}
