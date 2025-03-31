package com.purkt.mindexpense.core.logging

import com.purkt.mindexpense.core.testing.base.BaseTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.spyk
import io.mockk.unmockkConstructor
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

class AppLoggerTest: BaseTest() {
    private val mockTag = "Mock Tag"
    private val mockMessage = "Mock Message"
    private val mockkArgs = arrayOf<Any?>(null, 1, 3)
    private val mockClassName = "MyClass"
    private val mockLineNumber = 42
    private val mockStackTraceElement = mockk<StackTraceElement>(relaxed = true) {
        every { className } returns mockClassName
        every { lineNumber } returns mockLineNumber
    }
    private val testStackTraces = arrayOf(
        mockk(), mockk(), mockStackTraceElement
    )
    private val expectedCallerMethodName = "$mockClassName[L:$mockLineNumber]"

    @Before
    fun setupEach() {
        mockkConstructor(Throwable::class)
        every { anyConstructed<Throwable>().stackTrace } returns testStackTraces
    }

    @After
    fun tearDownEach() {
        unmockkConstructor(Throwable::class)
    }

    @Test
    fun `When we call AppLogger i with message and tag, then inner logger should be called with corresponding arguments`() {
        val mockInnerLogger = mockk<MyLogger>(relaxed = true)
        val spyAppLogger = spyk(AppLogger, recordPrivateCalls = true) {
            every { this@spyk.getProperty("_myLogger") } returns mockInnerLogger
        }
        spyAppLogger.i(tag = mockTag, message = mockMessage, args = mockkArgs)

        verify {
            mockInnerLogger.i(
                tag = mockTag,
                message = mockMessage,
                args = mockkArgs,
            )
        }
    }

    @Test
    fun `When we call AppLogger i with message, then inner logger should be called with corresponding arguments`() {
        val mockInnerLogger = mockk<MyLogger>(relaxed = true)
        val spyAppLogger = spyk(AppLogger, recordPrivateCalls = true) {
            every { this@spyk.getProperty("_myLogger") } returns mockInnerLogger
        }
        spyAppLogger.i(message = mockMessage, args = mockkArgs)

        verify {
            mockInnerLogger.i(
                tag = expectedCallerMethodName,
                message = mockMessage,
                args = mockkArgs,
            )
        }
    }

    @Test
    fun `When we call AppLogger v with message and tag, then inner logger should be called with corresponding arguments`() {
        val mockInnerLogger = mockk<MyLogger>(relaxed = true)
        val spyAppLogger = spyk(AppLogger, recordPrivateCalls = true) {
            every { this@spyk.getProperty("_myLogger") } returns mockInnerLogger
        }
        spyAppLogger.v(tag = mockTag, message = mockMessage, args = mockkArgs)

        verify {
            mockInnerLogger.v(
                tag = mockTag,
                message = mockMessage,
                args = mockkArgs,
            )
        }
    }

    @Test
    fun `When we call AppLogger v with message, then inner logger should be called with corresponding arguments`() {
        val mockInnerLogger = mockk<MyLogger>(relaxed = true)
        val spyAppLogger = spyk(AppLogger, recordPrivateCalls = true) {
            every { this@spyk.getProperty("_myLogger") } returns mockInnerLogger
        }
        spyAppLogger.v(message = mockMessage, args = mockkArgs)

        verify {
            mockInnerLogger.v(
                tag = expectedCallerMethodName,
                message = mockMessage,
                args = mockkArgs,
            )
        }
    }

    @Test
    fun `When we call AppLogger d with message and tag, then inner logger should be called with corresponding arguments`() {
        val mockInnerLogger = mockk<MyLogger>(relaxed = true)
        val spyAppLogger = spyk(AppLogger, recordPrivateCalls = true) {
            every { this@spyk.getProperty("_myLogger") } returns mockInnerLogger
        }
        spyAppLogger.d(tag = mockTag, message = mockMessage, args = mockkArgs)

        verify {
            mockInnerLogger.d(
                tag = mockTag,
                message = mockMessage,
                args = mockkArgs,
            )
        }
    }

    @Test
    fun `When we call AppLogger d with message, then inner logger should be called with corresponding arguments`() {
        val mockInnerLogger = mockk<MyLogger>(relaxed = true)
        val spyAppLogger = spyk(AppLogger, recordPrivateCalls = true) {
            every { this@spyk.getProperty("_myLogger") } returns mockInnerLogger
        }
        spyAppLogger.d(message = mockMessage, args = mockkArgs)

        verify {
            mockInnerLogger.d(
                tag = expectedCallerMethodName,
                message = mockMessage,
                args = mockkArgs,
            )
        }
    }

    @Test
    fun `When we call AppLogger w with message and tag, then inner logger should be called with corresponding arguments`() {
        val mockInnerLogger = mockk<MyLogger>(relaxed = true)
        val spyAppLogger = spyk(AppLogger, recordPrivateCalls = true) {
            every { this@spyk.getProperty("_myLogger") } returns mockInnerLogger
        }
        spyAppLogger.w(tag = mockTag, message = mockMessage, args = mockkArgs)

        verify {
            mockInnerLogger.w(
                tag = mockTag,
                message = mockMessage,
                args = mockkArgs,
            )
        }
    }

    @Test
    fun `When we call AppLogger w with message, then inner logger should be called with corresponding arguments`() {
        val mockInnerLogger = mockk<MyLogger>(relaxed = true)
        val spyAppLogger = spyk(AppLogger, recordPrivateCalls = true) {
            every { this@spyk.getProperty("_myLogger") } returns mockInnerLogger
        }
        spyAppLogger.w(message = mockMessage, args = mockkArgs)

        verify {
            mockInnerLogger.w(
                tag = expectedCallerMethodName,
                message = mockMessage,
                args = mockkArgs,
            )
        }
    }

    @Test
    fun `When we call AppLogger e with message and tag, then inner logger should be called with corresponding arguments`() {
        val mockInnerLogger = mockk<MyLogger>(relaxed = true)
        val spyAppLogger = spyk(AppLogger, recordPrivateCalls = true) {
            every { this@spyk.getProperty("_myLogger") } returns mockInnerLogger
        }
        spyAppLogger.e(tag = mockTag, message = mockMessage, args = mockkArgs)

        verify {
            mockInnerLogger.e(
                tag = mockTag,
                message = mockMessage,
                args = mockkArgs,
            )
        }
    }

    @Test
    fun `When we call AppLogger e with message, then inner logger should be called with corresponding arguments`() {
        val mockInnerLogger = mockk<MyLogger>(relaxed = true)
        val spyAppLogger = spyk(AppLogger, recordPrivateCalls = true) {
            every { this@spyk.getProperty("_myLogger") } returns mockInnerLogger
        }
        spyAppLogger.e(message = mockMessage, args = mockkArgs)

        verify {
            mockInnerLogger.e(
                tag = expectedCallerMethodName,
                message = mockMessage,
                args = mockkArgs,
            )
        }
    }

    @Test
    fun `Given we have a throwable, When we call AppLogger i with throwable, then inner logger should be called with corresponding arguments`() {
        val mockThrowable = mockk<Throwable>()
        val mockInnerLogger = mockk<MyLogger>(relaxed = true)
        val spyAppLogger = spyk(AppLogger, recordPrivateCalls = true) {
            every { this@spyk.getProperty("_myLogger") } returns mockInnerLogger
        }
        spyAppLogger.i(e = mockThrowable)

        verify { mockInnerLogger.i(e = mockThrowable, tag = expectedCallerMethodName) }
    }

    @Test
    fun `Given we have a throwable and tag, When we call AppLogger i, then inner logger should be called with corresponding arguments`() {
        val mockThrowable = mockk<Throwable>()
        val mockInnerLogger = mockk<MyLogger>(relaxed = true)
        val spyAppLogger = spyk(AppLogger, recordPrivateCalls = true) {
            every { this@spyk.getProperty("_myLogger") } returns mockInnerLogger
        }
        spyAppLogger.i(e = mockThrowable, tag = mockTag)

        verify { mockInnerLogger.i(e = mockThrowable, tag = mockTag) }
    }

    @Test
    fun `Given we have a throwable, When we call AppLogger v with throwable, then inner logger should be called with corresponding arguments`() {
        val mockThrowable = mockk<Throwable>()
        val mockInnerLogger = mockk<MyLogger>(relaxed = true)
        val spyAppLogger = spyk(AppLogger, recordPrivateCalls = true) {
            every { this@spyk.getProperty("_myLogger") } returns mockInnerLogger
        }
        spyAppLogger.v(e = mockThrowable)

        verify { mockInnerLogger.v(e = mockThrowable, tag = expectedCallerMethodName) }
    }

    @Test
    fun `Given we have a throwable and tag, When we call AppLogger v, then inner logger should be called with corresponding arguments`() {
        val mockThrowable = mockk<Throwable>()
        val mockInnerLogger = mockk<MyLogger>(relaxed = true)
        val spyAppLogger = spyk(AppLogger, recordPrivateCalls = true) {
            every { this@spyk.getProperty("_myLogger") } returns mockInnerLogger
        }
        spyAppLogger.v(e = mockThrowable, tag = mockTag)

        verify { mockInnerLogger.v(e = mockThrowable, tag = mockTag) }
    }

    @Test
    fun `Given we have a throwable, When we call AppLogger d with throwable, then inner logger should be called with corresponding arguments`() {
        val mockThrowable = mockk<Throwable>()
        val mockInnerLogger = mockk<MyLogger>(relaxed = true)
        val spyAppLogger = spyk(AppLogger, recordPrivateCalls = true) {
            every { this@spyk.getProperty("_myLogger") } returns mockInnerLogger
        }
        spyAppLogger.d(e = mockThrowable)

        verify { mockInnerLogger.d(e = mockThrowable, tag = expectedCallerMethodName) }
    }

    @Test
    fun `Given we have a throwable and tag, When we call AppLogger d, then inner logger should be called with corresponding arguments`() {
        val mockThrowable = mockk<Throwable>()
        val mockInnerLogger = mockk<MyLogger>(relaxed = true)
        val spyAppLogger = spyk(AppLogger, recordPrivateCalls = true) {
            every { this@spyk.getProperty("_myLogger") } returns mockInnerLogger
        }
        spyAppLogger.d(e = mockThrowable, tag = mockTag)

        verify { mockInnerLogger.d(e = mockThrowable, tag = mockTag) }
    }

    @Test
    fun `Given we have a throwable, When we call AppLogger w with throwable, then inner logger should be called with corresponding arguments`() {
        val mockThrowable = mockk<Throwable>()
        val mockInnerLogger = mockk<MyLogger>(relaxed = true)
        val spyAppLogger = spyk(AppLogger, recordPrivateCalls = true) {
            every { this@spyk.getProperty("_myLogger") } returns mockInnerLogger
        }
        spyAppLogger.w(e = mockThrowable)

        verify { mockInnerLogger.w(e = mockThrowable, tag = expectedCallerMethodName) }
    }

    @Test
    fun `Given we have a throwable and tag, When we call AppLogger w, then inner logger should be called with corresponding arguments`() {
        val mockThrowable = mockk<Throwable>()
        val mockInnerLogger = mockk<MyLogger>(relaxed = true)
        val spyAppLogger = spyk(AppLogger, recordPrivateCalls = true) {
            every { this@spyk.getProperty("_myLogger") } returns mockInnerLogger
        }
        spyAppLogger.w(e = mockThrowable, tag = mockTag)

        verify { mockInnerLogger.w(e = mockThrowable, tag = mockTag) }
    }

    @Test
    fun `Given we have a throwable, When we call AppLogger e with throwable, then inner logger should be called with corresponding arguments`() {
        val mockThrowable = mockk<Throwable>()
        val mockInnerLogger = mockk<MyLogger>(relaxed = true)
        val spyAppLogger = spyk(AppLogger, recordPrivateCalls = true) {
            every { this@spyk.getProperty("_myLogger") } returns mockInnerLogger
        }
        spyAppLogger.e(e = mockThrowable)

        verify { mockInnerLogger.e(e = mockThrowable, tag = expectedCallerMethodName) }
    }

    @Test
    fun `Given we have a throwable and tag, When we call AppLogger e, then inner logger should be called with corresponding arguments`() {
        val mockThrowable = mockk<Throwable>()
        val mockInnerLogger = mockk<MyLogger>(relaxed = true)
        val spyAppLogger = spyk(AppLogger, recordPrivateCalls = true) {
            every { this@spyk.getProperty("_myLogger") } returns mockInnerLogger
        }
        spyAppLogger.e(e = mockThrowable, tag = mockTag)

        verify { mockInnerLogger.e(e = mockThrowable, tag = mockTag) }
    }

    @Test
    fun `When we call any method of AppLogger and unexpected error on getCallerMethodName(), then tag should be empty`() {
        every { anyConstructed<Throwable>().stackTrace } returns testStackTraces.sliceArray(0..1)
        val mockThrowable = mockk<Throwable>()
        val mockInnerLogger = mockk<MyLogger>(relaxed = true)
        val spyAppLogger = spyk(AppLogger, recordPrivateCalls = true) {
            every { this@spyk.getProperty("_myLogger") } returns mockInnerLogger
        }
        spyAppLogger.e(e = mockThrowable)

        verify { mockInnerLogger.e(e = mockThrowable, tag = "") }
    }
}