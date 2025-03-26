package com.purkt.mindexpense.data.common

fun<T> tryOrDefault(default: T, block: () -> T): T {
    try {
        return block()
    } catch (e: Throwable) {
        e.printStackTrace()
        return default
    }
}

suspend fun<T> suspendTryOrDefault(default: T, block: suspend () -> T): T {
    try {
        return block()
    } catch (e: Throwable) {
        e.printStackTrace()
        return default
    }
}