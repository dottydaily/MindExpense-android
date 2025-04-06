package com.purkt.mindexpense.core.domain.expense.usecase

import com.purkt.mindexpense.core.data.expense.model.Expense
import com.purkt.mindexpense.core.data.expense.repository.ExpenseRepository
import com.purkt.mindexpense.core.logging.AppLogger

interface CreateExpenseUseCase {
    suspend fun execute(expense: Expense): Boolean
}

internal class CreateExpenseUseCaseImpl(
    private val repository: ExpenseRepository,
): CreateExpenseUseCase {
    override suspend fun execute(expense: Expense): Boolean {
        return try {
            repository.createExpense(expense)
        } catch (e: Throwable) {
            AppLogger.e(e)
            false
        }
    }
}