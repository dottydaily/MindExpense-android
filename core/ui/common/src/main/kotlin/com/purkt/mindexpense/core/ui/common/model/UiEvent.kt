package com.purkt.mindexpense.core.ui.common.model

/**
 * Class for handling single time UI event.
 */
class UiEvent<T>(val value: T) {
    var isConsumed: Boolean = false; private set

    fun consume(): T? {
        if (isConsumed) return null
        isConsumed = true
        return value
    }
}