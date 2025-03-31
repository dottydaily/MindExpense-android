package com.purkt.mindexpense.core.data.common

import com.purkt.mindexpense.core.logging.AppLogger

fun<T> tryOrDefault(default: T, block: () -> T): T {
    try {
        return block()
    } catch (e: Throwable) {
        AppLogger.e(e)
        return default
    }
}

suspend fun<T> suspendTryOrDefault(default: T, block: suspend () -> T): T {
    try {
        return block()
    } catch (e: Throwable) {
        AppLogger.e(e)
        return default
    }
}