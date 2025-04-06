package com.purkt.mindexpense.core.domain.expense.usecase

import com.purkt.mindexpense.core.logging.AppLogger

interface ValidateExpenseNoteUseCase {
    val maxLength: Int?
    fun execute(input: String): Boolean
}

internal class ValidateExpenseNoteUseCaseImpl : ValidateExpenseNoteUseCase {

    override val maxLength = 200

    override fun execute(input: String): Boolean {
        return try {
            // Check if blank space not empty
            if (input.isNotEmpty() && input.isBlank()) {
                throw Exception("Note cannot be not empty with only blank spaces")
            }

            // Check if too long
            if (input.length > maxLength) {
                throw Exception("Note cannot be longer than $maxLength characters")
            }

            false
        } catch (e: Throwable) {
            AppLogger.e(e)
            true
        }
    }
}