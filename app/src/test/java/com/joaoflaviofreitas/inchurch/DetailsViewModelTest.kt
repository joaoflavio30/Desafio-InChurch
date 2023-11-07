package com.joaoflaviofreitas.inchurch

import com.joaoflaviofreitas.inchurch.data.local.model.FavoriteMovieIdEntity
import com.joaoflaviofreitas.inchurch.domain.model.Genre
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.model.Response
import com.joaoflaviofreitas.inchurch.domain.usecases.AddFavoriteMovieImpl
import com.joaoflaviofreitas.inchurch.domain.usecases.DeleteFavoriteMovieImpl
import com.joaoflaviofreitas.inchurch.domain.usecases.GetGenresImpl
import com.joaoflaviofreitas.inchurch.domain.usecases.GetMovieDetailsImpl
import com.joaoflaviofreitas.inchurch.ui.details.DetailsViewModel
import com.joaoflaviofreitas.inchurch.utils.TestDataClassGenerator
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailsViewModelTest {

    private val getGenres: GetGenresImpl = mockk(relaxed = true)
    private val addFavoriteMovie: AddFavoriteMovieImpl = mockk()
    private val deleteFavoriteMovie: DeleteFavoriteMovieImpl = mockk()
    private val getMovieDetailsImpl: GetMovieDetailsImpl = mockk()

    private val testDispatcher = TestDispatcherProvider()

    private var viewModel: DetailsViewModel = DetailsViewModel(
        getGenres,
        addFavoriteMovie,
        deleteFavoriteMovie,
        getMovieDetailsImpl,
        testDispatcher,
    )

    @Test
    fun `Given UiState, When GetMovieDetails, Then Returns Success`() = runTest {
        // Given
        val expected = Response.Success(movie)
        coEvery { getMovieDetailsImpl.execute(any()) } returns flow { emit(expected) }

        // When
        viewModel.getMovieDetails(movie.id)

        // Then
        assertEquals(expected, viewModel.uiState.value)
    }

    @Test
    fun `Given Error, When GetMovieDetails, then expected error`() = runTest {
        val expected = Response.Error("Network Error")
        coEvery { getMovieDetailsImpl.execute(movie.id) } returns flow { emit(Response.Error("Network Error")) }

        // When
        viewModel.getMovieDetails(movie.id)

        assertEquals(expected, viewModel.uiState.value)
    }

    @Test
    fun `Given no favorite Movie, When click to favorite this Movie, then expected True sharedFlow Event`() =
        runTest {
            val expected = true
            movie.isFavorite = false
            coEvery { getMovieDetailsImpl.execute(any()) } returns flow { emit(Response.Success(movie)) }
            coEvery { addFavoriteMovie.execute(any()) } coAnswers {
                movie.isFavorite = expected
            }
            // When
            viewModel.getMovieDetails(movie.id)
            viewModel.addOrRemoveFavoriteMovie(FavoriteMovieIdEntity(1, "Spider-man"), movie)

            val result =
                if (viewModel.uiState.value is Response.Success) {
                    (viewModel.uiState.value as Response.Success<Movie?>).data?.isFavorite
                        ?: false
                } else {
                    false
                }
            assertEquals(expected, result)
        }

    @Test
    fun `Given favorite Movie, When click to favorite this Movie, then expected False sharedFlow Event`() =
        runTest {
            val expected = false
            movie.isFavorite = true
            coEvery { getMovieDetailsImpl.execute(any()) } returns flow { emit(Response.Success(movie)) }
            coEvery { deleteFavoriteMovie.execute(any()) } coAnswers {
                movie.isFavorite = expected
            }
            // When
            viewModel.getMovieDetails(movie.id)
            viewModel.addOrRemoveFavoriteMovie(FavoriteMovieIdEntity(1, "Spider-man"), movie)

            val result =
                if (viewModel.uiState.value is Response.Success) {
                    (viewModel.uiState.value as Response.Success<Movie?>).data?.isFavorite
                } else {
                    null
                }
            assertEquals(expected, result)
        }

    @Test
    fun `Given a Movie, when getGenres, Then expected list of genres`() = runTest {
        val expected = genres

        coEvery { getGenres.execute() } returns flow { emit(expected) }

        // When (Initialize ViewModel)
        viewModel = DetailsViewModel(getGenres, addFavoriteMovie, deleteFavoriteMovie, getMovieDetailsImpl, testDispatcher)

        val result = viewModel.genres.value

        assertEquals(expected, result)
    }

    @Test
    fun `Given a Movie no Genre from API, when getGenres, Then expected emptyList`() = runTest {
        val expected = emptyList<Genre>()

        coEvery { getGenres.execute() } returns flow { emit(expected) }

        // When (Initialize ViewModel)
        viewModel = DetailsViewModel(getGenres, addFavoriteMovie, deleteFavoriteMovie, getMovieDetailsImpl, testDispatcher)

        val result = viewModel.genres.value

        assertEquals(expected, result)
    }

    companion object {
        private val testDataClassGenerator = TestDataClassGenerator()
        val genres = testDataClassGenerator.getGenres()
        val movie = testDataClassGenerator.getMovie()
    }
}
