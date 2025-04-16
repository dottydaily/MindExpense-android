package com.purkt.mindexpense.core.data.expense.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.purkt.mindexpense.core.data.expense.database.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expense WHERE owner_user_id = :userId")
    fun getExpensesByUserId(userId: Int): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expense WHERE owner_user_id = :userId AND remote_id = :expenseId")
    suspend fun getExpensesByRemoteId(userId: Int, expenseId: String): ExpenseEntity?

    @Query("SELECT * FROM expense WHERE owner_user_id = :userId AND local_id = :expenseId")
    suspend fun getExpensesByLocalId(userId: Int, expenseId: String): ExpenseEntity?

    @Insert
    fun createExpense(expense: ExpenseEntity): Long

    @Update
    fun updateExpense(expense: ExpenseEntity): Int

    @Delete
    fun deleteExpense(expense: ExpenseEntity): Int
}