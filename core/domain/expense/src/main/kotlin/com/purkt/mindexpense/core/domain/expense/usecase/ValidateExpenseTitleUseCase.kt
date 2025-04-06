package com.purkt.mindexpense.core.domain.expense.usecase

import com.purkt.mindexpense.core.logging.AppLogger

interface ValidateExpenseTitleUseCase {
    val maxLength: Int?
    fun execute(input: String): Boolean
}

internal class ValidateExpenseTitleUseCaseImpl : ValidateExpenseTitleUseCase {

    override val maxLength = 30

    override fun execute(input: String): Boolean {
        return try {
            // Check if blank
            if (input.isBlank()) {
                throw Exception("Title cannot be blank")
            }

            // Check if too long
            if (input.length > maxLength) {
                throw Exception("Title cannot be longer than $maxLength characters")
            }

            false
        } catch (e: Throwable) {
            AppLogger.e(e)
            true
        }
    }
}