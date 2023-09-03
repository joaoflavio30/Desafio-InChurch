package com.joaoflaviofreitas.inchurch

import com.joaoflaviofreitas.inchurch.domain.model.Response
import com.joaoflaviofreitas.inchurch.domain.usecases.GetAllFavoriteMoviesQuantity
import com.joaoflaviofreitas.inchurch.domain.usecases.GetFavoriteMovies
import com.joaoflaviofreitas.inchurch.domain.usecases.GetMovieDetails
import com.joaoflaviofreitas.inchurch.domain.usecases.SearchFavoriteMoviesByTerm
import com.joaoflaviofreitas.inchurch.ui.favorites.FavoritesViewModel
import com.joaoflaviofreitas.inchurch.utils.MainDispatcherRule
import com.joaoflaviofreitas.inchurch.utils.TestDataClassGenerator
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoritesViewModelTest {

    private val getFavoriteMovie: GetFavoriteMovies = mockk()
    private val getMovieDetails: GetMovieDetails = mockk()
    private val searchFavoriteMoviesByTerm: SearchFavoriteMoviesByTerm = mockk()
    private val getAllFavoriteMoviesQuantity: GetAllFavoriteMoviesQuantity = mockk()

    private val testDispatcher = TestDispatcherProvider()

    private lateinit var viewModel: FavoritesViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val testDataClassGenerator = TestDataClassGenerator()

    @Test
    fun `When FetchFavoriteMovies, expected Success Response`() = runTest {
        coEvery { getAllFavoriteMoviesQuantity.execute() } returns flowOf(2)
        coEvery { getFavoriteMovie.execute() } returns flowOf(listOf(testDataClassGenerator.getFavoriteMovieId()))
        coEvery { getMovieDetails.execute(any()) } returns flowOf(Response.Success(testDataClassGenerator.getMovie()))

        viewModel = FavoritesViewModel(
            getFavoriteMovie,
            getMovieDetails,
            searchFavoriteMoviesByTerm,
            getAllFavoriteMoviesQuantity,
            testDispatcher,
        )

        assertEquals(Response.Success(listOf(testDataClassGenerator.getMovie())), viewModel.uiState.value)
    }

    @Test
    fun `When FetchFavoriteMovies, expected Error Response`() = runTest {
        coEvery { getAllFavoriteMoviesQuantity.execute() } throws IOException()
        coEvery { getFavoriteMovie.execute() } returns flowOf(listOf(testDataClassGenerator.getFavoriteMovieId()))
        coEvery { getMovieDetails.execute(any()) } returns flowOf(Response.Error("Network Error"))

        var exceptionThrown: Boolean = false
        try {
            viewModel = FavoritesViewModel(
                getFavoriteMovie,
                getMovieDetails,
                searchFavoriteMoviesByTerm,
                getAllFavoriteMoviesQuantity,
                testDispatcher,
            )
        } catch (exception: IOException) {
            // Maybe put some assertions on the exception here.
            exceptionThrown = true
        }
        if (exceptionThrown) assertTrue(viewModel.uiState.value !is Response.Success)
    }
}
