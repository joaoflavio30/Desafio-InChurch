package com.joaoflaviofreitas.inchurch
import androidx.paging.PagingData
import app.cash.turbine.test
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.usecases.GetPopularMoviesImpl
import com.joaoflaviofreitas.inchurch.domain.usecases.GetTrendingMoviesImpl
import com.joaoflaviofreitas.inchurch.domain.usecases.GetUpcomingMoviesImpl
import com.joaoflaviofreitas.inchurch.domain.usecases.SearchMoviesByTermImpl
import com.joaoflaviofreitas.inchurch.ui.home.HomeViewModel
import com.joaoflaviofreitas.inchurch.utils.MainDispatcherRule
import com.joaoflaviofreitas.inchurch.utils.TestDataClassGenerator
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class HomeViewModelTest {

    private val getTrendingMovies: GetTrendingMoviesImpl = mockk()

    private val getPopularMovies: GetPopularMoviesImpl = mockk()

    private val getUpcomingMovies: GetUpcomingMoviesImpl = mockk()

    private val searchMoviesByTerm: SearchMoviesByTermImpl = mockk()

    private var dispatcherProvider = TestDispatcherProvider()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var expected = TestDataClassGenerator().getPagingDataWithMovie()

    @Test
    fun `When GetTrendingMovies, expected PagingData no empty`() = runTest {
        // Given
        coEvery { getTrendingMovies.execute() } returns flow { emit(expected) }
        coEvery { getPopularMovies.execute() } returns flow { emit(expected) }
        coEvery { getUpcomingMovies.execute() } returns flow { emit(expected) }
        coEvery { searchMoviesByTerm.execute() } returns flow { emit(expected) }

        // When ( Initialize ViewModel )
        val viewModel = HomeViewModel(
            getTrendingMovies,
            getPopularMovies,
            getUpcomingMovies,
            searchMoviesByTerm,
            dispatcherProvider,
        )

        assertTrue(viewModel.trendingMovies != PagingData.empty<Movie>())
    }

    @Test
    fun `When GetPopularMovies, expected PagingData no empty`() = runTest {
        // Given
        coEvery { getTrendingMovies.execute() } returns flow { emit(expected) }
        coEvery { getPopularMovies.execute() } returns flow { emit(expected) }
        coEvery { getUpcomingMovies.execute() } returns flow { emit(expected) }
        coEvery { searchMoviesByTerm.execute() } returns flow { emit(expected) }

        // When ( Initialize ViewModel )
        val viewModel = HomeViewModel(
            getTrendingMovies,
            getPopularMovies,
            getUpcomingMovies,
            searchMoviesByTerm,
            dispatcherProvider,
        )

        assertTrue(viewModel.popularMovies != PagingData.empty<Movie>())
    }

    @Test
    fun `When GetUpcomingMovies, expected PagingData no empty`() = runTest {
        // Given
        coEvery { getTrendingMovies.execute() } returns flow { emit(expected) }
        coEvery { getPopularMovies.execute() } returns flow { emit(expected) }
        coEvery { getUpcomingMovies.execute() } returns flow { emit(expected) }
        coEvery { searchMoviesByTerm.execute() } returns flow { emit(expected) }

        // When ( Initialize ViewModel )
        val viewModel = HomeViewModel(
            getTrendingMovies,
            getPopularMovies,
            getUpcomingMovies,
            searchMoviesByTerm,
            dispatcherProvider,
        )

        assertTrue(viewModel.upcomingMovies != PagingData.empty<Movie>())
    }

    @Test
    fun `When SearchMoviesByTerm, expected no empty Paging Data`() = runTest {
        // Given
        coEvery { getTrendingMovies.execute() } returns flow { emit(expected) }
        coEvery { getPopularMovies.execute() } returns flow { emit(expected) }
        coEvery { getUpcomingMovies.execute() } returns flow { emit(expected) }
        coEvery { searchMoviesByTerm.execute(any(), any()) } returns flow { emit(expected) }

        // When ( call the useCase )
        val viewModel = HomeViewModel(
            getTrendingMovies,
            getPopularMovies,
            getUpcomingMovies,
            searchMoviesByTerm,
            dispatcherProvider,
        )
        viewModel.searchMoviesByTerm("")
        viewModel.searchedMovie.test {
            assertTrue(awaitItem() != PagingData.empty<Movie>())
        }
    }
}
