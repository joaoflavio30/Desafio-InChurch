package com.joaoflaviofreitas.inchurch

import com.joaoflaviofreitas.inchurch.utils.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope

@ExperimentalCoroutinesApi
class TestDispatcherProvider : DispatcherProvider {

    val testScope = TestScope()
    private val testDispatcher = StandardTestDispatcher(testScope.testScheduler)

    override val main: CoroutineDispatcher
        get() = testDispatcher

    override val io: CoroutineDispatcher
        get() = testDispatcher

    override val default: CoroutineDispatcher
        get() = testDispatcher
}
