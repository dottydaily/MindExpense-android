package com.purkt.mindexpense.core.data.expense.database

import com.purkt.mindexpense.core.data.common.suspendTryOrDefault
import com.purkt.mindexpense.core.data.common.tryOrDefault
import com.purkt.mindexpense.core.data.expense.database.dao.ExpenseDao
import com.purkt.mindexpense.core.data.expense.database.entity.ExpenseEntity
import com.purkt.mindexpense.core.data.expense.model.Expense
import com.purkt.mindexpense.core.data.expense.repository.ExpenseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext
import java.time.YearMonth

internal class LocalExpenseRepositoryImpl(private val dao: ExpenseDao): ExpenseRepository {
    override fun getExpenses(userId: Int, specificYearMonth: YearMonth): Flow<List<Expense>> {
        return tryOrDefault(default = flow { emit(emptyList()) }) {
            val startDateTime = specificYearMonth.atDay(1).atStartOfDay()
            val endDateTime = specificYearMonth.atEndOfMonth().atStartOfDay()
            dao.getExpensesByUserId(userId = userId, startDateTime = startDateTime, endDateTime = endDateTime)
                .transform { entities -> emit(entities.map { it.mapToModelOrThrowError() }) }
                .flowOn(Dispatchers.IO)
        }
    }

    override suspend fun getExpenseById(userId: Int, expenseId: String, isRemoteId: Boolean): Expense? {
        return suspendTryOrDefault(default = null) {
            withContext(Dispatchers.IO) {
                if (isRemoteId) {
                    dao.getExpensesByRemoteId(userId = userId, expenseId = expenseId)
                } else {
                    dao.getExpensesByLocalId(userId = userId, expenseId = expenseId)
                }
            }?.mapToModelOrThrowError()
        }
    }

    override suspend fun createExpense(expense: Expense): Boolean {
        return suspendTryOrDefault(default = false) {
            val entity = expense.mapToEntityOrThrowError()
            val resultId = withContext(Dispatchers.IO) { dao.createExpense(expense = entity) }
            resultId > 0
        }
    }

    override suspend fun updateExpense(expense: Expense): Boolean {
        return suspendTryOrDefault(default = false) {
            val entity = expense.mapToEntityOrThrowError()
            val affectedRow = withContext(Dispatchers.IO) { dao.updateExpense(expense = entity) }
            affectedRow > 0
        }
    }

    override suspend fun deleteExpense(expense: Expense): Boolean {
        return suspendTryOrDefault(default = false) {
            val entity = expense.mapToEntityOrThrowError()
            val affectedRow = withContext(Dispatchers.IO) { dao.deleteExpense(expense = entity) }
            affectedRow > 0
        }
    }
}

private fun ExpenseEntity.mapToModelOrThrowError(): Expense {
    return Expense(
        localId = localId,
        ownerUserId = ownerUserId,
        title = title,
        recipient = recipient,
        note = note.orEmpty(),
        amount = amount,
        imageUrl = imageUrl.orEmpty(),
        paidAt = paidAt,
        createdAt = updatedAt,
        updatedAt = updatedAt,
    )
}

private fun Expense.mapToEntityOrThrowError(): ExpenseEntity {
    return ExpenseEntity(
        localId = localId,
        ownerUserId = ownerUserId,
        title = title,
        recipient = recipient,
        note = note,
        amount = amount,
        imageUrl = imageUrl,
        paidAt = paidAt,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}