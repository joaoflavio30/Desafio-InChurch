package com.joaoflaviofreitas.inchurch

import com.joaoflaviofreitas.inchurch.domain.model.Response
import com.joaoflaviofreitas.inchurch.domain.usecases.GetAllFavoriteMoviesQuantityImpl
import com.joaoflaviofreitas.inchurch.domain.usecases.GetFavoriteMoviesImpl
import com.joaoflaviofreitas.inchurch.domain.usecases.GetMovieDetailsImpl
import com.joaoflaviofreitas.inchurch.domain.usecases.SearchFavoriteMoviesByTermImpl
import com.joaoflaviofreitas.inchurch.ui.favorites.FavoritesViewModel
import com.joaoflaviofreitas.inchurch.utils.MainDispatcherRule
import com.joaoflaviofreitas.inchurch.utils.TestDataClassGenerator
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoritesViewModelTest {
    private val getFavoriteMovie: GetFavoriteMoviesImpl = mockk()
    private val getMovieDetailsImpl: GetMovieDetailsImpl = mockk()
    private val searchFavoriteMoviesByTerm: SearchFavoriteMoviesByTermImpl = mockk()
    private val getAllFavoriteMoviesQuantity: GetAllFavoriteMoviesQuantityImpl = mockk()

    private val testDispatcher = TestDispatcherProvider()

    private lateinit var viewModel: FavoritesViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val testDataClassGenerator = TestDataClassGenerator()

    @Test
    fun `When FetchFavoriteMovies, expected Success Response`() = runTest {
        coEvery { getAllFavoriteMoviesQuantity.execute() } returns flowOf(2)
        coEvery { getFavoriteMovie.execute() } returns flowOf(listOf(testDataClassGenerator.getFavoriteMovieId()))
        coEvery { getMovieDetailsImpl.execute(any()) } returns flowOf(Response.Success(testDataClassGenerator.getMovie()))

        viewModel = FavoritesViewModel(
            getFavoriteMovie,
            getMovieDetailsImpl,
            searchFavoriteMoviesByTerm,
            getAllFavoriteMoviesQuantity,
            testDispatcher,
        )

        assertEquals(Response.Success(listOf(testDataClassGenerator.getMovie())), viewModel.uiState.value)
    }
}
