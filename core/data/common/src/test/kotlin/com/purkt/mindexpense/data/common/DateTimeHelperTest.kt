package com.purkt.mindexpense.data.common

import com.purkt.mindexpense.core.testing.base.BaseTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class DateTimeHelperTest: BaseTest() {

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            setUpMockAppLogger()
        }

        @AfterClass
        @JvmStatic
        fun tearDown() {
            tearDownMockAppLogger()
        }
    }

    @Test
    fun `Given we have localDateTime objects, When we convert to string, Then return correct date time string`() {
        val testDataList = listOf(
            LocalDateTime.of(2025, Month.JANUARY.value, 1, 12, 0, 57) to "2025-01-01T12:00:57",
            LocalDateTime.of(2025, Month.JULY.value, 31, 0, 9, 0) to "2025-07-31T00:09:00",
            LocalDateTime.of(2025, Month.OCTOBER.value, 8, 20, 15, 0) to "2025-10-08T20:15:00",
            LocalDateTime.of(1997, Month.DECEMBER.value, 17, 17, 45, 0) to "1997-12-17T17:45:00",
        )

        testDataList.forEach { (localDateTime, expectedString) ->
            assertEquals(expectedString, localDateTime.toIsoDateTimeStringOrThrowError())
        }
    }

    @Test
    fun `Given we have localDateTime objects, When we convert to string with manual pattern, Then return correct date time string`() {
        val manualPattern = "yyyy/MM/dd HH:mm"
        val testDataList = listOf(
            LocalDateTime.of(2025, Month.JANUARY.value, 1, 12, 0, 57) to "2025/01/01 12:00",
            LocalDateTime.of(2025, Month.JULY.value, 31, 0, 9, 0) to "2025/07/31 00:09",
            LocalDateTime.of(2025, Month.OCTOBER.value, 8, 20, 15, 0) to "2025/10/08 20:15",
            LocalDateTime.of(1997, Month.DECEMBER.value, 17, 17, 45, 0) to "1997/12/17 17:45",
        )

        testDataList.forEach { (localDateTime, expectedString) ->
            assertEquals(expectedString, localDateTime.toIsoDateTimeStringOrThrowError(manualPattern))
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Given we have localDateTime objects, When we convert to string with invalid pattern, Then throw error`() {
        val manualPattern = "ZXCVZXCVZXCV"
        val testData = LocalDateTime.of(2025, Month.JANUARY.value, 1, 12, 0, 57)
        testData.toIsoDateTimeStringOrThrowError(manualPattern)
    }

    @Test
    fun `Given we have localDateTime objects, When we convert to string and unexpected error has occurred, Then throw error`() {
        val testData = LocalDateTime.of(2025, Month.JANUARY.value, 1, 12, 0, 57)
        val mockPattern = mockk<DateTimeFormatter> {
            every { format(any()) } throws Exception("Unexpected error")
        }

        mockkStatic(DateTimeFormatter::class)
        every { DateTimeFormatter.ofPattern(any()) } returns mockPattern

        val actual = try {
            testData.toIsoDateTimeStringOrThrowError()
            null
        } catch (e: Throwable) { e }

        assertNotNull(actual)
        unmockkStatic(DateTimeFormatter::class)
    }

    @Test
    fun `Given we have localDateTime objects, When we convert to string nullable, Then return correct date time string`() {
        val testDataList = listOf(
            LocalDateTime.of(2025, Month.JANUARY.value, 1, 12, 0, 57) to "2025-01-01T12:00:57",
            LocalDateTime.of(2025, Month.JULY.value, 31, 0, 9, 0) to "2025-07-31T00:09:00",
            LocalDateTime.of(2025, Month.OCTOBER.value, 8, 20, 15, 0) to "2025-10-08T20:15:00",
            LocalDateTime.of(1997, Month.DECEMBER.value, 17, 17, 45, 0) to "1997-12-17T17:45:00",
        )

        testDataList.forEach { (localDateTime, expectedString) ->
            assertEquals(expectedString, localDateTime.toIsoDateTimeStringOrNull())
        }
    }

    @Test
    fun `Given we have localDateTime objects, When we convert to string nullable with manual pattern, Then return correct date time string`() {
        val manualPattern = "yyyy/MM/dd HH:mm"
        val testDataList = listOf(
            LocalDateTime.of(2025, Month.JANUARY.value, 1, 12, 0, 57) to "2025/01/01 12:00",
            LocalDateTime.of(2025, Month.JULY.value, 31, 0, 9, 0) to "2025/07/31 00:09",
            LocalDateTime.of(2025, Month.OCTOBER.value, 8, 20, 15, 0) to "2025/10/08 20:15",
            LocalDateTime.of(1997, Month.DECEMBER.value, 17, 17, 45, 0) to "1997/12/17 17:45",
        )

        testDataList.forEach { (localDateTime, expectedString) ->
            assertEquals(expectedString, localDateTime.toIsoDateTimeStringOrNull(manualPattern))
        }
    }

    @Test
    fun `Given we have localDateTime objects, When we convert to string nullable with invalid pattern, Then throw error`() {
        val manualPattern = "ZXCVZXCVZXCV"
        val testData = LocalDateTime.of(2025, Month.JANUARY.value, 1, 12, 0, 57)
        val actual = testData.toIsoDateTimeStringOrNull(manualPattern)
        assertNull(actual)
    }

    @Test
    fun `Given we have date time strings, When we convert to LocalDateTime, then return correct LocalDateTime`() {
        val testDataList = listOf(
            "2025-01-01T12:00:57" to LocalDateTime.of(2025, Month.JANUARY.value, 1, 12, 0, 57),
            "2025-07-31T00:09:00" to LocalDateTime.of(2025, Month.JULY.value, 31, 0, 9, 0),
            "2025-10-08T20:15:00" to LocalDateTime.of(2025, Month.OCTOBER.value, 8, 20, 15, 0),
            "1997-12-17T17:45:00" to LocalDateTime.of(1997, Month.DECEMBER.value, 17, 17, 45, 0),
        )
        testDataList.forEach { (dateTimeString, expectedLocalDateTime) ->
            assertEquals(expectedLocalDateTime, dateTimeString.toLocalDateTimeOrThrowError())
        }
    }

    @Test
    fun `Given we have date time strings, When we convert to LocalDateTime with manual pattern, then return correct LocalDateTime`() {
        val manualPattern = "yyyy/MM/dd HH:mm"
        val testDataList = listOf(
            "2025/01/01 12:00" to LocalDateTime.of(2025, Month.JANUARY.value, 1, 12, 0, 0),
            "2025/07/31 00:09" to LocalDateTime.of(2025, Month.JULY.value, 31, 0, 9, 0),
            "2025/10/08 20:15" to LocalDateTime.of(2025, Month.OCTOBER.value, 8, 20, 15, 0),
            "1997/12/17 17:45" to LocalDateTime.of(1997, Month.DECEMBER.value, 17, 17, 45, 0),
        )
        testDataList.forEach { (dateTimeString, expectedLocalDateTime) ->
            assertEquals(expectedLocalDateTime, dateTimeString.toLocalDateTimeOrThrowError(manualPattern))
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Given we have date time strings, When we convert to LocalDateTime with invalid pattern, then throw error`() {
        val manualPattern = "ZXCVZXCVZXCV"
        val testData = "2025-01-01T12:00:57"
        testData.toLocalDateTimeOrThrowError(manualPattern)
    }

    @Test
    fun `Given we have date time strings, When we convert to LocalDateTime and unexpected error has occurred, Then throw error`() {
        val testData = "2025-01-01T12:00:57"

        mockkStatic(LocalDateTime::class)
        every { LocalDateTime.parse(testData, any()) } throws Exception("Unexpected error")

        val actual = try {
            testData.toLocalDateTimeOrThrowError()
            null
        } catch (e: Throwable) { e }

        assertNotNull(actual)

        unmockkStatic(LocalDateTime::class)
    }

    @Test
    fun `Given we have date time strings, When we convert to LocalDateTime nullable, then return correct LocalDateTime`() {
        val testDataList = listOf(
            "2025-01-01T12:00:57" to LocalDateTime.of(2025, Month.JANUARY.value, 1, 12, 0, 57),
            "2025-07-31T00:09:00" to LocalDateTime.of(2025, Month.JULY.value, 31, 0, 9, 0),
            "2025-10-08T20:15:00" to LocalDateTime.of(2025, Month.OCTOBER.value, 8, 20, 15, 0),
            "1997-12-17T17:45:00" to LocalDateTime.of(1997, Month.DECEMBER.value, 17, 17, 45, 0),
        )
        testDataList.forEach { (dateTimeString, expectedLocalDateTime) ->
            assertEquals(expectedLocalDateTime, dateTimeString.toLocalDateTimeOrNull())
        }
    }

    @Test
    fun `Given we have date time strings, When we convert to LocalDateTime nullable with manual pattern, then return correct LocalDateTime`() {
        val manualPattern = "yyyy/MM/dd HH:mm"
        val testDataList = listOf(
            "2025/01/01 12:00" to LocalDateTime.of(2025, Month.JANUARY.value, 1, 12, 0, 0),
            "2025/07/31 00:09" to LocalDateTime.of(2025, Month.JULY.value, 31, 0, 9, 0),
            "2025/10/08 20:15" to LocalDateTime.of(2025, Month.OCTOBER.value, 8, 20, 15, 0),
            "1997/12/17 17:45" to LocalDateTime.of(1997, Month.DECEMBER.value, 17, 17, 45, 0),
        )
        testDataList.forEach { (dateTimeString, expectedLocalDateTime) ->
            assertEquals(expectedLocalDateTime, dateTimeString.toLocalDateTimeOrNull(manualPattern))
        }
    }

    @Test
    fun `Given we have date time strings, When we convert to LocalDateTime nullable with invalid pattern, then return null`() {
        val manualPattern = "ZXCVZXCVZXCV"
        val testDataList = listOf(
            "2025/01/01 12:00",
            "2025/07/31 00:09",
            "2025/10/08 20:15",
        )
        testDataList.forEach { dateTimeString ->
            assertNull(dateTimeString.toLocalDateTimeOrNull(manualPattern))
        }
    }

    @Test
    fun `Given we have date time strings, When we convert to LocalDateTime nullable and unexpected error has occurred, Then return null`() {
        val testData = "2025-01-01T12:00:57"

        mockkStatic(LocalDateTime::class)
        every { LocalDateTime.parse(testData, any()) } throws Exception("Unexpected error")

        val actual = testData.toLocalDateTimeOrNull()
        assertNull(actual)

        unmockkStatic(LocalDateTime::class)
    }
}