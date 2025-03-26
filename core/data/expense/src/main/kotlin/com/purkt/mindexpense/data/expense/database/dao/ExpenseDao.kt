package com.purkt.mindexpense.data.expense.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.purkt.mindexpense.data.expense.database.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expense WHERE owner_user_id = :userId")
    fun getExpensesByUserId(userId: Int): Flow<List<ExpenseEntity>>

    @Insert
    fun createExpense(expense: ExpenseEntity): Long

    @Update
    fun updateExpense(expense: ExpenseEntity): Int

    @Delete
    fun deleteExpense(expense: ExpenseEntity): Int
}