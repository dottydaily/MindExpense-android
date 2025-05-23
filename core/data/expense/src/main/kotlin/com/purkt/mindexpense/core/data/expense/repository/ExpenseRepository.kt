package com.purkt.mindexpense.core.data.expense.repository

import com.purkt.mindexpense.core.data.expense.model.Expense
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth

interface ExpenseRepository {
    fun getExpenses(userId: Int, specificYearMonth: YearMonth): Flow<List<Expense>>
    suspend fun getExpenseById(userId: Int, expenseId: String, isRemoteId: Boolean): Expense?
    suspend fun createExpense(expense: Expense): Boolean
    suspend fun updateExpense(expense: Expense): Boolean
    suspend fun deleteExpense(expense: Expense): Boolean
}