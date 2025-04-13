package com.purkt.mindexpense.core.domain.expense.usecase

import com.purkt.mindexpense.core.data.expense.model.Expense
import com.purkt.mindexpense.core.data.expense.repository.ExpenseRepository
import com.purkt.mindexpense.core.logging.AppLogger

interface UpdateExpenseUseCase {
    suspend fun execute(expense: Expense): Boolean
}

internal class UpdateExpenseUseCaseImpl(private val repository: ExpenseRepository): UpdateExpenseUseCase {
    override suspend fun execute(expense: Expense): Boolean {
        return try {
            repository.updateExpense(expense)
        } catch (e: Throwable) {
            AppLogger.e(e)
            false
        }
    }
}