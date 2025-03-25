package com.purkt.mindexpense.data.expense.database

import com.purkt.mindexpense.data.expense.model.Expense
import com.purkt.mindexpense.data.expense.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal class FakeExpenseRepositoryImpl: ExpenseRepository {
    override fun getExpenses(userId: String): Flow<List<Expense>> {
        val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")
        val mockExpenses = listOf(
            Expense(
                localId = "1",
                ownerUserId = "1",
                title = "Dinner",
                receiver = "KFC",
                note = "Dinner with friends",
                amount = 100.0,
                imageUrl = "",
                paidAt = LocalDateTime.parse("2025-03-24T00:00:00Z", pattern),
                createdAt = LocalDateTime.parse("2025-03-24T00:00:00Z", pattern),
                updatedAt = LocalDateTime.parse("2025-03-24T00:00:00Z", pattern),
            ),
            Expense(
                localId = "2",
                ownerUserId = "1",
                title = "Coffee",
                receiver = "Starbucks",
                note = "Coffee before work",
                amount = 80.0,
                imageUrl = "",
                paidAt = LocalDateTime.parse("2025-03-24T10:00:00Z", pattern),
                createdAt = LocalDateTime.parse("2025-03-24T10:00:00Z", pattern),
                updatedAt = LocalDateTime.parse("2025-03-24T10:00:00Z", pattern),
            ),
            Expense(
                localId = "3",
                ownerUserId = "1",
                title = "Lunch",
                receiver = "McDonald",
                note = "Lunch fast food",
                amount = 150.0,
                imageUrl = "",
                paidAt = LocalDateTime.parse("2025-03-25T05:12:00Z", pattern),
                createdAt = LocalDateTime.parse("2025-03-25T05:12:00Z", pattern),
                updatedAt = LocalDateTime.parse("2025-03-25T05:12:00Z", pattern),
            ),
            Expense(
                localId = "4",
                ownerUserId = "1",
                title = "Car fuel",
                receiver = "Shell",
                note = "Fill car tank",
                amount = 500.0,
                imageUrl = "",
                paidAt = LocalDateTime.parse("2025-03-26T12:40:00Z", pattern),
                createdAt = LocalDateTime.parse("2025-03-26T12:40:00Z", pattern),
                updatedAt = LocalDateTime.parse("2025-03-26T12:40:00Z", pattern)
            )
        )

        return flow { emit(mockExpenses.filter { it.ownerUserId == userId }) }
    }

    override suspend fun createExpense(expense: Expense): Boolean {
        return true
    }

    override suspend fun updateExpense(expense: Expense): Boolean {
        return true
    }

    override suspend fun deleteExpense(expense: Expense): Boolean {
        return true
    }
}