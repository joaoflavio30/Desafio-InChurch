package com.joaoflaviofreitas.inchurch.domain

import com.joaoflaviofreitas.inchurch.data.model.ResponseGenre
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import com.joaoflaviofreitas.inchurch.domain.usecases.GetGenres
import com.joaoflaviofreitas.inchurch.utils.TestDataClassGenerator
import com.joaoflaviofreitas.inchurch.utils.toGenre
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Test

@ExperimentalCoroutinesApi
class GetGenresTest {
    private val repository: MovieRepository = mockk()
    private val getGenres = GetGenres(repository)
    private val testDataClassGenerator = TestDataClassGenerator()

    @Test
    fun `When GetGenres, expected List of Genres`() = runTest {
        val modelExpected: List<ResponseGenre> = testDataClassGenerator.getResponseGenres()
        coEvery { repository.getGenres() } returns flow { emit(modelExpected) }

        val result = getGenres.execute().first()

        assertEquals(modelExpected.map { it.toGenre() }, result)
    }

    @Test
    fun `When GetGenres, expected IOException`() = runTest {
        coEvery { repository.getGenres() } coAnswers { throw IOException() }

        var exceptionThrown: Boolean = false
        try {
            getGenres.execute()
        } catch (exception: IOException) {
            // Maybe put some assertions on the exception here.
            exceptionThrown = true
        }
        assertTrue(exceptionThrown)
    }
}
