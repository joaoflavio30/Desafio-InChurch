package com.joaoflaviofreitas.inchurch

import com.joaoflaviofreitas.inchurch.data.model.FavoriteMovieId
import com.joaoflaviofreitas.inchurch.domain.model.Genre
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.model.Response
import com.joaoflaviofreitas.inchurch.domain.usecases.AddFavoriteMovie
import com.joaoflaviofreitas.inchurch.domain.usecases.DeleteFavoriteMovie
import com.joaoflaviofreitas.inchurch.domain.usecases.GetGenres
import com.joaoflaviofreitas.inchurch.domain.usecases.GetMovieDetails
import com.joaoflaviofreitas.inchurch.ui.details.DetailsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.util.Date

@ExperimentalCoroutinesApi
class DetailsViewModelTest {

    private val getGenres: GetGenres = mockk(relaxed = true)
    private val addFavoriteMovie: AddFavoriteMovie = mockk()
    private val deleteFavoriteMovie: DeleteFavoriteMovie = mockk()
    private val getMovieDetails: GetMovieDetails = mockk()

    private val testDispatcher = TestDispatcherProvider()

    private var viewModel: DetailsViewModel = DetailsViewModel(
        getGenres,
        addFavoriteMovie,
        deleteFavoriteMovie,
        getMovieDetails,
        testDispatcher,
    )

//    @Before
//    fun setUp() {
//        Dispatchers.setMain(StandardTestDispatcher())
//   }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//    }

    @Test
    fun `Given UiState, When GetMovieDetails, Then Returns Success`() = runTest {
        // Given
        val expected = Response.Success(movie)
        coEvery { getMovieDetails.execute(any()) } returns flow { emit(expected) }

        // When
        viewModel.getMovieDetails(movie.id)
//        getMovieDetails.emit(Response.Success(movie))

        // Then
        assertEquals(expected, viewModel.uiState.value)
    }

    @Test
    fun `Given Error, When GetMovieDetails, then expected error`() = runTest {
        val expected = Response.Error("Network Error")
        coEvery { getMovieDetails.execute(movie.id) } returns flow { emit(Response.Error("Network Error")) }

        // Call
        viewModel.getMovieDetails(movie.id)

        assertEquals(expected, viewModel.uiState.value)
    }

    @Test
    fun `Given no favorite Movie, When click to favorite this Movie, then expected True sharedFlow Event`() =
        runTest {
            val expected = true
            movie.isFavorite = false
            coEvery { getMovieDetails.execute(any()) } returns flow { emit(Response.Success(movie)) }
            coEvery { addFavoriteMovie.execute(any()) } coAnswers {
                movie.isFavorite = expected
            }
            // Call
            viewModel.getMovieDetails(movie.id)
            viewModel.addOrRemoveFavoriteMovie(FavoriteMovieId(1, "Spider-man"), movie)

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
            coEvery { getMovieDetails.execute(any()) } returns flow { emit(Response.Success(movie)) }
            coEvery { deleteFavoriteMovie.execute(any()) } coAnswers {
                movie.isFavorite = expected
            }
            // Call
            viewModel.getMovieDetails(movie.id)
            viewModel.addOrRemoveFavoriteMovie(FavoriteMovieId(1, "Spider-man"), movie)

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
        viewModel = DetailsViewModel(getGenres, addFavoriteMovie, deleteFavoriteMovie, getMovieDetails, testDispatcher)

        val result = viewModel.genres.value

        assertEquals(expected, result)
    }

    @Test
    fun `Given a Movie no Genre from API, when getGenres, Then expected emptyList`() = runTest {
        val expected = emptyList<Genre>()

        coEvery { getGenres.execute() } returns flow { emit(expected) }

        // When (Initialize ViewModel)
        viewModel = DetailsViewModel(getGenres, addFavoriteMovie, deleteFavoriteMovie, getMovieDetails, testDispatcher)

        val result = viewModel.genres.value

        assertEquals(expected, result)
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
    }
}
