package com.purkt.mindexpense.domain.expense.usecase

import com.purkt.mindexpense.data.expense.model.Expense
import com.purkt.mindexpense.data.expense.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

interface GetExpensesByUserIdUseCase {
    fun execute(userId: String): Flow<List<Expense>>
}

internal class GetExpensesByUserIdUseCaseImpl(
    private val repository: ExpenseRepository,
): GetExpensesByUserIdUseCase {
    override fun execute(userId: String): Flow<List<Expense>> {
        return repository.getExpenses(userId = userId)
    }
}