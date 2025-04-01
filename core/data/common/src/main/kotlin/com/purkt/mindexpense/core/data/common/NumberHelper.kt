package com.purkt.mindexpense.core.data.common

import com.purkt.mindexpense.core.logging.AppLogger
import java.text.DecimalFormat

private const val currencyPattern = "#,##0.##"

fun Number.formatCurrencyOrNull(): String? {
    return try {
        DecimalFormat(currencyPattern).format(this)
    } catch (e: Throwable) {
        AppLogger.e(e)
        null
    }
}