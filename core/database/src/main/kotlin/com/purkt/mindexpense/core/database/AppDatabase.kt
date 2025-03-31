package com.purkt.mindexpense.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.purkt.mindexpense.core.data.expense.database.dao.ExpenseDao
import com.purkt.mindexpense.core.data.expense.database.entity.ExpenseEntity
import com.purkt.mindexpense.core.data.users.database.dao.UsersDao
import com.purkt.mindexpense.core.data.users.database.entity.UserEntity

@Database(
    entities = [
        ExpenseEntity::class,
        UserEntity::class,
    ],
    version = 1,
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun usersDao(): UsersDao
}