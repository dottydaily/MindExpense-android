package com.purkt.mindexpense.core.testing.base

import com.purkt.mindexpense.core.logging.AppLogger
import com.purkt.mindexpense.core.testing.logging.mockMyLoggerModule
import io.mockk.every
import io.mockk.just
import io.mockk.mockkClass
import io.mockk.mockkObject
import io.mockk.runs
import io.mockk.unmockkObject
import org.junit.Rule
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule

/**
 * Base class for unit testing initiated with various mock data by Koin and MockK.
 */
abstract class BaseTest: KoinTest {
    /**
     * Additional scope for adding mock modules.
     * This will be called in the end of module declaration in [koinTestRule].
     */
    open val additionalMockModule: ModuleDeclaration = {}

    @get:Rule
    open val koinTestRule = KoinTestRule.create {
        modules(
            module {
                includes(mockMyLoggerModule)
                additionalMockModule.invoke(this)
            }
        )
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    companion object {
        fun setUpMockAppLogger() {
            mockkObject(AppLogger)
            every { AppLogger.i(message = any()) } just runs
            every { AppLogger.d(message = any()) } just runs
            every { AppLogger.v(message = any()) } just runs
            every { AppLogger.w(message = any()) } just runs
            every { AppLogger.e(message = any()) } just runs
        }

        fun tearDownMockAppLogger() {
            unmockkObject(AppLogger)
        }
    }
}