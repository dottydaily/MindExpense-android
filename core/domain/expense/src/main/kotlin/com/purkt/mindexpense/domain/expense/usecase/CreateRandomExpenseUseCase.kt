package com.purkt.mindexpense.domain.expense.usecase

import com.purkt.mindexpense.data.expense.model.Expense
import com.purkt.mindexpense.data.expense.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlin.random.Random

interface CreateRandomExpenseUseCase {
    suspend fun execute(userId: Int): Boolean
}

internal class CreateRandomExpenseUseCaseImpl(
    private val repository: ExpenseRepository,
): CreateRandomExpenseUseCase {
    private val titles = listOf("Breakfast", "Lunch", "Dinner", "Snack", "Food Gift")
    private val receivers = listOf("KFC", "McDonald", "Burger King", "Pizza Hut", "Subway")

    override suspend fun execute(userId: Int): Boolean {
        return repository.createExpense(
            Expense(
                ownerUserId = userId,
                title = titles.random(),
                receiver = receivers.random(),
                amount = Random.nextInt(50, 300).toDouble(),
            )
        )
    }
}