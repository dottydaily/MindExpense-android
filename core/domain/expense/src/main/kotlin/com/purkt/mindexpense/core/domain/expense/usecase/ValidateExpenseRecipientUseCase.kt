package com.purkt.mindexpense.core.domain.expense.usecase

import com.purkt.mindexpense.core.logging.AppLogger

interface ValidateExpenseRecipientUseCase {
    val maxLength: Int?
    fun execute(input: String): Boolean
}

internal class ValidateExpenseRecipientUseCaseImpl : ValidateExpenseRecipientUseCase {
    override val maxLength = 20

    override fun execute(input: String): Boolean {
        return try {
            // Check if blank
            if (input.isBlank()) {
                throw Exception("Recipient cannot be blank")
            }

            // Check if too long
            if (input.length > maxLength) {
                throw Exception("Recipient cannot be longer than $maxLength characters")
            }

            true
        } catch (e: Throwable) {
            AppLogger.e(e)
            false
        }
    }
}