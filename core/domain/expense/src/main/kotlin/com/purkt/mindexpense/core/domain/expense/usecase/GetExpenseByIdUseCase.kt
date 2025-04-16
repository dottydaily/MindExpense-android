package com.purkt.mindexpense.core.domain.expense.usecase

import com.purkt.mindexpense.core.data.expense.model.Expense
import com.purkt.mindexpense.core.data.expense.repository.ExpenseRepository
import com.purkt.mindexpense.core.logging.AppLogger

interface GetExpenseByIdUseCase {
    suspend fun execute(userId: Int, expenseId: String, isRemoteId: Boolean): Expense?
}

internal class GetExpenseByIdUseCaseImpl(private val repository: ExpenseRepository): GetExpenseByIdUseCase {
    override suspend fun execute(userId: Int, expenseId: String, isRemoteId: Boolean): Expense? {
        return try {
            repository.getExpenseById(
                userId = userId,
                expenseId = expenseId,
                isRemoteId = isRemoteId
            )
        } catch (e: Throwable) {
            AppLogger.e(e)
            null
        }
    }
}