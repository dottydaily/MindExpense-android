package com.purkt.mindexpense.core.data.common

import com.purkt.mindexpense.core.testing.base.BaseTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryHelperTest: BaseTest() {
    @Test
    fun `Given we have block that returns value, When we call tryOrDefault with success, then return block value`() {
        val mockResult = "Success"
        val mockDefault = "Default"
        val mockBlock = mockk<() -> Any> {
            val block = this
            every { block.invoke() } returns mockResult
        }

        val actual = tryOrDefault(mockDefault, mockBlock)

        verify(exactly = 1) { mockBlock.invoke() }
        assertEquals(mockResult, actual)
    }

    @Test
    fun `When we call tryOrDefault with exception, then return default value`() {
        val mockDefault = "Default"
        val mockBlock = mockk<() -> Any> {
            val block = this
            every { block.invoke() } throws Exception("Error")
        }

        val actual = tryOrDefault(mockDefault, mockBlock)

        verify(exactly = 1) { mockBlock.invoke() }
        assertEquals(mockDefault, actual)
    }

    @Test
    fun `Given we have block that returns value, When we call suspendTryOrDefault with success, then return block value`() {
        runTest {
            val mockResult = "Success"
            val mockDefault = "Default"
            val mockBlock = mockk<suspend () -> Any> {
                val block = this
                coEvery { block.invoke() } returns mockResult
            }

            val actual = suspendTryOrDefault(mockDefault, mockBlock)
            advanceUntilIdle()

            coVerify(exactly = 1) { mockBlock.invoke() }
            assertEquals(mockResult, actual)
        }
    }

    @Test
    fun `When we call suspendTryOrDefault with exception, then return default value`() {
        runTest {
            val mockDefault = "Default"
            val mockBlock = mockk<suspend () -> Any> {
                val block = this
                coEvery { block.invoke() } throws Exception("Error")
            }

            val actual = suspendTryOrDefault(mockDefault, mockBlock)
            advanceUntilIdle()

            coVerify(exactly = 1) { mockBlock.invoke() }
            assertEquals(mockDefault, actual)
        }
    }
}