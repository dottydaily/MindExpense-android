package com.purkt.mindexpense.core.data.common

import com.purkt.mindexpense.core.testing.base.BaseTest
import com.purkt.mindexpense.core.testing.logging.includedMockMyLogger
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.koin.dsl.ModuleDeclaration
import kotlin.test.assertEquals
import kotlin.test.assertNull

class NumberHelperTest: BaseTest() {
    override val mockModuleScope: ModuleDeclaration = {
        includedMockMyLogger()
    }

    @Test
    fun `Given we get decimal number without fraction, When we call formatCurrencyOrNull, Then return formatted currency string`() {
        val dataList = listOf(
            14 to "14",
            0 to "0",
            255 to "255",
            -999 to "-999",
            17_000 to "17,000",
            -4_300 to "-4,300",
            199_232 to "199,232",
            9_003_002 to "9,003,002",
            25_000_777 to "25,000,777",
            100_800_555 to "100,800,555",
        )

        dataList.forEach { (input, expected) ->
            assertEquals(expected, input.formatCurrencyOrNull())
        }
    }

    @Test
    fun `Given we get decimal number with fraction, When we call formatCurrencyOrNull, Then return formatted currency string`() {
        val dataList = listOf(
            14.1 to "14.1",
            0.00 to "0",
            255.000 to "255",
            -999.99 to "-999.99",
            17_000.005 to "17,000.01",
            -4_300.01 to "-4,300.01",
            199_232.23 to "199,232.23",
            9_003_002.123 to "9,003,002.12",
            25_000_777.2 to "25,000,777.2",
            100_800_555.0 to "100,800,555",
            100_800_555.12 to "100,800,555.12",
        )

        dataList.forEach { (input, expected) ->
            assertEquals(expected, input.formatCurrencyOrNull())
        }
    }

    @Test
    fun `Given an unexpected error occurs when format number, When we call formatCurrencyOrNull, Then return null`() {
        val mockNumber = mockk<Number>()
        every { mockNumber.formatCurrencyOrNull() } throws Exception("Mock error")
        assertNull(mockNumber.formatCurrencyOrNull())
    }
}