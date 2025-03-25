package com.purkt.mindexpense.data.expense.repository

import com.purkt.mindexpense.data.expense.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getExpenses(userId: String): Flow<List<Expense>>
    suspend fun createExpense(expense: Expense): Boolean
    suspend fun updateExpense(expense: Expense): Boolean
    suspend fun deleteExpense(expense: Expense): Boolean
}