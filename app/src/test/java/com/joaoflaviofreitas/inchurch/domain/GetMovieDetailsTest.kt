package com.joaoflaviofreitas.inchurch.domain

import com.joaoflaviofreitas.inchurch.domain.model.Response
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
<<<<<<< HEAD
import com.joaoflaviofreitas.inchurch.domain.usecases.GetMovieDetailsImpl
=======
import com.joaoflaviofreitas.inchurch.domain.usecases.GetMovieDetails
>>>>>>> dd53c58 (add more unittests)
import com.joaoflaviofreitas.inchurch.utils.TestDataClassGenerator
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class GetMovieDetailsTest {
    private val repository: MovieRepository = mockk()
<<<<<<< HEAD
    private val getMovieDetailsImpl = GetMovieDetailsImpl(repository)
=======
    private val getMovieDetails = GetMovieDetails(repository)
>>>>>>> dd53c58 (add more unittests)
    private val testDataClassGenerator = TestDataClassGenerator()

    @Test
    fun `When GetMovieDetails, expected Success Response`() = runTest {
        val modelExpected = testDataClassGenerator.getResponseMovie()
        coEvery { repository.getMovieDetails(any()) } returns flow { emit(Response.Success(modelExpected)) }
        coEvery { repository.isMovieFavorite(any()) } returns true

<<<<<<< HEAD
        val result = getMovieDetailsImpl.execute(123).first()
=======
        val result = getMovieDetails.execute(123).first()
>>>>>>> dd53c58 (add more unittests)

        assertTrue(result is Response.Success)
    }

    @Test
    fun `When GetMovieDetails, expected Error Response`() = runTest {
        coEvery { repository.getMovieDetails(any()) } returns flow { emit(Response.Error("Network Error")) }
        coEvery { repository.isMovieFavorite(any()) } returns true

<<<<<<< HEAD
        val result = getMovieDetailsImpl.execute(123).first()
=======
        val result = getMovieDetails.execute(123).first()
>>>>>>> dd53c58 (add more unittests)

        assertTrue(result is Response.Error)
    }
}
